package com.fstuckint.baedalyogieats.core.api.store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fstuckint.baedalyogieats.core.api.store.support.Cursor;
import com.fstuckint.baedalyogieats.storage.db.core.store.CategoryEntity;
import com.fstuckint.baedalyogieats.storage.db.core.store.StoreEntity;
import com.fstuckint.baedalyogieats.storage.db.core.store.StoreRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
class StoreReaderTest {

    @Autowired
    private EntityManager entityManager;

    @Mock
    private StoreRepository storeRepository;

    private StoreReader storeReader;

    private List<StoreEntity> stores;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        storeReader = new StoreReader(storeRepository);

        CategoryEntity category = new CategoryEntity("한식");
        entityManager.persist(category);

        stores = IntStream.rangeClosed(1, 15)
            .mapToObj(i -> new StoreEntity("가게" + i, "가게 설명", "주소", UUID.randomUUID(), category))
            .peek(entityManager::persist)
            .toList();

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void 커서가_없을_때_첫_페이지_가게_목록을_조회한다() {
        // given
        final long limit = 10;
        final String sortKey = "createdAt";
        final Sort.Direction sort = Sort.Direction.ASC;
        Cursor cursor = new Cursor(null, limit, sortKey, sort);
        when(storeRepository.findByCursor(cursor.getUuid(), cursor.getTimestamp(), cursor.limit() + 1, cursor.sortKey(),
                cursor.sort()))
            .thenReturn(stores.subList(0, (int) limit));

        // when
        List<StoreResult> results = storeReader.read(cursor);

        // then
        assertThat(results).hasSize((int) limit);
        assertThat(results.getFirst().name()).isEqualTo("가게1");
        assertThat(results.getLast().name()).isEqualTo("가게10");
    }

    @Test
    void 커서가_있을_때_다음_페이지의_가게_목록을_조회한다() {
        // given
        final long limit = 10;
        final String sortKey = "createdAt";
        final Sort.Direction sort = Sort.Direction.ASC;
        UUID lastUuid = UUID.randomUUID();
        LocalDateTime lastTimestamp = LocalDateTime.now();
        Cursor cursor = new Cursor(Cursor.encodeCursor(lastUuid, lastTimestamp), limit, sortKey, sort);
        when(storeRepository.findByCursor(lastUuid, lastTimestamp, limit + 1, sortKey, sort))
            .thenReturn(stores.subList(10, 15));

        // when
        List<StoreResult> results = storeReader.read(cursor);

        // then
        assertThat(results).hasSize(5);
        assertThat(results.get(0).name()).isEqualTo("가게11");
        assertThat(results.get(4).name()).isEqualTo("가게15");
    }

    @Test
    void 마지막_페이지_이후_가게_목록을_조회하면_빈_목록이_반환된다() {
        // given
        final long limit = 10;
        final String sortKey = "createdAt";
        final Sort.Direction sort = Sort.Direction.ASC;
        UUID lastUuid = UUID.randomUUID();
        LocalDateTime lastTimestamp = LocalDateTime.now();
        Cursor cursor = new Cursor(Cursor.encodeCursor(lastUuid, lastTimestamp), limit, sortKey, sort);
        when(storeRepository.findByCursor(lastUuid, lastTimestamp, limit + 1, sortKey, sort)).thenReturn(List.of());

        // when
        List<StoreResult> results = storeReader.read(cursor);

        // then
        assertThat(results).isEmpty();
    }

}

package com.fstuckint.baedalyogieats.storage.db.core.store;

import static org.assertj.core.api.Assertions.assertThat;

import com.fstuckint.baedalyogieats.storage.db.CoreDbContextTest;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class StoreRepositoryIT extends CoreDbContextTest {

    private final StoreRepository storeRepository;

    private final EntityManager entityManager;

    private List<StoreEntity> stores;

    public StoreRepositoryIT(StoreRepository storeRepository, EntityManager entityManager) {
        this.storeRepository = storeRepository;
        this.entityManager = entityManager;
    }

    @BeforeEach
    void setUp() {
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
    void 가게가_추가된_후_가게_UUID로_조회돼야_한다() {
        // given
        CategoryEntity category = new CategoryEntity("한식");
        entityManager.persist(category);

        UUID userUuid = UUID.randomUUID();
        StoreEntity store = storeRepository.add(new StoreEntity("상호명", "가게 설명", "주소", userUuid, category));
        entityManager.flush();
        entityManager.clear();

        // when
        StoreEntity addedStore = storeRepository.findByUuid(store.getUuid())
            .orElseThrow(() -> new AssertionError("Store not found"));

        // then
        assertThat(addedStore).isNotNull();
        assertThat(addedStore.getUuid()).isEqualTo(store.getUuid());
        assertThat(addedStore.getName()).isEqualTo("상호명");
        assertThat(addedStore.getDescription()).isEqualTo("가게 설명");
        assertThat(addedStore.getFullAddress()).isEqualTo("주소");
        assertThat(addedStore.getUserUuid()).isEqualTo(userUuid);
        assertThat(addedStore.getCategoryEntity()).isNotNull();
        assertThat(addedStore.getCategoryEntity().getName()).isEqualTo("한식");
    }

    @Test
    void 가게_UUID와_타임스탬프_기반_커서로_가게_목록의_첫_번째_페이지를_조회한다() {
        // given
        final long limit = 10;
        String sortKey = "createdAt";
        Sort.Direction sort = Direction.ASC;

        // when
        List<StoreEntity> firstPage = storeRepository.findByCursor(null, null, limit, sortKey, sort);

        // then
        assertThat(firstPage).hasSize(10);
        assertThat(firstPage.getFirst().getUuid()).isEqualTo(stores.getFirst().getUuid());
        assertThat(firstPage.getLast().getUuid()).isEqualTo(stores.get(9).getUuid());
    }

    @Test
    void 가게_UUID와_타임스탬프_기반_커서로_가게_목록의_두_번째_페이지를_조회한다() {
        // given
        final long limit = 10;
        String sortKey = "createdAt";
        Sort.Direction sort = Direction.ASC;

        // when
        List<StoreEntity> firstPage = storeRepository.findByCursor(null, null, limit, sortKey, sort);
        StoreEntity lastStore = firstPage.getLast();
        List<StoreEntity> secondPage = storeRepository.findByCursor(lastStore.getUuid(), lastStore.getCreatedAt(),
                limit, sortKey, sort);

        // then
        assertThat(secondPage).hasSize(5);
        assertThat(secondPage.getFirst().getUuid()).isEqualTo(stores.get(10).getUuid());
        assertThat(secondPage.getLast().getUuid()).isEqualTo(stores.getLast().getUuid());
    }

    @Test
    void 가게_UUID와_타임스탬프_기반_커서로_마지막_가게_이후_조회_시_빈_리스트가_반환되어야_한다() {
        // given
        final long limit = 10;
        String sortKey = "createdAt";
        Sort.Direction sort = Sort.Direction.ASC;
        StoreEntity lastStore = stores.getLast();

        // when
        List<StoreEntity> result = storeRepository.findByCursor(lastStore.getUuid(), lastStore.getCreatedAt(), limit,
                sortKey, sort);

        // then
        assertThat(result).isEmpty();
    }

}
package com.fstuckint.baedalyogieats.core.api.store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fstuckint.baedalyogieats.core.api.store.support.Cursor;
import com.fstuckint.baedalyogieats.storage.db.core.store.CategoryEntity;
import com.fstuckint.baedalyogieats.storage.db.core.store.StoreEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StoreServiceTest {

    private UUID ownerUuid;

    private UUID categoryUuid;

    private UUID storeUuid;

    @Mock
    private StoreRegister storeRegister;

    @Mock
    private StoreFinder storeFinder;

    @Mock
    private StoreReader storeReader;

    private StoreService storeService;

    @BeforeEach
    void setUp() {
        this.ownerUuid = UUID.randomUUID();
        this.categoryUuid = UUID.randomUUID();
        this.storeUuid = UUID.randomUUID();

        MockitoAnnotations.openMocks(this);
        storeService = new StoreService(storeRegister, storeFinder, storeReader);
    }

    @Test
    void 가게를_등록한다() {
        // given
        String name = "상호명";
        String description = "가게 설명";
        String fullAddress = "주소";
        String categoryName = "한식";

        Owner owner = new Owner(ownerUuid);
        CategoryWithUuid categoryWithUuid = new CategoryWithUuid(categoryUuid, categoryName);
        Store store = new Store(name, description, fullAddress, categoryWithUuid);
        OwnerStore ownerStore = new OwnerStore(owner, store);

        CategoryEntity categoryEntity = new CategoryEntity(categoryName);
        ReflectionTestUtils.setField(categoryEntity, "uuid", categoryUuid);
        StoreEntity storeEntity = ownerStore.toEntity(categoryEntity);
        ReflectionTestUtils.setField(storeEntity, "uuid", storeUuid);
        when(storeRegister.register(ownerStore)).thenReturn(StoreResult.of(storeEntity));

        // when
        StoreResult result = storeService.register(ownerStore);

        // then
        assertThat(result).isNotNull();
        assertThat(result.uuid()).isEqualTo(storeUuid);
        assertThat(result.name()).isEqualTo(name);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.fullAddress()).isEqualTo(fullAddress);
        assertThat(result.categoryUuid()).isEqualTo(categoryUuid);
        assertThat(result.categoryName()).isEqualTo(categoryName);
    }

    @Test
    void 가게를_조회한다() {
        // given
        String name = "상호명";
        String description = "가게 설명";
        String fullAddress = "주소";
        String categoryName = "한식";

        Owner owner = new Owner(ownerUuid);
        CategoryWithUuid categoryWithUuid = new CategoryWithUuid(categoryUuid, categoryName);
        Store store = new Store(name, description, fullAddress, categoryWithUuid);
        OwnerStore ownerStore = new OwnerStore(owner, store);

        CategoryEntity categoryEntity = new CategoryEntity(categoryName);
        ReflectionTestUtils.setField(categoryEntity, "uuid", categoryUuid);
        StoreEntity storeEntity = ownerStore.toEntity(categoryEntity);
        ReflectionTestUtils.setField(storeEntity, "uuid", storeUuid);
        when(storeFinder.find(storeUuid)).thenReturn(StoreResult.of(storeEntity));

        // when
        StoreResult result = storeService.find(storeUuid);

        // then
        assertThat(result).isNotNull();
        assertThat(result.uuid()).isEqualTo(storeUuid);
        assertThat(result.name()).isEqualTo(name);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.fullAddress()).isEqualTo(fullAddress);
        assertThat(result.categoryUuid()).isEqualTo(categoryUuid);
        assertThat(result.categoryName()).isEqualTo(categoryName);
    }

    @Test
    void 가게_목록을_조회한다() {
        // given
        Cursor cursor = new Cursor(null, 10, "createdAt", Sort.Direction.ASC);
        List<StoreResult> expectedResults = List.of(
                new StoreResult(UUID.randomUUID(), "가게1", "가게 설명", "주소", UUID.randomUUID(), "한식", UUID.randomUUID(),
                        LocalDateTime.now(), LocalDateTime.now()),
                new StoreResult(UUID.randomUUID(), "가게2", "가게 설명", "주소", UUID.randomUUID(), "한식", UUID.randomUUID(),
                        LocalDateTime.now(), LocalDateTime.now()));
        when(storeReader.read(cursor)).thenReturn(expectedResults);

        // when
        List<StoreResult> results = storeService.read(cursor);

        // then
        assertThat(results).isEqualTo(expectedResults);
        verify(storeReader).read(cursor);
    }

}
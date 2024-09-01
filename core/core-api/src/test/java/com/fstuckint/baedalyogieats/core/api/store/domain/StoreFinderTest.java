package com.fstuckint.baedalyogieats.core.api.store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fstuckint.baedalyogieats.storage.db.core.store.CategoryEntity;
import com.fstuckint.baedalyogieats.storage.db.core.store.StoreEntity;
import com.fstuckint.baedalyogieats.storage.db.core.store.StoreRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StoreFinderTest {

    private UUID ownerUuid;

    private UUID storeUuid;

    private UUID categoryUuid;

    @Mock
    private StoreRepository storeRepository;

    private StoreFinder storeFinder;

    @BeforeEach
    void setUp() {
        this.ownerUuid = UUID.randomUUID();
        this.storeUuid = UUID.randomUUID();
        this.categoryUuid = UUID.randomUUID();

        MockitoAnnotations.initMocks(this);
        storeFinder = new StoreFinder(storeRepository);
    }

    @Test
    void 가게를_조회한다() {
        // given
        String name = "상호명";
        String description = "가게 설명";
        String fullAddress = "주소";
        String categoryName = "한식";

        Owner owner = new Owner(ownerUuid);
        Category category = new Category(categoryUuid, categoryName);
        Store store = new Store(name, description, fullAddress, category);
        OwnerStore ownerStore = new OwnerStore(owner, store);

        CategoryEntity categoryEntity = new CategoryEntity(categoryName);
        ReflectionTestUtils.setField(categoryEntity, "uuid", categoryUuid);
        StoreEntity storeEntity = ownerStore.toEntity(categoryEntity);
        ReflectionTestUtils.setField(storeEntity, "uuid", storeUuid);

        when(storeRepository.findByUuid(storeUuid)).thenReturn(Optional.of(storeEntity));

        // when
        StoreResult result = storeFinder.find(storeUuid);

        // then
        assertThat(result).isNotNull();
        assertThat(result.uuid()).isEqualTo(storeUuid);
        assertThat(result.name()).isEqualTo(name);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.fullAddress()).isEqualTo(fullAddress);
        assertThat(result.categoryUuid()).isEqualTo(categoryUuid);
        assertThat(result.categoryName()).isEqualTo(categoryName);
    }

}
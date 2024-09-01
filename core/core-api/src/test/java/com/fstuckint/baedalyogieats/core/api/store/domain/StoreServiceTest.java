package com.fstuckint.baedalyogieats.core.api.store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fstuckint.baedalyogieats.storage.db.core.store.CategoryEntity;
import com.fstuckint.baedalyogieats.storage.db.core.store.StoreEntity;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StoreServiceTest {

    private UUID ownerUuid;

    private UUID categoryUuid;

    private UUID storeUuid;

    @Mock
    private StoreRegister storeRegister;

    private StoreService storeService;

    @BeforeEach
    void setUp() {
        this.ownerUuid = UUID.randomUUID();
        this.categoryUuid = UUID.randomUUID();
        this.storeUuid = UUID.randomUUID();

        MockitoAnnotations.openMocks(this);
        storeService = new StoreService(storeRegister);
    }

    @Test
    void 가게를_등록한다() {
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

}
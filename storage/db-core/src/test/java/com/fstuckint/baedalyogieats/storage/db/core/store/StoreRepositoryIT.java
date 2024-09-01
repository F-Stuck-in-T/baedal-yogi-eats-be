package com.fstuckint.baedalyogieats.storage.db.core.store;

import static org.assertj.core.api.Assertions.assertThat;

import com.fstuckint.baedalyogieats.storage.db.CoreDbContextTest;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

class StoreRepositoryIT extends CoreDbContextTest {

    private final StoreRepository storeRepository;

    private final EntityManager entityManager;

    public StoreRepositoryIT(StoreRepository storeRepository, EntityManager entityManager) {
        this.storeRepository = storeRepository;
        this.entityManager = entityManager;
    }

    @Transactional
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

}
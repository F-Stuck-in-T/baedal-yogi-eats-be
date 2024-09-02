package com.fstuckint.baedalyogieats.core.api.store.domain;

import com.fstuckint.baedalyogieats.storage.db.core.store.CategoryEntity;
import com.fstuckint.baedalyogieats.storage.db.core.store.CategoryRepository;
import com.fstuckint.baedalyogieats.storage.db.core.store.StoreEntity;
import com.fstuckint.baedalyogieats.storage.db.core.store.StoreRepository;
import org.springframework.stereotype.Component;

@Component
public class StoreRegister {

    private final StoreRepository storeRepository;

    private final CategoryRepository categoryRepository;

    public StoreRegister(StoreRepository storeRepository, CategoryRepository categoryRepository) {
        this.storeRepository = storeRepository;
        this.categoryRepository = categoryRepository;
    }

    public StoreResult register(OwnerStore ownerStore) {
        CategoryEntity categoryEntity = categoryRepository.findByUuid(ownerStore.store().categoryWithUuid().uuid())
            .orElseThrow();
        StoreEntity registeredStore = storeRepository.add(ownerStore.toEntity(categoryEntity));
        return StoreResult.of(registeredStore);
    }

}

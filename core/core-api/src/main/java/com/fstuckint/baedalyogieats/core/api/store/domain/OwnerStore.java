package com.fstuckint.baedalyogieats.core.api.store.domain;

import com.fstuckint.baedalyogieats.storage.db.core.store.CategoryEntity;
import com.fstuckint.baedalyogieats.storage.db.core.store.StoreEntity;

public record OwnerStore(Owner Owner, Store store) {

    public StoreEntity toEntity(CategoryEntity categoryEntity) {
        return new StoreEntity(store.name(), store.description(), store.fullAddress(), Owner.uuid(), categoryEntity);
    }

}

package com.fstuckint.baedalyogieats.core.api.store.controller.v1.request;

import com.fstuckint.baedalyogieats.core.api.store.domain.Category;
import com.fstuckint.baedalyogieats.core.api.store.domain.Store;
import java.util.UUID;

public record StoreRegisterRequest(String name, String description, String fullAddress, String categoryUuid) {

    public Store toStore() {
        Category category = new Category(UUID.fromString(categoryUuid), name);
        return new Store(name, description, fullAddress, category);
    }
}

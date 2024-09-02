package com.fstuckint.baedalyogieats.core.api.store.domain;

import com.fstuckint.baedalyogieats.storage.db.core.store.CategoryEntity;

public record Category(String name) {
    public CategoryEntity toEntity() {
        return new CategoryEntity(name);
    }
}

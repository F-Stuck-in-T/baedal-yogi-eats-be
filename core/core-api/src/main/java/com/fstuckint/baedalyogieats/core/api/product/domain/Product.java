package com.fstuckint.baedalyogieats.core.api.product.domain;

import com.fstuckint.baedalyogieats.storage.db.core.product.ProductEntity;
import java.util.UUID;

public record Product(String name, String description, int unitPrice, UUID storeUuid) {

    public ProductEntity toEntity() {
        return new ProductEntity(name, description, unitPrice, storeUuid);
    }
}

package com.fstuckint.baedalyogieats.core.api.product.domain;

import com.fstuckint.baedalyogieats.storage.db.core.product.ProductEntity;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResult(UUID uuid, String name, String description, int unitPrice, UUID storeUuid,
        LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static ProductResult of(ProductEntity productEntity) {
        return new ProductResult(productEntity.getUuid(), productEntity.getName(), productEntity.getDescription(),
                productEntity.getUnitPrice(), productEntity.getStoreUuid(), productEntity.getCreatedAt(),
                productEntity.getUpdatedAt());
    }
}

package com.fstuckint.baedalyogieats.core.api.product.controller.v1.response;

import com.fstuckint.baedalyogieats.core.api.product.domain.ProductResult;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProductResponse(UUID uuid, String name, String description, int unitPrice, UUID storeUuid,
        LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static ProductResponse of(ProductResult productResult) {
        return new ProductResponse(productResult.uuid(), productResult.name(), productResult.description(),
                productResult.unitPrice(), productResult.storeUuid(), productResult.createdAt(),
                productResult.updatedAt());
    }

    public static List<ProductResponse> of(List<ProductResult> productResults) {
        return productResults.stream().map(ProductResponse::of).toList();
    }

}

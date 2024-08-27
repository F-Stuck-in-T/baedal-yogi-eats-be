package com.fstuckint.baedalyogieats.core.api.product.controller.v1.response;

import com.fstuckint.baedalyogieats.core.api.product.domain.ProductResult;
import java.util.UUID;

public record ProductResponse(UUID uuid, String name, String description, int unitPrice, UUID storeUuid) {

    public static ProductResponse of(ProductResult productResult) {
        return new ProductResponse(productResult.uuid(), productResult.name(), productResult.description(),
                productResult.unitPrice(), productResult.storeUuid());
    }
}

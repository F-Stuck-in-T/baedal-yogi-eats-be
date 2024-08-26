package com.fstuckint.baedalyogieats.core.api.product.controller.v1.request;

import com.fstuckint.baedalyogieats.core.api.product.domain.Product;
import java.util.UUID;

public record ProductRegisterRequest(String name, String description, int unitPrice, String storeUuid) {

	public Product toProduct() {
		return new Product(name, description, unitPrice, UUID.fromString(storeUuid));
	}
}

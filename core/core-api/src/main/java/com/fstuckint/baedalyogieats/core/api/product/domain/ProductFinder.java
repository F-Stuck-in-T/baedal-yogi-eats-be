package com.fstuckint.baedalyogieats.core.api.product.domain;

import com.fstuckint.baedalyogieats.storage.db.core.product.ProductRepository;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ProductFinder {

	private final ProductRepository productRepository;

	public ProductFinder(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public ProductResult find(UUID productUuid) {
		return ProductResult.of(productRepository.findByUuid(productUuid).orElseThrow());
	}

}

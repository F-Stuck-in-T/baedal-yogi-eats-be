package com.fstuckint.baedalyogieats.core.api.product.domain;

import com.fstuckint.baedalyogieats.storage.db.core.product.ProductEntity;
import com.fstuckint.baedalyogieats.storage.db.core.product.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductRegister {

    private final ProductRepository productRepository;

    public ProductRegister(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResult add(Product product) {
        ProductEntity addedProduct = productRepository.add(product.toEntity());
        return ProductResult.of(addedProduct);
    }

}

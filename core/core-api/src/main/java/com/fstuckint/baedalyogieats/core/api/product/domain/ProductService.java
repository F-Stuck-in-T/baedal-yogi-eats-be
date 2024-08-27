package com.fstuckint.baedalyogieats.core.api.product.domain;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRegister productRegister;

    private final ProductFinder productFinder;

    public ProductService(ProductRegister productRegister, ProductFinder productFinder) {
        this.productRegister = productRegister;
        this.productFinder = productFinder;
    }

    public ProductResult register(Product product) {
        return productRegister.add(product);
    }

    public ProductResult find(UUID productUuid) {
        return productFinder.find(productUuid);
    }

}

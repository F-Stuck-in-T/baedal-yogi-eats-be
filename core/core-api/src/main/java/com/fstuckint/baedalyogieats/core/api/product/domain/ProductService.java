package com.fstuckint.baedalyogieats.core.api.product.domain;

import com.fstuckint.baedalyogieats.core.api.product.support.Cursor;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRegister productRegister;

    private final ProductFinder productFinder;

    private final ProductReader productReader;

    public ProductService(ProductRegister productRegister, ProductFinder productFinder, ProductReader productReader) {
        this.productRegister = productRegister;
        this.productFinder = productFinder;
        this.productReader = productReader;
    }

    public ProductResult register(Product product) {
        return productRegister.add(product);
    }

    public ProductResult find(UUID productUuid) {
        return productFinder.find(productUuid);
    }

    public List<ProductResult> read(Cursor cursor) {
        return productReader.read(cursor);
    }

}

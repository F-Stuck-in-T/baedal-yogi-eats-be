package com.fstuckint.baedalyogieats.core.api.product.domain;

import com.fstuckint.baedalyogieats.core.api.product.support.Cursor;
import com.fstuckint.baedalyogieats.storage.db.core.product.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductReader {

    private final ProductRepository productRepository;

    public ProductReader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResult> read(Cursor cursor) {
        return productRepository
            .findByCursor(cursor.getUuid(), cursor.getTimestamp(), cursor.limit() + 1, cursor.sortKey(), cursor.sort())
            .stream()
            .map(ProductResult::of)
            .toList();
    }

}

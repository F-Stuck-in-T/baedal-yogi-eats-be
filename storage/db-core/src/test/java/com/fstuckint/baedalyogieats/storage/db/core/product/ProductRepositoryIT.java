package com.fstuckint.baedalyogieats.storage.db.core.product;

import com.fstuckint.baedalyogieats.storage.db.CoreDbContextTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

class ProductRepositoryIT extends CoreDbContextTest {

    private final ProductRepository productRepository;

    private UUID storeUuid;

    public ProductRepositoryIT(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @BeforeEach
    void setUp() {
        this.storeUuid = UUID.randomUUID();
    }

    @Test
    void 상품이_추가된_후_상품_UUID로_조회돼야_한다() {
        // given
        ProductEntity product = productRepository.add(new ProductEntity("상품", "상품 설명", 1000, storeUuid));

        // when
        ProductEntity addedProduct = productRepository.findByUuid(product.getUuid())
            .orElseThrow(() -> new AssertionError("Product not found"));

        // then
        assertThat(addedProduct).isNotNull();
        assertThat(addedProduct.getUuid()).isEqualTo(product.getUuid());
        assertThat(addedProduct.getName()).isEqualTo("상품");
        assertThat(addedProduct.getDescription()).isEqualTo("상품 설명");
        assertThat(addedProduct.getUnitPrice()).isEqualTo(1000);
        assertThat(addedProduct.getStoreUuid()).isEqualTo(storeUuid);
    }

}
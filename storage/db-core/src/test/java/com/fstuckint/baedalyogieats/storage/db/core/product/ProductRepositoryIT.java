package com.fstuckint.baedalyogieats.storage.db.core.product;

import static org.assertj.core.api.Assertions.assertThat;

import com.fstuckint.baedalyogieats.storage.db.CoreDbContextTest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

class ProductRepositoryIT extends CoreDbContextTest {

    private final ProductRepository productRepository;

    private UUID storeUuid;

    private List<ProductEntity> products;

    public ProductRepositoryIT(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @BeforeEach
    void setUp() {
        this.storeUuid = UUID.randomUUID();

        productRepository.deleteAll();

        products = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            ProductEntity product = new ProductEntity("상품 " + (i + 1), "상품 설명 " + (i + 1), 1000 * (i + 1),
                    UUID.randomUUID());
            products.add(product);
        }
        productRepository.saveAll(products);
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

    @Test
    void 상품_UUID와_타임스탬프_기반_커서로_상품_목록의_첫_번째_페이지를_조회한다() {
        // given
        final long limit = 10;
        String sortKey = "createdAt";
        Sort.Direction sort = Sort.Direction.ASC;

        // when
        List<ProductEntity> firstPage = productRepository.findByCursor(null, null, limit, sortKey, sort);

        // then
        assertThat(firstPage).hasSize(10);
        assertThat(firstPage.getFirst().getUuid()).isEqualTo(products.getFirst().getUuid());
        assertThat(firstPage.getLast().getUuid()).isEqualTo(products.get(9).getUuid());
    }

    @Test
    void 상품_UUID와_타임스탬프_기반_커서로_상품_목록의_두_번째_페이지를_조회한다() {
        // given
        final long limit = 10;
        String sortKey = "createdAt";
        Sort.Direction sort = Sort.Direction.ASC;

        // when
        List<ProductEntity> firstPage = productRepository.findByCursor(null, null, limit, sortKey, sort);
        ProductEntity lastProduct = firstPage.getLast();
        List<ProductEntity> secondPage = productRepository.findByCursor(lastProduct.getUuid(),
                lastProduct.getCreatedAt(), limit, sortKey, sort);

        // then
        assertThat(secondPage).hasSize(5);
        assertThat(secondPage.getFirst().getUuid()).isEqualTo(products.get(10).getUuid());
        assertThat(secondPage.getLast().getUuid()).isEqualTo(products.getLast().getUuid());
    }

    @Test
    void 상품_UUID와_타임스탬프_기반_커서로_마지막_상품_이후_조회_시_빈_리스트가_반환되어야_한다() {
        // given
        final long limit = 10;
        String sortKey = "createdAt";
        Sort.Direction sort = Sort.Direction.ASC;
        ProductEntity lastProduct = products.getLast();

        // when
        List<ProductEntity> result = productRepository.findByCursor(lastProduct.getUuid(), lastProduct.getCreatedAt(),
                limit, sortKey, sort);

        // then
        assertThat(result).isEmpty();
    }

}
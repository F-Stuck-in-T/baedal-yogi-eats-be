package com.fstuckint.baedalyogieats.core.api.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fstuckint.baedalyogieats.core.api.product.support.Cursor;
import com.fstuckint.baedalyogieats.storage.db.core.product.ProductEntity;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductServiceTest {

    private UUID productUuid;

    private UUID storeUuid;

    @Mock
    private ProductRegister productRegister;

    @Mock
    private ProductFinder productFinder;

    @Mock
    private ProductReader productReader;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        this.productUuid = UUID.randomUUID();
        this.storeUuid = UUID.randomUUID();

        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRegister, productFinder, productReader);
    }

    @Test
    void 상품을_추가한다() {
        // given
        String name = "상품";
        String description = "상품 설명";
        int unitPrice = 10_000;
        Product product = new Product(name, description, unitPrice, storeUuid);
        ProductEntity productEntity = product.toEntity();
        ReflectionTestUtils.setField(productEntity, "uuid", productUuid);
        when(productRegister.add(product)).thenReturn(ProductResult.of(productEntity));

        // when
        ProductResult result = productService.register(product);

        // then
        assertThat(result).isNotNull();
        assertThat(result.uuid()).isEqualTo(productUuid);
        assertThat(result.name()).isEqualTo(name);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.unitPrice()).isEqualTo(unitPrice);
        assertThat(result.storeUuid()).isEqualTo(storeUuid);
    }

    @Test
    void 상품을_조회한다() {
        // given
        String name = "상품";
        String description = "상품 설명";
        int unitPrice = 10_000;
        Product product = new Product(name, description, unitPrice, storeUuid);
        ProductEntity productEntity = product.toEntity();
        ReflectionTestUtils.setField(productEntity, "uuid", productUuid);
        when(productFinder.find(productUuid)).thenReturn(ProductResult.of(productEntity));

        // when
        ProductResult result = productService.find(productUuid);

        // then
        assertThat(result).isNotNull();
        assertThat(result.uuid()).isEqualTo(productUuid);
        assertThat(result.name()).isEqualTo(name);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.unitPrice()).isEqualTo(unitPrice);
        assertThat(result.storeUuid()).isEqualTo(storeUuid);
    }

    @Test
    void 상품_목록을_조회한다() {
        // given
        Cursor cursor = new Cursor(null, 10, "createdAt", Sort.Direction.ASC);
        List<ProductResult> expectedResults = List.of(
                new ProductResult(UUID.randomUUID(), "상품1", "설명1", 1000, UUID.randomUUID(), null, null),
                new ProductResult(UUID.randomUUID(), "상품2", "설명2", 2000, UUID.randomUUID(), null, null));
        when(productReader.read(cursor)).thenReturn(expectedResults);

        // when
        List<ProductResult> results = productService.read(cursor);

        // then
        assertThat(results).isEqualTo(expectedResults);
        verify(productReader).read(cursor);
    }

}
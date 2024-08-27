package com.fstuckint.baedalyogieats.core.api.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fstuckint.baedalyogieats.storage.db.core.product.ProductEntity;
import com.fstuckint.baedalyogieats.storage.db.core.product.ProductRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductFinderTest {

    private UUID productUuid;

    private UUID storeUuid;

    @Mock
    private ProductRepository productRepository;

    private ProductFinder productFinder;

    @BeforeEach
    void setUp() {
        this.productUuid = UUID.randomUUID();
        this.storeUuid = UUID.randomUUID();

        MockitoAnnotations.openMocks(this);
        productFinder = new ProductFinder(productRepository);
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
        when(productRepository.findByUuid(productUuid)).thenReturn(Optional.of(productEntity));

        // when
        ProductResult result = productFinder.find(productUuid);

        // then
        assertThat(result).isNotNull();
        assertThat(result.uuid()).isEqualTo(productUuid);
        assertThat(result.name()).isEqualTo(name);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.unitPrice()).isEqualTo(unitPrice);
        assertThat(result.storeUuid()).isEqualTo(storeUuid);
    }

}
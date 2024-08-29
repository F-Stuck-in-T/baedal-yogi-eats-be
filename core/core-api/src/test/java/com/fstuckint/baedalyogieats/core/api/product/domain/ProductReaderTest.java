package com.fstuckint.baedalyogieats.core.api.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fstuckint.baedalyogieats.core.api.product.support.Cursor;
import com.fstuckint.baedalyogieats.storage.db.core.product.ProductEntity;
import com.fstuckint.baedalyogieats.storage.db.core.product.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductReaderTest {

    @Mock
    private ProductRepository productRepository;

    private ProductReader productReader;

    private List<ProductEntity> products;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productReader = new ProductReader(productRepository);

        // 15개의 테스트용 상품 데이터 생성
        products = IntStream.rangeClosed(1, 15)
            .mapToObj(i -> new ProductEntity("상품" + i, "설명" + i, i * 1000, UUID.randomUUID()))
            .toList();
    }

    @Test
    void 커서가_없을_때_첫_페이지_상품_목록을_조회한다() {
        // given
        final long limit = 10;
        final String sortKey = "createdAt";
        final Sort.Direction sort = Sort.Direction.ASC;
        Cursor cursor = new Cursor(null, limit, sortKey, sort);
        when(productRepository.findByCursor(cursor.getUuid(), cursor.getTimestamp(), cursor.limit() + 1,
                cursor.sortKey(), cursor.sort()))
            .thenReturn(products.subList(0, (int) limit));

        // when
        List<ProductResult> results = productReader.read(cursor);

        // then
        assertThat(results).hasSize((int) limit);
        assertThat(results.getFirst().name()).isEqualTo("상품1");
        assertThat(results.getFirst().unitPrice()).isEqualTo(1000);
        assertThat(results.getLast().name()).isEqualTo("상품10");
        assertThat(results.getLast().unitPrice()).isEqualTo(10000);
    }

    @Test
    void 커서가_있을_때_다음_페이지의_상품_목록을_조회한다() {
        // given
        final long limit = 10;
        final String sortKey = "createdAt";
        final Sort.Direction sort = Sort.Direction.ASC;
        UUID lastUuid = UUID.randomUUID();
        LocalDateTime lastTimestamp = LocalDateTime.now();
        Cursor cursor = new Cursor(Cursor.encodeCursor(lastUuid, lastTimestamp), limit, sortKey, sort);
        when(productRepository.findByCursor(lastUuid, lastTimestamp, limit + 1, sortKey, sort))
            .thenReturn(products.subList(10, 15));

        // when
        List<ProductResult> results = productReader.read(cursor);

        // then
        assertThat(results).hasSize(5);
        assertThat(results.get(0).name()).isEqualTo("상품11");
        assertThat(results.get(0).unitPrice()).isEqualTo(11000);
        assertThat(results.get(4).name()).isEqualTo("상품15");
        assertThat(results.get(4).unitPrice()).isEqualTo(15000);
    }

    @Test
    void 마지막_페이지_이후_상품_목록을_조회하면_빈_목록이_반환된다() {
        // given
        final long limit = 10;
        final String sortKey = "createdAt";
        final Sort.Direction sort = Sort.Direction.ASC;
        UUID lastUuid = UUID.randomUUID();
        LocalDateTime lastTimestamp = LocalDateTime.now();
        Cursor cursor = new Cursor(Cursor.encodeCursor(lastUuid, lastTimestamp), limit, sortKey, sort);
        when(productRepository.findByCursor(lastUuid, lastTimestamp, limit + 1, sortKey, sort)).thenReturn(List.of());

        // when
        List<ProductResult> results = productReader.read(cursor);

        // then
        assertThat(results).isEmpty();
    }

}
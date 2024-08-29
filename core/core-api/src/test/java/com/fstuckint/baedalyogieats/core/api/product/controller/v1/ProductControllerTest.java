package com.fstuckint.baedalyogieats.core.api.product.controller.v1;

import static com.fstuckint.baedalyogieats.test.api.RestDocsUtils.requestPreprocessor;
import static com.fstuckint.baedalyogieats.test.api.RestDocsUtils.responsePreprocessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fstuckint.baedalyogieats.core.api.product.controller.v1.request.ProductRegisterRequest;
import com.fstuckint.baedalyogieats.core.api.product.domain.Product;
import com.fstuckint.baedalyogieats.core.api.product.domain.ProductResult;
import com.fstuckint.baedalyogieats.core.api.product.domain.ProductService;
import com.fstuckint.baedalyogieats.core.api.product.support.Cursor;
import com.fstuckint.baedalyogieats.test.api.RestDocsTest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

class ProductControllerTest extends RestDocsTest {

    private UUID productUuid;

    private UUID storeUuid;

    private ProductService productService;

    private ProductController productController;

    @BeforeEach
    void setUp() {
        this.productUuid = UUID.randomUUID();
        this.storeUuid = UUID.randomUUID();
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
        mockMvc = mockController(productController);
    }

    @Test
    void 상품_등록() throws JsonProcessingException {
        // given
        String name = "상품";
        String description = "상품 설명";
        int unitPrice = 10_000;

        Product product = new Product(name, description, unitPrice, storeUuid);
        ProductResult productResult = new ProductResult(productUuid, name, description, unitPrice, storeUuid,
                LocalDateTime.now(), LocalDateTime.now());
        when(productService.register(product)).thenReturn(productResult);

        ProductRegisterRequest request = new ProductRegisterRequest(name, description, unitPrice, storeUuid.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(request);

        given().contentType("application/json")
            .body(requestBody)
            .post("/api/v1/products")
            .then()
            .status(HttpStatus.OK)
            .apply(document("상품 등록", requestPreprocessor(), responsePreprocessor(),
                    requestFields(fieldWithPath("name").type(JsonFieldType.STRING).description("상품명"),
                            fieldWithPath("description").type(JsonFieldType.STRING).description("상품 설명"),
                            fieldWithPath("unitPrice").type(JsonFieldType.NUMBER).description("가격"),
                            fieldWithPath("storeUuid").type(JsonFieldType.STRING).description("가게 UUID")),
                    responseFields(fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                            fieldWithPath("data.uuid").type(JsonFieldType.STRING).description("상품 UUID"),
                            fieldWithPath("data.name").type(JsonFieldType.STRING).description("상품명"),
                            fieldWithPath("data.description").type(JsonFieldType.STRING).description("상품 설명"),
                            fieldWithPath("data.unitPrice").type(JsonFieldType.NUMBER).description("가격"),
                            fieldWithPath("data.storeUuid").type(JsonFieldType.STRING).description("가게 UUID"),
                            fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("상품 생성 시간"),
                            fieldWithPath("data.updatedAt").type(JsonFieldType.STRING).description("상품 수정 시간"),
                            fieldWithPath("error").type(JsonFieldType.NULL).ignored())));
    }

    @Test
    void 상품_상세_조회() {
        ProductResult productResult = new ProductResult(productUuid, "상품", "상품 설명", 10_000, storeUuid,
                LocalDateTime.now(), LocalDateTime.now());
        when(productService.find(productUuid)).thenReturn(productResult);

        given().contentType("application/json")
            .get("/api/v1/products/{productUuid}", productUuid.toString())
            .then()
            .status(HttpStatus.OK)
            .apply(document("상품 상세 조회", requestPreprocessor(), responsePreprocessor(),
                    responseFields(fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                            fieldWithPath("data.uuid").type(JsonFieldType.STRING).description("상품 UUID"),
                            fieldWithPath("data.name").type(JsonFieldType.STRING).description("상품명"),
                            fieldWithPath("data.description").type(JsonFieldType.STRING).description("상품 설명"),
                            fieldWithPath("data.unitPrice").type(JsonFieldType.NUMBER).description("가격"),
                            fieldWithPath("data.storeUuid").type(JsonFieldType.STRING).description("가게 UUID"),
                            fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("상품 생성 시간"),
                            fieldWithPath("data.updatedAt").type(JsonFieldType.STRING).description("상품 수정 시간"),
                            fieldWithPath("error").type(JsonFieldType.NULL).ignored())));
    }

    @Test
    void 빈_커서로_상품_목록_조회() {
        UUID storeUuid = UUID.randomUUID();
        List<ProductResult> productResults = List.of(
                new ProductResult(UUID.randomUUID(), "상품1", "설명1", 10000, storeUuid, LocalDateTime.now(),
                        LocalDateTime.now()),
                new ProductResult(UUID.randomUUID(), "상품2", "설명2", 20000, storeUuid, LocalDateTime.now(),
                        LocalDateTime.now()));

        when(productService.read(any(Cursor.class))).thenReturn(productResults);

        given().param("cursor", "")
            .param("limit", 10)
            .param("sortKey", "createdAt")
            .param("sort", Sort.Direction.ASC.name())
            .get("/api/v1/products")
            .then()
            .status(HttpStatus.OK)
            .apply(document("상품 목록 조회 (빈 커서)", requestPreprocessor(), responsePreprocessor(),
                    queryParameters(parameterWithName("cursor").description("커서 (첫 페이지: null)").optional(),
                            parameterWithName("limit").description("페이지 크기").optional(),
                            parameterWithName("sortKey").description("정렬 키").optional(),
                            parameterWithName("sort").description("정렬 방향 (ASC / DESC)").optional()),
                    responseFields(fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                            fieldWithPath("data.result").type(JsonFieldType.ARRAY).description("상품 목록"),
                            fieldWithPath("data.result[].uuid").type(JsonFieldType.STRING).description("상품 UUID"),
                            fieldWithPath("data.result[].name").type(JsonFieldType.STRING).description("상품명"),
                            fieldWithPath("data.result[].description").type(JsonFieldType.STRING).description("상품 설명"),
                            fieldWithPath("data.result[].unitPrice").type(JsonFieldType.NUMBER).description("가격"),
                            fieldWithPath("data.result[].storeUuid").type(JsonFieldType.STRING).description("가게 UUID"),
                            fieldWithPath("data.result[].createdAt").type(JsonFieldType.STRING).description("상품 생성 시간"),
                            fieldWithPath("data.result[].updatedAt").type(JsonFieldType.STRING).description("상품 수정 시간"),
                            fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                            fieldWithPath("data.nextCursor").type(JsonFieldType.NULL)
                                .ignored()
                                .description("다음 페이지 커서"),
                            fieldWithPath("error").type(JsonFieldType.NULL).ignored())));

        verify(productService).read(any(Cursor.class));
    }

    @Test
    void 커서로_상품_목록_조회() {
        UUID storeUuid = UUID.randomUUID();
        List<ProductResult> productResults = IntStream.rangeClosed(1, 11)
            .mapToObj(i -> new ProductResult(UUID.randomUUID(), "상품" + i, "설명" + i, i * 10000, storeUuid,
                    LocalDateTime.now().minusHours(i), LocalDateTime.now().minusHours(i)))
            .toList();
        UUID lastProductUuid = UUID.randomUUID();
        LocalDateTime lastProductTimestamp = LocalDateTime.now().minusHours(12);
        String cursor = lastProductUuid + "_" + lastProductTimestamp;

        when(productService.read(any(Cursor.class))).thenReturn(productResults);

        given().param("cursor", cursor)
            .param("limit", 10)
            .param("sortKey", "createdAt")
            .param("sort", Sort.Direction.DESC.name())
            .get("/api/v1/products")
            .then()
            .status(HttpStatus.OK)
            .apply(document("상품 목록 조회 (커서)", requestPreprocessor(), responsePreprocessor(),
                    queryParameters(parameterWithName("cursor").description("커서 (UUID_타임스탬프 형식)"),
                            parameterWithName("limit").description("페이지 크기"),
                            parameterWithName("sortKey").description("정렬 키"),
                            parameterWithName("sort").description("정렬 방향 (ASC / DESC)")),
                    responseFields(fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                            fieldWithPath("data.result").type(JsonFieldType.ARRAY).description("상품 목록"),
                            fieldWithPath("data.result[].uuid").type(JsonFieldType.STRING).description("상품 UUID"),
                            fieldWithPath("data.result[].name").type(JsonFieldType.STRING).description("상품명"),
                            fieldWithPath("data.result[].description").type(JsonFieldType.STRING).description("상품 설명"),
                            fieldWithPath("data.result[].unitPrice").type(JsonFieldType.NUMBER).description("가격"),
                            fieldWithPath("data.result[].storeUuid").type(JsonFieldType.STRING).description("가게 UUID"),
                            fieldWithPath("data.result[].createdAt").type(JsonFieldType.STRING).description("상품 생성 시간"),
                            fieldWithPath("data.result[].updatedAt").type(JsonFieldType.STRING).description("상품 수정 시간"),
                            fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                            fieldWithPath("data.nextCursor").type(JsonFieldType.STRING).description("다음 페이지 커서"),
                            fieldWithPath("error").type(JsonFieldType.NULL).ignored())));

        verify(productService).read(any(Cursor.class));
    }

}

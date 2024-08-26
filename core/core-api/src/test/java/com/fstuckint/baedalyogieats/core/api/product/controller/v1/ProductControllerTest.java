package com.fstuckint.baedalyogieats.core.api.product.controller.v1;

import static com.fstuckint.baedalyogieats.test.api.RestDocsUtils.requestPreprocessor;
import static com.fstuckint.baedalyogieats.test.api.RestDocsUtils.responsePreprocessor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fstuckint.baedalyogieats.core.api.product.controller.v1.request.ProductRegisterRequest;
import com.fstuckint.baedalyogieats.core.api.product.domain.Product;
import com.fstuckint.baedalyogieats.core.api.product.domain.ProductResult;
import com.fstuckint.baedalyogieats.core.api.product.domain.ProductService;
import com.fstuckint.baedalyogieats.test.api.RestDocsTest;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
		ProductResult productResult = new ProductResult(productUuid, name, description, unitPrice, storeUuid);
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
							fieldWithPath("error").type(JsonFieldType.NULL).ignored())));
	}

	@Test
	void 상품_상세_조회() {
		ProductResult productResult = new ProductResult(productUuid, "상품", "상품 설명", 10_000, storeUuid);
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
							fieldWithPath("error").type(JsonFieldType.NULL).ignored())));
	}

}

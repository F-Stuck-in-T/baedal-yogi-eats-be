package com.fstuckint.baedalyogieats.core.api.product.controller.v1;

import static com.fstuckint.baedalyogieats.test.api.RestDocsUtils.requestPreprocessor;
import static com.fstuckint.baedalyogieats.test.api.RestDocsUtils.responsePreprocessor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import com.fstuckint.baedalyogieats.core.api.product.controller.v1.request.ExampleRequestDto;
import com.fstuckint.baedalyogieats.core.api.product.domain.ExampleResult;
import com.fstuckint.baedalyogieats.core.api.product.domain.ExampleProductService;
import com.fstuckint.baedalyogieats.test.api.RestDocsTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

public class ExampleProductControllerTest extends RestDocsTest {

	private ExampleProductService exampleProductService;

	private ExampleProductController controller;

	@BeforeEach
	public void setUp() {
		exampleProductService = mock(ExampleProductService.class);
		controller = new ExampleProductController(exampleProductService);
		mockMvc = mockController(controller);
	}

	@Test
    public void exampleGet() {
        when(exampleProductService.processExample(any())).thenReturn(new ExampleResult("BYE"));

        given().contentType(ContentType.JSON)
                .queryParam("exampleParam", "HELLO_PARAM")
                .get("/get/{exampleValue}", "HELLO_PATH")
                .then()
                .status(HttpStatus.OK)
                .apply(
                        document(
                                "exampleGet",
                                requestPreprocessor(),
                                responsePreprocessor(),
                                pathParameters(
                                        parameterWithName("exampleValue")
                                                .description("ExampleValue")),
                                queryParameters(
                                        parameterWithName("exampleParam")
                                                .description("ExampleParam")),
                                responseFields(
                                        fieldWithPath("result")
                                                .type(JsonFieldType.STRING)
                                                .description("ResultType"),
                                        fieldWithPath("data.result")
                                                .type(JsonFieldType.STRING)
                                                .description("Result Date"),
                                        fieldWithPath("error")
                                                .type(JsonFieldType.NULL)
                                                .ignored())));
    }

	@Test
    public void examplePost() {
        when(exampleProductService.processExample(any())).thenReturn(new ExampleResult("BYE"));

        given().contentType(ContentType.JSON)
                .body(new ExampleRequestDto("HELLO_BODY"))
                .post("/post")
                .then()
                .status(HttpStatus.OK)
                .apply(
                        document(
                                "examplePost",
                                requestPreprocessor(),
                                responsePreprocessor(),
                                requestFields(
                                        fieldWithPath("data")
                                                .type(JsonFieldType.STRING)
                                                .description("ExampleBody Data Field")),
                                responseFields(
                                        fieldWithPath("result")
                                                .type(JsonFieldType.STRING)
                                                .description("ResultType"),
                                        fieldWithPath("data.result")
                                                .type(JsonFieldType.STRING)
                                                .description("Result Date"),
                                        fieldWithPath("error")
                                                .type(JsonFieldType.STRING)
                                                .ignored())));
    }

}

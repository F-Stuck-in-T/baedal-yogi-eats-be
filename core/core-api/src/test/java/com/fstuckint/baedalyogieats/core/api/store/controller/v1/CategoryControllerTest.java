package com.fstuckint.baedalyogieats.core.api.store.controller.v1;

import static com.fstuckint.baedalyogieats.test.api.RestDocsUtils.requestPreprocessor;
import static com.fstuckint.baedalyogieats.test.api.RestDocsUtils.responsePreprocessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fstuckint.baedalyogieats.core.api.store.controller.v1.request.CategoryRegisterRequest;
import com.fstuckint.baedalyogieats.core.api.store.domain.Category;
import com.fstuckint.baedalyogieats.core.api.store.domain.CategoryResult;
import com.fstuckint.baedalyogieats.core.api.store.domain.CategoryService;
import com.fstuckint.baedalyogieats.test.api.RestDocsTest;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

class CategoryControllerTest extends RestDocsTest {

    private UUID categoryUuid;

    private CategoryService categoryService;

    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        this.categoryUuid = UUID.randomUUID();

        categoryService = mock(CategoryService.class);
        categoryController = new CategoryController(categoryService);
        mockMvc = mockController(categoryController);
    }

    @Test
    void 카테고리_등록() throws JsonProcessingException {
        // given
        String name = "한식";

        CategoryResult categoryResult = new CategoryResult(categoryUuid, name, LocalDateTime.now(),
                LocalDateTime.now());
        when(categoryService.register(any(Category.class))).thenReturn(categoryResult);

        CategoryRegisterRequest request = new CategoryRegisterRequest(name);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(request);

        given().contentType("application/json")
            .body(requestBody)
            .post("/api/v1/category")
            .then()
            .status(HttpStatus.OK)
            .apply(document("카테고리 등록", requestPreprocessor(), responsePreprocessor(),
                    requestFields(fieldWithPath("name").type(JsonFieldType.STRING).description("카테고리명")),
                    responseFields(fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                            fieldWithPath("data.uuid").type(JsonFieldType.STRING).description("카테고리 UUID"),
                            fieldWithPath("data.name").type(JsonFieldType.STRING).description("카테고리명"),
                            fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("카테고리 생성 시간"),
                            fieldWithPath("data.updatedAt").type(JsonFieldType.STRING).description("카테고리 수정 시간"),
                            fieldWithPath("error").type(JsonFieldType.NULL).ignored())));
    }

}
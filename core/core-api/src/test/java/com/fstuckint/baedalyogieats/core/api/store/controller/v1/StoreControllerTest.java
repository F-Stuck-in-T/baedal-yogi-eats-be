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
import com.fstuckint.baedalyogieats.core.api.store.controller.v1.request.StoreRegisterRequest;
import com.fstuckint.baedalyogieats.core.api.store.domain.OwnerStore;
import com.fstuckint.baedalyogieats.core.api.store.domain.StoreResult;
import com.fstuckint.baedalyogieats.core.api.store.domain.StoreService;
import com.fstuckint.baedalyogieats.test.api.RestDocsTest;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

class StoreControllerTest extends RestDocsTest {

    private UUID ownerUuid;

    private UUID categoryUuid;

    private UUID storeUuid;

    private StoreService storeService;

    private StoreController storeController;

    @BeforeEach
    void setUp() {
        this.ownerUuid = UUID.randomUUID();
        this.categoryUuid = UUID.randomUUID();
        this.storeUuid = UUID.randomUUID();

        storeService = mock(StoreService.class);
        storeController = new StoreController(storeService);
        mockMvc = mockController(storeController);
    }

    @Test
    void 가게_등록() throws JsonProcessingException {
        // given
        String name = "상호명";
        String description = "가게 설명";
        String fullAddress = "주소";
        String categoryName = "한식";

        StoreResult storeResult = new StoreResult(storeUuid, name, description, fullAddress, categoryUuid, categoryName,
                ownerUuid, LocalDateTime.now(), LocalDateTime.now());
        when(storeService.register(any(OwnerStore.class))).thenReturn(storeResult);

        StoreRegisterRequest request = new StoreRegisterRequest(name, description, fullAddress,
                categoryUuid.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(request);

        given().contentType("application/json")
            .body(requestBody)
            .post("/api/v1/stores")
            .then()
            .status(HttpStatus.OK)
            .apply(document("가게 등록", requestPreprocessor(), responsePreprocessor(),
                    requestFields(fieldWithPath("name").type(JsonFieldType.STRING).description("상호명"),
                            fieldWithPath("description").type(JsonFieldType.STRING).description("가게 설명"),
                            fieldWithPath("fullAddress").type(JsonFieldType.STRING).description("가게 주소"),
                            fieldWithPath("categoryUuid").type(JsonFieldType.STRING).description("카테고리 UUID")),
                    responseFields(fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                            fieldWithPath("data.uuid").type(JsonFieldType.STRING).description("가게 UUID"),
                            fieldWithPath("data.name").type(JsonFieldType.STRING).description("상호명"),
                            fieldWithPath("data.description").type(JsonFieldType.STRING).description("가게 설명"),
                            fieldWithPath("data.fullAddress").type(JsonFieldType.STRING).description("가게 주소"),
                            fieldWithPath("data.categoryUuid").type(JsonFieldType.STRING).description("카테고리 UUID"),
                            fieldWithPath("data.categoryName").type(JsonFieldType.STRING).description("카테고리명"),
                            fieldWithPath("data.ownerUuid").type(JsonFieldType.STRING).description("사장님 UUID"),
                            fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("가게 생성 시간"),
                            fieldWithPath("data.updatedAt").type(JsonFieldType.STRING).description("가게 수정 시간"),
                            fieldWithPath("error").type(JsonFieldType.NULL).ignored())));
    }

    @Test
    void 가게_상세_조회() {
        // given
        String name = "상호명";
        String description = "가게 설명";
        String fullAddress = "주소";
        String categoryName = "한식";

        StoreResult storeResult = new StoreResult(storeUuid, name, description, fullAddress, categoryUuid, categoryName,
                ownerUuid, LocalDateTime.now(), LocalDateTime.now());
        when(storeService.find(storeUuid)).thenReturn(storeResult);

        given().contentType("application/json")
            .get("/api/v1/stores/{storeUuid}", storeUuid.toString())
            .then()
            .status(HttpStatus.OK)
            .apply(document("가게 상세 조회", requestPreprocessor(), responsePreprocessor(),
                    responseFields(fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                            fieldWithPath("data.uuid").type(JsonFieldType.STRING).description("가게 UUID"),
                            fieldWithPath("data.name").type(JsonFieldType.STRING).description("상호명"),
                            fieldWithPath("data.description").type(JsonFieldType.STRING).description("가게 설명"),
                            fieldWithPath("data.fullAddress").type(JsonFieldType.STRING).description("가게 주소"),
                            fieldWithPath("data.categoryUuid").type(JsonFieldType.STRING).description("카테고리 UUID"),
                            fieldWithPath("data.categoryName").type(JsonFieldType.STRING).description("카테고리명"),
                            fieldWithPath("data.ownerUuid").type(JsonFieldType.STRING).description("사장님 UUID"),
                            fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("가게 생성 시간"),
                            fieldWithPath("data.updatedAt").type(JsonFieldType.STRING).description("가게 수정 시간"),
                            fieldWithPath("error").type(JsonFieldType.NULL).ignored())));
    }

}
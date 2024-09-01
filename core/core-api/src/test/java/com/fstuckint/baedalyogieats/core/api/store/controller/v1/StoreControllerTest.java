package com.fstuckint.baedalyogieats.core.api.store.controller.v1;

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
import com.fstuckint.baedalyogieats.core.api.store.controller.v1.request.StoreRegisterRequest;
import com.fstuckint.baedalyogieats.core.api.store.domain.OwnerStore;
import com.fstuckint.baedalyogieats.core.api.store.domain.StoreResult;
import com.fstuckint.baedalyogieats.core.api.store.domain.StoreService;
import com.fstuckint.baedalyogieats.core.api.store.support.Cursor;
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

    @Test
    void 빈_커서로_가게_목록_조회() {
        List<StoreResult> storeResults = List.of(
                new StoreResult(UUID.randomUUID(), "가게1", "가게 설명", "주소", UUID.randomUUID(), "한식", UUID.randomUUID(),
                        LocalDateTime.now(), LocalDateTime.now()),
                new StoreResult(UUID.randomUUID(), "가게2", "가게 설명", "주소", UUID.randomUUID(), "중식", UUID.randomUUID(),
                        LocalDateTime.now(), LocalDateTime.now()));

        when(storeService.read(any(Cursor.class))).thenReturn(storeResults);

        given().param("cursor", "")
            .param("limit", 10)
            .param("sortKey", "createdAt")
            .param("sort", Sort.Direction.ASC.name())
            .get("/api/v1/stores")
            .then()
            .status(HttpStatus.OK)
            .apply(document("가게 목록 조회 (빈 커서)", requestPreprocessor(), responsePreprocessor(),
                    queryParameters(parameterWithName("cursor").description("커서 (첫 페이지: null)").optional(),
                            parameterWithName("limit").description("페이지 크기").optional(),
                            parameterWithName("sortKey").description("정렬 키").optional(),
                            parameterWithName("sort").description("정렬 방향 (ASC / DESC)").optional()),
                    responseFields(fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                            fieldWithPath("data.result").type(JsonFieldType.ARRAY).description("가게 목록"),
                            fieldWithPath("data.result[].uuid").type(JsonFieldType.STRING).description("가게 UUID"),
                            fieldWithPath("data.result[].name").type(JsonFieldType.STRING).description("상호명"),
                            fieldWithPath("data.result[].description").type(JsonFieldType.STRING).description("가게 설명"),
                            fieldWithPath("data.result[].fullAddress").type(JsonFieldType.STRING).description("주소"),
                            fieldWithPath("data.result[].categoryUuid").type(JsonFieldType.STRING)
                                .description("카테고리 UUID"),
                            fieldWithPath("data.result[].categoryName").type(JsonFieldType.STRING).description("카테고리명"),
                            fieldWithPath("data.result[].ownerUuid").type(JsonFieldType.STRING).description("사장님 UUID"),
                            fieldWithPath("data.result[].createdAt").type(JsonFieldType.STRING).description("가게 생성 시간"),
                            fieldWithPath("data.result[].updatedAt").type(JsonFieldType.STRING).description("가게 수정 시간"),
                            fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                            fieldWithPath("data.nextCursor").type(JsonFieldType.NULL)
                                .ignored()
                                .description("다음 페이지 커서"),
                            fieldWithPath("error").type(JsonFieldType.NULL).ignored())));

        verify(storeService).read(any(Cursor.class));
    }

    @Test
    void 커서로_가게_목록_조회() {
        List<StoreResult> storeResults = IntStream.rangeClosed(1, 11)
            .mapToObj(i -> new StoreResult(UUID.randomUUID(), "가게" + i, "가게 설명", "주소", UUID.randomUUID(), "한식",
                    UUID.randomUUID(), LocalDateTime.now().minusHours(i), LocalDateTime.now().minusHours(i)))
            .toList();
        UUID lastStoreUuid = UUID.randomUUID();
        LocalDateTime lastStoreTimestamp = LocalDateTime.now().minusHours(12);
        String cursor = lastStoreUuid + "_" + lastStoreTimestamp;

        when(storeService.read(any(Cursor.class))).thenReturn(storeResults);

        given().param("cursor", cursor)
            .param("limit", 10)
            .param("sortKey", "createdAt")
            .param("sort", Sort.Direction.DESC.name())
            .get("/api/v1/stores")
            .then()
            .status(HttpStatus.OK)
            .apply(document("가게 목록 조회 (커서)", requestPreprocessor(), responsePreprocessor(),
                    queryParameters(parameterWithName("cursor").description("커서 (UUID_타임스탬프 형식)"),
                            parameterWithName("limit").description("페이지 크기"),
                            parameterWithName("sortKey").description("정렬 키"),
                            parameterWithName("sort").description("정렬 방향 (ASC / DESC)")),
                    responseFields(fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                            fieldWithPath("data.result").type(JsonFieldType.ARRAY).description("가게 목록"),
                            fieldWithPath("data.result[].uuid").type(JsonFieldType.STRING).description("가게 UUID"),
                            fieldWithPath("data.result[].name").type(JsonFieldType.STRING).description("상호명"),
                            fieldWithPath("data.result[].description").type(JsonFieldType.STRING).description("가게 설명"),
                            fieldWithPath("data.result[].fullAddress").type(JsonFieldType.STRING).description("주소"),
                            fieldWithPath("data.result[].categoryUuid").type(JsonFieldType.STRING)
                                .description("카테고리 UUID"),
                            fieldWithPath("data.result[].categoryName").type(JsonFieldType.STRING).description("카테고리명"),
                            fieldWithPath("data.result[].ownerUuid").type(JsonFieldType.STRING).description("사장님 UUID"),
                            fieldWithPath("data.result[].createdAt").type(JsonFieldType.STRING).description("가게 생성 시간"),
                            fieldWithPath("data.result[].updatedAt").type(JsonFieldType.STRING).description("가게 수정 시간"),
                            fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                            fieldWithPath("data.nextCursor").type(JsonFieldType.STRING).description("다음 페이지 커서"),
                            fieldWithPath("error").type(JsonFieldType.NULL).ignored())));

        verify(storeService).read(any(Cursor.class));
    }

}
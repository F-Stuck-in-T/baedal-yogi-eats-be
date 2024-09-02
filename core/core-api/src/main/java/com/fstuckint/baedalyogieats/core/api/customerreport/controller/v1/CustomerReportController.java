package com.fstuckint.baedalyogieats.core.api.customerreport.controller.v1;

import com.fstuckint.baedalyogieats.core.api.ai.support.response.ApiResponse;
import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.customerreport.controller.v1.request.AdminAnswerRequest;
import com.fstuckint.baedalyogieats.core.api.customerreport.controller.v1.request.CustomerReportRequest;
import com.fstuckint.baedalyogieats.core.api.customerreport.controller.v1.response.CustomerReportResponse;
import com.fstuckint.baedalyogieats.core.api.customerreport.domain.CustomerReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class CustomerReportController {

    private final CustomerReportService customerReportService;

    // CUSTOMER, OWNER 가능
    @PostMapping
    public ResponseEntity<ApiResponse<?>> requestQuestion(
            @RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String bearerToken,
            @RequestBody CustomerReportRequest customerReportRequest) {
        CustomerReportResponse data = customerReportService.requestQuestion(customerReportRequest, bearerToken);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    // 작성자 본인? 혹은 관리자!
    @GetMapping("/{reportUuid}")
    public ResponseEntity<ApiResponse<?>> getQuestion(@RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String bearerToken,
            @PathVariable UUID reportUuid) {
        CustomerReportResponse data = customerReportService.getQuestion(reportUuid, bearerToken);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    // 관리자 모든 조회
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getQuestionAll() {
        List<CustomerReportResponse> data = customerReportService.getQuestionList();
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    // 관리자 // 답변 제공
    @PostMapping("/{reportUuid}")
    public ResponseEntity<ApiResponse<?>> providerAnswer(@PathVariable UUID reportUuid,
            @RequestBody AdminAnswerRequest answerRequest) {
        CustomerReportResponse data = customerReportService.provideAnswer(reportUuid, answerRequest);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    // 작성자! 즉, customer 또는 owner ( 단, 답변이 달린 report 는 수정 불가능하다! )
    @PutMapping("/{reportUuid}")
    public ResponseEntity<ApiResponse<?>> editQuestion(@PathVariable UUID reportUuid,
            @RequestBody CustomerReportRequest customerReportRequest,
            @RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String bearerToken) {
        CustomerReportResponse data = customerReportService.editQuestion(reportUuid, customerReportRequest,
                bearerToken);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    // 작성자만 가능 또는 관리자
    @DeleteMapping("/{reportUuid}")
    public ResponseEntity<ApiResponse<?>> deleteQuestion(@PathVariable UUID reportUuid,
            @RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String bearerToken) {
        CustomerReportResponse data = customerReportService.deleteQuestion(reportUuid, bearerToken);
        return ResponseEntity.ok(ApiResponse.success());
    }

}

package com.fstuckint.baedalyogieats.core.api.payment.controller.v1;

import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.payment.controller.v1.request.PaymentRequest;
import com.fstuckint.baedalyogieats.core.api.payment.controller.v1.response.PaymentPageResponse;
import com.fstuckint.baedalyogieats.core.api.payment.controller.v1.response.PaymentResponse;
import com.fstuckint.baedalyogieats.core.api.payment.domain.PaymentService;
import com.fstuckint.baedalyogieats.core.api.user.support.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> requestPayment(@RequestBody PaymentRequest paymentRequestDto,
            @RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String bearerToken) {
        PaymentResponse data = paymentService.requestPayment(paymentRequestDto, bearerToken);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @GetMapping("/users/{userUuid}")
    public ResponseEntity<ApiResponse<?>> getPaymentListByUser(@PathVariable UUID userUuid,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime cursor,
            @RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "id") String sortKey,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String bearerToken) {
        PaymentPageResponse data = paymentService.getPaymentListByUser(userUuid, cursor, limit, sortKey, direction,
                bearerToken);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @GetMapping("/stores/{storeUuid}")
    public ResponseEntity<ApiResponse<?>> getPaymentListByOwner(@PathVariable UUID storeUuid,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime cursor,
            @RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "id") String sortKey,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String bearerToken) {
        PaymentPageResponse data = paymentService.getPaymentListByOwner(storeUuid, cursor, limit, sortKey, direction,
                bearerToken);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllPayment(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime cursor,
            @RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "id") String sortKey,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String bearerToken) {
        PaymentPageResponse data = paymentService.getAllPayment(cursor, limit, sortKey, direction, bearerToken);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @DeleteMapping("/{paymentUuid}")
    public ResponseEntity<ApiResponse<?>> cancelPayment(@PathVariable UUID paymentUuid,
            @RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String bearerToken) {
        PaymentResponse data = paymentService.cancelPayment(paymentUuid, bearerToken);
        return ResponseEntity.ok(ApiResponse.success());
    }

}

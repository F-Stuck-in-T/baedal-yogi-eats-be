package com.fstuckint.baedalyogieats.core.api.payment.domain;

import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.payment.controller.v1.request.PaymentRequest;
import com.fstuckint.baedalyogieats.core.api.payment.controller.v1.response.PaymentPageResponse;
import com.fstuckint.baedalyogieats.core.api.payment.controller.v1.response.PaymentResponse;
import com.fstuckint.baedalyogieats.core.api.payment.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.payment.support.error.PaymentException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentPilot paymentPilot;

    private final JwtUtils jwtUtils;

    @Transactional
    public PaymentResponse requestPayment(PaymentRequest dto, String bearerToken) {
        if (jwtUtils.checkAdmin(bearerToken))
            return PaymentResponse.of(paymentPilot.requestPaymentAdmin(dto));
        if (jwtUtils.checkCustomer(bearerToken))
            return PaymentResponse.of(paymentPilot.requestPayment(dto, bearerToken));
        throw new PaymentException(ErrorType.DEFAULT_ERROR);
    }

    @Transactional(readOnly = true)
    public PaymentPageResponse getPaymentListByUser(UUID userUuid, LocalDateTime cursor, Integer limit, String sortKey,
            String direction, String bearerToken) {
        if (jwtUtils.checkAdmin(bearerToken))
            return PaymentResult
                .of(paymentPilot.getPaymentListByUserAdmin(userUuid, cursor, getSortedPage(limit, sortKey, direction)));
        if (jwtUtils.checkCustomer(bearerToken))
            return PaymentResult.of(paymentPilot.getPaymentListByUserUuid(userUuid, cursor,
                    getSortedPage(limit, sortKey, direction), bearerToken));
        throw new PaymentException(ErrorType.DEFAULT_ERROR);
    }

    @Transactional(readOnly = true)
    public PaymentPageResponse getPaymentListByOwner(UUID storeUuid, LocalDateTime cursor, Integer limit,
            String sortKey, String direction, String bearerToken) {
        if (jwtUtils.checkAdmin(bearerToken))
            return PaymentResult.of(paymentPilot.getPaymentListByOwnerAdmin(storeUuid, cursor,
                    getSortedPage(limit, sortKey, direction)));
        if (jwtUtils.checkOwner(bearerToken))
            return PaymentResult.of(paymentPilot.getPaymentListByOwner(storeUuid, cursor,
                    getSortedPage(limit, sortKey, direction), bearerToken));
        throw new PaymentException(ErrorType.DEFAULT_ERROR);
    }

    @Transactional(readOnly = true)
    public PaymentPageResponse getAllPayment(LocalDateTime cursor, Integer limit, String sortKey, String direction,
            String bearToken) {
        if (jwtUtils.checkAdmin(bearToken))
            return PaymentResult.of(paymentPilot.getAllPayment(cursor, getSortedPage(limit, sortKey, direction)));
        throw new PaymentException(ErrorType.DEFAULT_ERROR);
    }

    @Transactional
    public PaymentResponse cancelPayment(UUID paymentUuid, String bearerToken) {
        if (jwtUtils.checkAdmin(bearerToken))
            return PaymentResponse.of(paymentPilot.cancelPayment(paymentUuid));
        throw new PaymentException(ErrorType.DEFAULT_ERROR);
    }

    private static PageRequest getSortedPage(Integer limit, String sortKey, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortKey);
        return PageRequest.of(0, limit, sort);
    }

}

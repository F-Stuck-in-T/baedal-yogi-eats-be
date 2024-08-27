package com.fstuckint.baedalyogieats.core.api.payment.domain;

import com.fstuckint.baedalyogieats.core.api.common.jwt.UserChecker;
import com.fstuckint.baedalyogieats.core.api.payment.controller.v1.request.PaymentRequest;
import com.fstuckint.baedalyogieats.core.api.payment.controller.v1.response.PaymentPageResponse;
import com.fstuckint.baedalyogieats.core.api.payment.controller.v1.response.PaymentResponse;
import com.fstuckint.baedalyogieats.core.api.payment.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.payment.support.error.PaymentException;
import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.user.support.response.ApiResponse;
import com.fstuckint.baedalyogieats.storage.db.core.payment.PaymentEntity;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentPilot paymentPilot;
    private final UserChecker userChecker;
    private final JwtUtils jwtUtils;


    @Transactional
    public PaymentResponse requestPayment(PaymentRequest dto, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        userChecker.checkTokenValid(token);
        userChecker.checkTokenNotOwner(token);
        return PaymentResponse.of(paymentPilot.requestPayment(dto));
    }

    @Transactional(readOnly = true)
    public PaymentPageResponse getPaymentListByUser(UUID userUuid, LocalDateTime cursor, Integer limit, String sortKey, String direction, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        userChecker.checkTokenValid(token);
        if (!userChecker.checkAdmin(token)) { userChecker.checkIdentityByUserUuid(token, userUuid); }
        return PaymentResult.of(paymentPilot.getPaymentListByUserUuid(userUuid, cursor, getSortedPage(limit, sortKey, direction)));
    }


    @Transactional(readOnly = true)
    public PaymentPageResponse getPaymentListByOwner(UUID storeUuid, LocalDateTime cursor, Integer limit, String sortKey, String direction, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        userChecker.checkTokenValid(token);

        return PaymentResult.of(paymentPilot.getPaymentListByStoreUuid(storeUuid, cursor, getSortedPage(limit, sortKey, direction), token));
    }

    @Transactional(readOnly = true)
    public PaymentPageResponse getAllPayment(LocalDateTime cursor, Integer limit, String sortKey, String direction, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        if (!userChecker.checkAdmin(token)) { throw new PaymentException(ErrorType.ROLE_ERROR); }
        return PaymentResult.of(paymentPilot.getAllPayment(cursor, getSortedPage(limit, sortKey, direction)));
    }

    @Transactional
    public void cancelPayment(UUID paymentUuid, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        if (!userChecker.checkAdmin(token)) { throw new PaymentException(ErrorType.ROLE_ERROR); }
        paymentPilot.cancelPayment(paymentUuid);
    }



    private static PageRequest getSortedPage(Integer limit, String sortKey, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortKey);
        return PageRequest.of(0, limit, sort);
    }
}

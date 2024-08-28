package com.fstuckint.baedalyogieats.core.api.payment.domain;

import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.payment.controller.v1.request.PaymentRequest;
import com.fstuckint.baedalyogieats.core.api.payment.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.payment.support.error.PaymentException;
import com.fstuckint.baedalyogieats.storage.db.core.payment.PaymentEntity;
import com.fstuckint.baedalyogieats.storage.db.core.payment.PaymentRepository;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRole;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentPilot {

    private final PaymentRepository paymentRepository;

    // private final OrderRepository orderRepository;
    // private final StoreRepository storeRepository;
    private final JwtUtils jwtUtils;

    public PaymentResult requestPayment(PaymentRequest dto) {
        Payment payment = dto.toPayment();
        // TODO: order Repo 에서 존재 유무 확인, total Price 가져와 payment 에 넣기

        PaymentEntity paymentEntity = payment.toEntity();
        return PaymentResult.of(paymentRepository.save(paymentEntity));
    }

    public Page<PaymentEntity> getPaymentListByUserUuid(UUID userUuid, LocalDateTime cursor, PageRequest sortedPage) {
        return paymentRepository.findAllByUserUuid(userUuid,
                cursor == null ? LocalDateTime.of(2000, 1, 1, 0, 0) : cursor, sortedPage);
    }

    public Page<PaymentEntity> getPaymentListByStoreUuid(UUID storeUuid, LocalDateTime cursor, PageRequest sortedPage,
            String token) {

        // TODO:
        // 1. store Repo 에서 storeUuid 와 일치하는 store 객체 찾기
        // - ( 없다면 Exception )
        // - ( 있다면 2. )
        // 2.a. 만약 UserRole.MASTER 또는 MANAGER 일 떄, 바로 query 날림.
        // 2.b. 만약 UserRole.OWNER 일 때, store 객체의 userUuid == token Uuid 동일 유무 확인 후 query
        // 날림

        Claims claims = jwtUtils.extractClaims(token);
        String role = claims.get(JwtUtils.CLAIMS_ROLE).toString();
        UUID userUuid = UUID.fromString(claims.get(JwtUtils.CLAIMS_UUID).toString());
        if (UserRole.CUSTOMER.getAuthority().equals(role))
            throw new PaymentException(ErrorType.ROLE_ERROR);
        else if (UserRole.OWNER.getAuthority().equals(role)) {
            // userUuid 와 store 객체의 userUuid 일치 유무 확인
            // if (userUuid != storeEntity.getUserUuid()) throw new
            // PaymentException(ErrorType.TOKEN_ERROR);
            return null;
        }
        else {
            return paymentRepository.findAllByStoreUuid(storeUuid,
                    cursor == null ? LocalDateTime.of(2000, 1, 1, 0, 0) : cursor, sortedPage);
        }
    }

    public Page<PaymentEntity> getAllPayment(LocalDateTime cursor, PageRequest sortedPage) {
        return paymentRepository.findAllByAdmin(cursor == null ? LocalDateTime.of(2000, 1, 1, 0, 0) : cursor,
                sortedPage);
    }

    public void cancelPayment(UUID paymentUuid) {
        PaymentEntity paymentEntity = paymentRepository.findByUuidAndIsCancelFalse(paymentUuid)
            .orElseThrow(() -> new PaymentException(ErrorType.NOT_FOUND_ERROR));
        paymentEntity.cancel();
    }

}

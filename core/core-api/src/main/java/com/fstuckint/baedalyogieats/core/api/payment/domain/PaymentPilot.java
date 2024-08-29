package com.fstuckint.baedalyogieats.core.api.payment.domain;

import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.payment.controller.v1.request.PaymentRequest;
import com.fstuckint.baedalyogieats.core.api.payment.controller.v1.response.PaymentPageResponse;
import com.fstuckint.baedalyogieats.core.api.payment.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.payment.support.error.PaymentException;
import com.fstuckint.baedalyogieats.storage.db.core.order.OrderEntity;
import com.fstuckint.baedalyogieats.storage.db.core.order.OrderRepository;
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

    private final OrderRepository orderRepository;

    // private final StoreRepository storeRepository;
    private final JwtUtils jwtUtils;

    public PaymentResult requestPayment(PaymentRequest dto, String bearerToken) {
        String token = jwtUtils.subStringToken(bearerToken);
        UUID userUuid = UUID.fromString(jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_UUID).toString());
        if (!dto.userUuid().equals(userUuid))
            throw new PaymentException(ErrorType.TOKEN_ERROR);
        OrderEntity orderEntity = orderRepository.findById(dto.orderUuid())
            .orElseThrow(() -> new PaymentException(ErrorType.NOT_FOUND_ERROR));
        // requestDto 의 storeUuid 의 존재 여부 확인 ( storeRepository.findById )
        return PaymentResult
            .of(paymentRepository.save(dto.toPayment().addAmount(orderEntity.getTotalPrice()).toEntity()));
    }

    public PaymentResult requestPaymentAdmin(PaymentRequest dto) {
        OrderEntity orderEntity = orderRepository.findById(dto.orderUuid())
            .orElseThrow(() -> new PaymentException(ErrorType.NOT_FOUND_ERROR));
        // 마찬가지로 requestDto 의 storeUuid 의 존재 여부 확인
        return PaymentResult
            .of(paymentRepository.save(dto.toPayment().addAmount(orderEntity.getTotalPrice()).toEntity()));
    }

    public Page<PaymentEntity> getPaymentListByUserUuid(UUID userUuid, LocalDateTime cursor, PageRequest sortedPage,
            String bearerToken) {
        String token = jwtUtils.subStringToken(bearerToken);
        UUID tokenUserUuid = UUID.fromString(jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_UUID).toString());
        if (!userUuid.equals(tokenUserUuid))
            throw new PaymentException(ErrorType.TOKEN_ERROR);
        return paymentRepository.findAllByUserUuid(userUuid,
                cursor == null ? LocalDateTime.of(2000, 1, 1, 0, 0) : cursor, sortedPage);
    }

    public Page<PaymentEntity> getPaymentListByUserAdmin(UUID userUuid, LocalDateTime cursor, PageRequest sortedPage) {
        return paymentRepository.findAllByUserUuid(userUuid,
                cursor == null ? LocalDateTime.of(2000, 1, 1, 0, 0) : cursor, sortedPage);
    }

    public Page<PaymentEntity> getPaymentListByOwner(UUID storeUuid, LocalDateTime cursor, PageRequest sortedPage,
            String bearerToken) {
        // StoreEntity storeEntity = storeRepository.findById(storeUuid).orElseThrow(() ->
        // new PaymentException(ErrorType.NOT_FOUND_ERROR));
        String token = jwtUtils.subStringToken(bearerToken);
        UUID userUuid = UUID.fromString(jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_UUID).toString());
        // if (!storeEntity.getUserUuid.equals(userUuid)) throw new
        // PaymentException(ErrorType.TOKEN_ERROR);

        return paymentRepository.findAllByStoreUuid(storeUuid,
                cursor == null ? LocalDateTime.of(2000, 1, 1, 0, 0) : cursor, sortedPage);
    }

    public Page<PaymentEntity> getPaymentListByOwnerAdmin(UUID storeUuid, LocalDateTime cursor,
            PageRequest sortedPage) {
        return paymentRepository.findAllByStoreUuid(storeUuid,
                cursor == null ? LocalDateTime.of(2000, 1, 1, 0, 0) : cursor, sortedPage);

    }

    public Page<PaymentEntity> getAllPayment(LocalDateTime cursor, PageRequest sortedPage) {
        return paymentRepository.findAllByAdmin(cursor == null ? LocalDateTime.of(2000, 1, 1, 0, 0) : cursor,
                sortedPage);
    }

    public PaymentResult cancelPayment(UUID paymentUuid) {
        PaymentEntity paymentEntity = paymentRepository.findByUuidAndIsCancelFalse(paymentUuid)
            .orElseThrow(() -> new PaymentException(ErrorType.NOT_FOUND_ERROR));
        paymentEntity.cancel();
        return PaymentResult.of(paymentEntity);
    }

}

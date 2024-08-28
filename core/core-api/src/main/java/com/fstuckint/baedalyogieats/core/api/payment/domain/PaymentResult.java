package com.fstuckint.baedalyogieats.core.api.payment.domain;

import com.fstuckint.baedalyogieats.core.api.payment.controller.v1.response.PaymentPageResponse;
import com.fstuckint.baedalyogieats.storage.db.core.payment.PaymentEntity;
import com.fstuckint.baedalyogieats.storage.db.core.payment.PaymentType;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public record PaymentResult(UUID paymentUuid, UUID orderUuid, UUID userUuid, UUID storeUuid, PaymentType paymentType,
                            Integer amount) {

    public static PaymentResult of(PaymentEntity paymentEntity) {
        return new PaymentResult(paymentEntity.getUuid(), paymentEntity.getOrderUuid(), paymentEntity.getUserUuid(),
                paymentEntity.getStoreUuid(), paymentEntity.getStatus(), paymentEntity.getAmount());
    }

    public static PaymentPageResponse of(Page<PaymentEntity> pageEntity) {
        List<PaymentResult> pageResult = pageEntity.stream().map(PaymentResult::of).toList();
        boolean hasNext = pageEntity.hasNext();
        return new PaymentPageResponse(pageResult, hasNext);
    }
}

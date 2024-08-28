package com.fstuckint.baedalyogieats.core.api.payment.controller.v1.response;

import com.fstuckint.baedalyogieats.core.api.payment.domain.PaymentResult;
import com.fstuckint.baedalyogieats.storage.db.core.payment.PaymentEntity;
import com.fstuckint.baedalyogieats.storage.db.core.payment.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

public record PaymentResponse(PaymentType type, UUID paymentUuid, UUID userUuid, UUID orderUuid, UUID storeUuid,
        Integer amount) {

    public static PaymentResponse of(PaymentResult paymentResult) {
        return new PaymentResponse(paymentResult.paymentType(), paymentResult.paymentUuid(), paymentResult.userUuid(),
                paymentResult.orderUuid(), paymentResult.storeUuid(), paymentResult.amount());
    }
}

package com.fstuckint.baedalyogieats.core.api.payment.controller.v1.request;

import com.fstuckint.baedalyogieats.core.api.payment.domain.Payment;
import com.fstuckint.baedalyogieats.storage.db.core.payment.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

public record PaymentRequest(PaymentType paymentType, UUID userUuid,
                             UUID orderUuid, UUID storeUuid) {

    public Payment toPayment() {
        return new Payment(paymentType, userUuid, orderUuid, storeUuid);
    }

}

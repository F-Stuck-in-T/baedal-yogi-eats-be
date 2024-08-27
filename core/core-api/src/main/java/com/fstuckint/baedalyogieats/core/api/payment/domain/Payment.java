package com.fstuckint.baedalyogieats.core.api.payment.domain;

import com.fstuckint.baedalyogieats.core.api.payment.controller.v1.request.PaymentRequest;
import com.fstuckint.baedalyogieats.storage.db.core.payment.PaymentEntity;
import com.fstuckint.baedalyogieats.storage.db.core.payment.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private PaymentType status;
    private UUID userUuid;
    private UUID orderUuid;
    private UUID storeUuid;
    private Integer amount;
    public PaymentEntity toEntity() {
        return new PaymentEntity(status, orderUuid, userUuid, storeUuid, amount);
    }

    public Payment(PaymentType status, UUID userUuid, UUID orderUuid, UUID storeUuid) {
        this.status = status;
        this.userUuid = userUuid;
        this.orderUuid = orderUuid;
        this.storeUuid = storeUuid;
    }

    public void addAmount(Integer amount) {
        this.amount = amount;
    }
}

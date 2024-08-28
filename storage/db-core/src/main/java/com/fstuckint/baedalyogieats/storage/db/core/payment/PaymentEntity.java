package com.fstuckint.baedalyogieats.storage.db.core.payment;

import com.fstuckint.baedalyogieats.storage.db.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaymentEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private PaymentType status;

    private UUID userUuid;

    private UUID orderUuid;

    private UUID storeUuid;

    private Integer amount;

    private boolean isCancel = false;

    public PaymentEntity(PaymentType status, UUID orderUuid, UUID userUuid, UUID storeUuid, Integer amount) {
        this.status = status;
        this.orderUuid = orderUuid;
        this.userUuid = userUuid;
        this.storeUuid = storeUuid;
        this.amount = amount;
    }

    public PaymentEntity cancel() {
        isCancel = true;
        return this;
    }

}

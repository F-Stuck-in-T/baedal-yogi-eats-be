package com.fstuckint.baedalyogieats.core.api.payment.domain;

import com.fstuckint.baedalyogieats.storage.db.core.payment.PaymentEntity;
import com.fstuckint.baedalyogieats.storage.db.core.payment.PaymentType;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentInfo {

    private UUID paymentUuid;

    private PaymentType paymentType;

    public static PaymentInfo of(PaymentEntity payment) {
        return PaymentInfo.builder().paymentUuid(payment.getUuid()).paymentType(payment.getStatus()).build();
    }

}

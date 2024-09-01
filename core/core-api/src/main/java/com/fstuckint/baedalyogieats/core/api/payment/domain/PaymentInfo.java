package com.fstuckint.baedalyogieats.core.api.payment.domain;

import com.fstuckint.baedalyogieats.storage.db.core.payment.*;
import lombok.*;

import java.util.*;

@Builder
public class PaymentInfo {

    private UUID paymentUuid;

    private PaymentType paymentType;

    public static PaymentInfo of(PaymentEntity payment) {
        return PaymentInfo.builder()
                .paymentUuid(payment.getUuid())
                .paymentType(payment.getStatus())
                .build();
    }
}

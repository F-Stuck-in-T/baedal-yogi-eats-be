package com.fstuckint.baedalyogieats.core.api.payment.controller.v1.response;


import com.fstuckint.baedalyogieats.core.api.payment.domain.PaymentResult;
import com.fstuckint.baedalyogieats.storage.db.core.payment.PaymentEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public record PaymentPageResponse(List<PaymentResult> paymentResults, boolean hasNext) {

}

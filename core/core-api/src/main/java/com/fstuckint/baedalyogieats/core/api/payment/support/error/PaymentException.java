package com.fstuckint.baedalyogieats.core.api.payment.support.error;

import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException {

    private final ErrorType errorType;

    private final Object data;

    public PaymentException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = null;
    }

    public PaymentException(ErrorType errorType, Object data) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = data;
    }

}

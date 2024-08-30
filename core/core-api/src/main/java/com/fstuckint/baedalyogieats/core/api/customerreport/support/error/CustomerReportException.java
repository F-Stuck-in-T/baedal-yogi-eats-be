package com.fstuckint.baedalyogieats.core.api.customerreport.support.error;

import lombok.Getter;

@Getter
public class CustomerReportException extends RuntimeException {

    private final ErrorType errorType;

    private final Object data;

    public CustomerReportException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = null;
    }

    public CustomerReportException(ErrorType errorType, Object data) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = data;
    }

}

package com.fstuckint.baedalyogieats.core.api.address.support.error;

import lombok.Getter;

@Getter
public class AddressException extends RuntimeException {

    private final ErrorType errorType;

    private final Object data;

    public AddressException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = null;
    }

    public AddressException(ErrorType errorType, Object data) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = data;
    }

}

package com.fstuckint.baedalyogieats.core.api.user.support.error;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class UserException extends RuntimeException {

    private final ErrorType errorType;

    private final Object data;

    public UserException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = null;
    }

    public UserException(ErrorType errorType, Object data) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = data;
    }

}

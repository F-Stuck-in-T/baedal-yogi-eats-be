package com.fstuckint.baedalyogieats.core.api.ai.support.error;

import com.fstuckint.baedalyogieats.core.api.address.support.error.ErrorType;

public class AiChatsApiException extends RuntimeException {

    private final ErrorType errorType;

    private final Object data;

    public AiChatsApiException(com.fstuckint.baedalyogieats.core.api.address.support.error.ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = null;
    }

    public AiChatsApiException(ErrorType errorType, Object data) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = data;
    }

}

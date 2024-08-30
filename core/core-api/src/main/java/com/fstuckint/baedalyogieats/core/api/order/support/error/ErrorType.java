package com.fstuckint.baedalyogieats.core.api.order.support.error;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public enum ErrorType {

    DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500, "An unexpected error has occurred.",
            LogLevel.ERROR),
    NOT_FOUND_ENTITY(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Entity not found.", LogLevel.INFO),
    INVALID_STATUS_CHANGE(HttpStatus.BAD_REQUEST, ErrorCode.E401, "Status can not change.", LogLevel.INFO),
    CANCEL_TIME_OUT(HttpStatus.BAD_REQUEST, ErrorCode.E402, "Order can not cancel.", LogLevel.INFO);

    private final HttpStatus status;

    private final ErrorCode code;

    private final String message;

    private final LogLevel logLevel;

    ErrorType(HttpStatus status, ErrorCode code, String message, LogLevel logLevel) {

        this.status = status;
        this.code = code;
        this.message = message;
        this.logLevel = logLevel;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

}

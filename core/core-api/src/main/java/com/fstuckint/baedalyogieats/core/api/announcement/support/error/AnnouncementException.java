package com.fstuckint.baedalyogieats.core.api.announcement.support.error;

import lombok.Getter;

@Getter
public class AnnouncementException extends RuntimeException {

    private final ErrorType errorType;

    private final Object data;

    public AnnouncementException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = null;
    }

    public AnnouncementException(ErrorType errorType, Object data) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = data;
    }

}

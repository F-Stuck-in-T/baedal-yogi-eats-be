package com.fstuckint.baedalyogieats.core.api.announcement.controller.v1;

import com.fstuckint.baedalyogieats.core.api.announcement.support.error.AnnouncementException;
import com.fstuckint.baedalyogieats.core.api.announcement.support.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.fstuckint.baedalyogieats.core.api.announcement")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AnnouncementExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(AnnouncementException.class)
    public ResponseEntity<ApiResponse<?>> handleAnnouncementException(AnnouncementException e) {
        log.error("AnnouncementException : {}", e.getMessage(), e);
        return ResponseEntity.status(e.getErrorType().getStatus())
            .body(ApiResponse.error(e.getErrorType(), e.getData()));
    }

}

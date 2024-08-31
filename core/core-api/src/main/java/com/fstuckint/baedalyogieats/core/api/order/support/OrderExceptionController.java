package com.fstuckint.baedalyogieats.core.api.order.support;

import com.fstuckint.baedalyogieats.core.api.order.support.error.*;
import com.fstuckint.baedalyogieats.core.api.order.support.response.*;
import org.slf4j.*;
import org.springframework.core.*;
import org.springframework.core.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice(basePackages = "com.fstuckint.baedalyogieats.core.api.order")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OrderExceptionController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CoreApiException.class)
    public ResponseEntity<ApiResponse<?>> handleCoreApiException(CoreApiException e) {
        switch (e.getErrorType().getLogLevel()) {
            case ERROR -> log.error("CoreApiException : {}", e.getMessage(), e);
            case WARN -> log.warn("CoreApiException : {}", e.getMessage(), e);
            default -> log.info("CoreApiException : {}", e.getMessage(), e);
        }
        return new ResponseEntity<>(ApiResponse.error(e.getErrorType(), e.getData()), e.getErrorType().getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return new ResponseEntity<>(ApiResponse.error(ErrorType.DEFAULT_ERROR), ErrorType.DEFAULT_ERROR.getStatus());
    }

}

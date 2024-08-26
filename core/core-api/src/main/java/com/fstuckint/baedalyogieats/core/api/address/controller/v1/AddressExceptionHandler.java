package com.fstuckint.baedalyogieats.core.api.address.controller.v1;

import com.fstuckint.baedalyogieats.core.api.address.support.error.AddressException;
import com.fstuckint.baedalyogieats.core.api.address.support.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.fstuckint.baedalyogieats.core.api.address")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AddressExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(AddressException.class)
    public ResponseEntity<ApiResponse<?>> handleUserException(AddressException e) {
        log.error("AddressException : {}", e.getMessage(), e);
        return new ResponseEntity<>(ApiResponse.error(e.getErrorType(), e.getData()), e.getErrorType().getStatus());
    }

}

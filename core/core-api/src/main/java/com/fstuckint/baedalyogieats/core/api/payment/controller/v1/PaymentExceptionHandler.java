package com.fstuckint.baedalyogieats.core.api.payment.controller.v1;

import com.fstuckint.baedalyogieats.core.api.payment.support.response.ApiResponse;
import com.fstuckint.baedalyogieats.core.api.payment.support.error.PaymentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.fstuckint.baedalyogieats.core.api.payment")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PaymentExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ApiResponse<?>> handleUserException(PaymentException e) {
        log.error("PaymentException : {}", e.getMessage(), e);
        return new ResponseEntity<>(ApiResponse.error(e.getErrorType(), e.getData()), e.getErrorType().getStatus());
    }

}

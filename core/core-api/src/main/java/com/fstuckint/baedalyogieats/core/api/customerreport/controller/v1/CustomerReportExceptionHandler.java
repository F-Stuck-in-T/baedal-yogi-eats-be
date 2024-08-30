package com.fstuckint.baedalyogieats.core.api.customerreport.controller.v1;

import com.fstuckint.baedalyogieats.core.api.customerreport.support.error.CustomerReportException;
import com.fstuckint.baedalyogieats.core.api.customerreport.support.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.fstuckint.baedalyogieats.core.api.customerreport")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomerReportExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CustomerReportException.class)
    public ResponseEntity<ApiResponse<?>> handleUserException(CustomerReportException e) {
        log.error("AddressException : {}", e.getMessage(), e);
        return new ResponseEntity<>(ApiResponse.error(e.getErrorType(), e.getData()), e.getErrorType().getStatus());
    }

}

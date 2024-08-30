package com.fstuckint.baedalyogieats.core.api.customerreport.controller.v1.request;

import com.fstuckint.baedalyogieats.core.api.customerreport.domain.CustomerReport;

import java.util.UUID;

public record CustomerReportRequest(String question) {

    public CustomerReport toCustomerReport(UUID userUuid) {
        return new CustomerReport(userUuid, question);
    }
}

package com.fstuckint.baedalyogieats.core.api.customerreport.controller.v1.response;

import com.fstuckint.baedalyogieats.core.api.customerreport.domain.CustomerReportResult;

import java.util.UUID;

public record CustomerReportResponse(UUID customerReportUuid, UUID userUuid, String question, String answer) {

    public static CustomerReportResponse of(CustomerReportResult customerReportResult) {
        return new CustomerReportResponse(customerReportResult.customerReportUuid(), customerReportResult.userUuid(),
                customerReportResult.question(), customerReportResult.answer());
    }
}

package com.fstuckint.baedalyogieats.core.api.customerreport.domain;

import com.fstuckint.baedalyogieats.storage.db.core.customerreport.CustomerReportEntity;

import java.util.UUID;

public record CustomerReportResult(UUID customerReportUuid, UUID userUuid, String question, String answer) {

    public static CustomerReportResult of(CustomerReportEntity customerReportEntity) {
        return new CustomerReportResult(customerReportEntity.getUuid(), customerReportEntity.getUserUuid(),
                customerReportEntity.getQuestion(), customerReportEntity.getAnswer());
    }
}

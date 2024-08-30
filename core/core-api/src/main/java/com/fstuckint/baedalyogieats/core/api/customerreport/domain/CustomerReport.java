package com.fstuckint.baedalyogieats.core.api.customerreport.domain;

import com.fstuckint.baedalyogieats.storage.db.core.customerreport.CustomerReportEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReport {

    private UUID userUuid;

    private String question;

    public CustomerReportEntity toEntity() {
        return new CustomerReportEntity(userUuid, question);
    }

}

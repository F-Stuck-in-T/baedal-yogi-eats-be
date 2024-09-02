package com.fstuckint.baedalyogieats.storage.db.core.customerreport;

import com.fstuckint.baedalyogieats.storage.db.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_customer_report")
public class CustomerReportEntity extends BaseEntity {

    private UUID userUuid;

    private String question;

    private String answer;

    private boolean isDeleted = false;

    public CustomerReportEntity(UUID userUuid, String question) {
        this.userUuid = userUuid;
        this.question = question;
    }

    public CustomerReportEntity provideAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    public CustomerReportEntity delete() {
        isDeleted = true;
        return this;
    }

    public CustomerReportEntity editQuestion(String question) {
        this.question = question;
        return this;
    }

}

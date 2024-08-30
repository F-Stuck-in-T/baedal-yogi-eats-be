package com.fstuckint.baedalyogieats.core.api.customerreport.domain;

import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.customerreport.controller.v1.request.AdminAnswerRequest;
import com.fstuckint.baedalyogieats.core.api.customerreport.controller.v1.request.CustomerReportRequest;
import com.fstuckint.baedalyogieats.core.api.customerreport.controller.v1.response.CustomerReportResponse;
import com.fstuckint.baedalyogieats.core.api.customerreport.support.error.CustomerReportException;
import com.fstuckint.baedalyogieats.core.api.customerreport.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerReportService {

    private final CustomerReportPilot customerReportPilot;

    private final JwtUtils jwtUtils;

    @Transactional
    public CustomerReportResponse requestQuestion(CustomerReportRequest customerReportRequest, String bearerToken) {
        return CustomerReportResponse.of(customerReportPilot.requestQuestion(customerReportRequest, bearerToken));
    }

    @Transactional(readOnly = true)
    public CustomerReportResponse getQuestion(UUID reportUuid, String bearerToken) {
        if (jwtUtils.checkAdmin(bearerToken))
            return CustomerReportResponse.of(customerReportPilot.getQuestionAdmin(reportUuid));
        if (jwtUtils.checkCustomer(bearerToken) || jwtUtils.checkOwner(bearerToken))
            return CustomerReportResponse.of(customerReportPilot.getQuestion(reportUuid, bearerToken));
        throw new CustomerReportException(ErrorType.DEFAULT_ERROR);
    }

    @Transactional(readOnly = true)
    public List<CustomerReportResponse> getQuestionList() {
        return customerReportPilot.getQuestionList().stream().map(CustomerReportResponse::of).toList();
    }

    @Transactional
    public CustomerReportResponse provideAnswer(UUID reportUuid, AdminAnswerRequest answerRequest) {
        return CustomerReportResponse.of(customerReportPilot.provideAnswer(reportUuid, answerRequest));
    }

    @Transactional
    public CustomerReportResponse editQuestion(UUID reportUuid, CustomerReportRequest customerReportRequest,
            String bearerToken) {
        return CustomerReportResponse
            .of(customerReportPilot.editQuestion(reportUuid, customerReportRequest, bearerToken));
    }

    @Transactional
    public CustomerReportResponse deleteQuestion(UUID reportUuid, String bearerToken) {
        if (jwtUtils.checkAdmin(bearerToken))
            return CustomerReportResponse.of(customerReportPilot.deleteQuestionAdmin(reportUuid));
        if (jwtUtils.checkCustomer(bearerToken) || jwtUtils.checkOwner(bearerToken))
            return CustomerReportResponse.of(customerReportPilot.deleteQuestion(reportUuid, bearerToken));
        throw new CustomerReportException(ErrorType.DEFAULT_ERROR);

    }

}

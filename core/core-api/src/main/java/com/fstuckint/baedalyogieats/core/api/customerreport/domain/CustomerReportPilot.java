package com.fstuckint.baedalyogieats.core.api.customerreport.domain;

import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.customerreport.controller.v1.request.AdminAnswerRequest;
import com.fstuckint.baedalyogieats.core.api.customerreport.controller.v1.request.CustomerReportRequest;
import com.fstuckint.baedalyogieats.core.api.customerreport.support.error.CustomerReportException;
import com.fstuckint.baedalyogieats.core.api.customerreport.support.error.ErrorType;
import com.fstuckint.baedalyogieats.storage.db.core.customerreport.CustomerReportEntity;
import com.fstuckint.baedalyogieats.storage.db.core.customerreport.CustomerReportRepository;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerReportPilot {

    private final CustomerReportRepository customerReportRepository;

    private final UserRepository userRepository;

    private final JwtUtils jwtUtils;

    public CustomerReportResult requestQuestion(CustomerReportRequest customerReportRequest, String bearerToken) {
        String token = jwtUtils.subStringToken(bearerToken);
        UUID userUuid = UUID.fromString(jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_UUID).toString());
        userRepository.findByUuidAndIsDeletedFalse(userUuid)
            .orElseThrow(() -> new CustomerReportException(ErrorType.NOT_FOUND_ERROR));
        return CustomerReportResult
            .of(customerReportRepository.save(customerReportRequest.toCustomerReport(userUuid).toEntity()));
    }

    public CustomerReportResult getQuestion(UUID reportUuid, String bearerToken) {
        CustomerReportEntity customerReportEntity = customerReportRepository.findByUuidAndIsDeletedFalse(reportUuid)
            .orElseThrow(() -> new CustomerReportException(ErrorType.NOT_FOUND_ERROR));
        String token = jwtUtils.subStringToken(bearerToken);
        UUID userUuid = UUID.fromString(jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_UUID).toString());
        if (!customerReportEntity.getUserUuid().equals(userUuid))
            throw new CustomerReportException(ErrorType.BAD_REQUEST_ERROR);
        return CustomerReportResult.of(customerReportEntity);
    }

    public CustomerReportResult getQuestionAdmin(UUID reportUuid) {
        return CustomerReportResult.of(customerReportRepository.findByUuidAndIsDeletedFalse(reportUuid)
            .orElseThrow(() -> new CustomerReportException(ErrorType.NOT_FOUND_ERROR)));
    }

    public List<CustomerReportResult> getQuestionList() {
        return customerReportRepository.findAllByIsDeletedFalse().stream().map(CustomerReportResult::of).toList();
    }

    public CustomerReportResult provideAnswer(UUID reportUuid, AdminAnswerRequest answerRequest) {
        CustomerReportEntity customerReportEntity = customerReportRepository.findByUuidAndIsDeletedFalse(reportUuid)
            .orElseThrow(() -> new CustomerReportException(ErrorType.NOT_FOUND_ERROR));
        return CustomerReportResult
            .of(customerReportRepository.save(customerReportEntity.provideAnswer(answerRequest.answer())));
    }

    public CustomerReportResult editQuestion(UUID reportUuid, CustomerReportRequest customerReportRequest,
            String bearerToken) {
        CustomerReportEntity customerReportEntity = customerReportRepository.findByUuidAndIsDeletedFalse(reportUuid)
            .orElseThrow(() -> new CustomerReportException(ErrorType.NOT_FOUND_ERROR));
        if (customerReportEntity.getAnswer() != null)
            throw new CustomerReportException(ErrorType.ALREADY_WRITTEN_ERROR);
        String token = jwtUtils.subStringToken(bearerToken);
        UUID userUuid = UUID.fromString(jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_UUID).toString());
        if (!customerReportEntity.getUserUuid().equals(userUuid))
            throw new CustomerReportException(ErrorType.BAD_REQUEST_ERROR);
        return CustomerReportResult
            .of(customerReportRepository.save(customerReportEntity.editQuestion(customerReportRequest.question())));
    }

    public CustomerReportResult deleteQuestion(UUID reportUuid, String bearerToken) {
        CustomerReportEntity customerReportEntity = customerReportRepository.findByUuidAndIsDeletedFalse(reportUuid)
            .orElseThrow(() -> new CustomerReportException(ErrorType.NOT_FOUND_ERROR));
        String token = jwtUtils.subStringToken(bearerToken);
        UUID userUuid = UUID.fromString(jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_UUID).toString());
        if (!customerReportEntity.getUserUuid().equals(userUuid))
            throw new CustomerReportException(ErrorType.BAD_REQUEST_ERROR);
        return CustomerReportResult.of(customerReportRepository.save(customerReportEntity.delete()));
    }

    public CustomerReportResult deleteQuestionAdmin(UUID reportUuid) {
        return CustomerReportResult.of(customerReportRepository.findByUuidAndIsDeletedFalse(reportUuid)
            .orElseThrow(() -> new CustomerReportException(ErrorType.NOT_FOUND_ERROR))
            .delete());
    }

}

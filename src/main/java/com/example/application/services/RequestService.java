package com.example.application.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.data.Request;
import com.example.application.data.requestStatusEnum.RequestStatusEnum;
import com.example.application.repository.RequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import jakarta.transaction.Transactional;

@Service
public class RequestService {

    @Autowired
    RequestRepository requestRepository;

    @Transactional
    public Request createRequest(String insuranceContractNumber, String debitorsCountry, String registrationCode,
            BigDecimal clAmount,
            String clCurrency, String clTermsAndConditions, String adjustmentPossibility, RequestStatusEnum status) {

        Request request = new Request();
        request.setInsuranceContractNumber(insuranceContractNumber);
        request.setDebitorsCountry(debitorsCountry);
        request.setRegistrationCode(registrationCode);
        request.setClAmount(clAmount);
        request.setClCurrency(clCurrency);
        request.setClTermsAndConditions(clTermsAndConditions);
        request.setAdjustmentPossibility(adjustmentPossibility);
        request.setRequestStatus(status);
        // request.setDebtor(debtor);
        return requestRepository.save(request);
    }

    public Page<Request> list(Pageable pageable) {
        return requestRepository.findAll(pageable);
    }

    public Page<Request> list(Pageable pageable, Specification<Request> filter) {
        return requestRepository.findAll(filter, pageable);
    }

    // public Page<Request> listAcceptedRequests(Pageable pageable) {
    // return requestRepository.getAcceptedRequestsByRegistrationCode(pageable);
    // }

    // public Page<Request> listAcceptedRequests(Specification<Request> filter,
    // Pageable pageable) {
    // return requestRepository.getAcceptedRequestsByRegistrationCode(pageable);
    // }

    public Request findRequestById(Integer id) {
        return requestRepository.findById(id).orElse(null);
    }

    public Request update(Request entity) {
        return requestRepository.save(entity);
    }

    public Optional<Request> get(Integer id) {
        return requestRepository.findById(id);
    }

    @Transactional
    public void deleteRequestById(Integer id) {
        requestRepository.deleteById(id);
    }

    public Page<Request> getAcceptedRequestsByRegistrationCode(String registrationCode, Pageable pageable) {
        return requestRepository.getAcceptedRequestsByRegistrationCode(registrationCode, pageable);
    }

    public Page<Request> getPendingRequestsByRegistrationCode(String registrationCode, Pageable pageable) {
        return requestRepository.getPendingRequestsByRegistrationCode(registrationCode, pageable);
    }
}

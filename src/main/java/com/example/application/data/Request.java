package com.example.application.data;

import java.math.BigDecimal;

import com.example.application.data.requestStatusEnum.RequestStatusEnum;

import jakarta.persistence.*;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String insuranceContractNumber;
    private String debitorsCountry;
    private String registrationCode;
    private BigDecimal clAmount;
    private String clCurrency;
    @Column(name = "cl_terms_conditions")
    private String clTermsAndConditions;
    private String adjustmentPossibility;
    private Integer debtor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatusEnum status;

    public Request() {
    }

    public Request(String insuranceContractNumber, String debitorsCountry, String registrationCode, BigDecimal clAmount,
            String clCurrency, String clTermsAndConditions, String adjustmentPossibility, RequestStatusEnum status,
            Integer debtor) {
        this.insuranceContractNumber = insuranceContractNumber;
        this.debitorsCountry = debitorsCountry;
        this.registrationCode = registrationCode;
        this.clAmount = clAmount;
        this.clCurrency = clCurrency;
        this.clTermsAndConditions = clTermsAndConditions;
        this.adjustmentPossibility = adjustmentPossibility;
        this.status = status;
        this.debtor = debtor;
    }

    public Integer getId() {
        return id;
    }

    public String getInsuranceContractNumber() {
        return insuranceContractNumber;
    }

    public void setInsuranceContractNumber(String insuranceContractNumber) {
        this.insuranceContractNumber = insuranceContractNumber;
    }

    public String getDebitorsCountry() {
        return debitorsCountry;
    }

    public void setDebitorsCountry(String debitorsCountry) {
        this.debitorsCountry = debitorsCountry;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public BigDecimal getClAmount() {
        return clAmount;
    }

    public void setClAmount(BigDecimal clAmount) {
        this.clAmount = clAmount;
    }

    public String getClCurrency() {
        return clCurrency;
    }

    public void setClCurrency(String clCurrency) {
        this.clCurrency = clCurrency;
    }

    public String getClTermsAndConditions() {
        return clTermsAndConditions;
    }

    public void setClTermsAndConditions(String clTermsAndConditions) {
        this.clTermsAndConditions = clTermsAndConditions;
    }

    public String getAdjustmentPossibility() {
        return adjustmentPossibility;
    }

    public void setAdjustmentPossibility(String adjustmentPossibility) {
        this.adjustmentPossibility = adjustmentPossibility;
    }

    public RequestStatusEnum getStatus() {
        return status;
    }

    public void setStatus(RequestStatusEnum status) {
        this.status = status;
    }

    public Integer getDebtor() {
        return debtor;
    }

    public void setDebtor(Integer debtor) {
        this.debtor = debtor;
    }
}

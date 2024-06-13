package com.example.application.data;

import java.math.BigDecimal;

import com.example.application.data.requestStatusEnum.RequestStatusEnum;

import jakarta.persistence.*;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "insurance_contract_number", referencedColumnName = "id")
    private Contract insuranceContractNumber;
    private String debitorsCountry;
    private String registrationCode;
    private BigDecimal clAmount;
    private String clCurrency;
    @Column(name = "cl_terms_conditions")
    private String clTermsAndConditions;
    private String adjustmentPossibility;
    @ManyToOne
    @JoinColumn(name = "debtor", referencedColumnName = "id")
    private Debtors debtor;
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatusEnum status;

    public Request() {
    }

    public Request(Contract insuranceContractNumber, String debitorsCountry, String registrationCode,
            BigDecimal clAmount,
            String clCurrency, String clTermsAndConditions, String adjustmentPossibility, RequestStatusEnum status,
            Debtors debtor, String comment) {
        this.insuranceContractNumber = insuranceContractNumber;
        this.debitorsCountry = debitorsCountry;
        this.registrationCode = registrationCode;
        this.clAmount = clAmount;
        this.clCurrency = clCurrency;
        this.clTermsAndConditions = clTermsAndConditions;
        this.adjustmentPossibility = adjustmentPossibility;
        this.status = status;
        this.debtor = debtor;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public Contract getInsuranceContractNumber() {
        return insuranceContractNumber;
    }

    public void setInsuranceContractNumber(Contract insuranceContractNumber) {
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

    public Debtors getDebtor() {
        return debtor;
    }

    public void setDebtor(Debtors debtor) {
        this.debtor = debtor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}

package com.example.application.data;

import java.math.BigDecimal;

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
    private String clTermAndConditions;
    private String adjustmentPossibility;

    public Request() {

    }

    public Request(String insuranceContractNumber, String debitorsCountry, String registrationCode, BigDecimal clAmount,
            String clCurrency, String clTermAndConditions, String adjustmentPossibility) {
        this.insuranceContractNumber = insuranceContractNumber;
        this.debitorsCountry = debitorsCountry;
        this.registrationCode = registrationCode;
        this.clAmount = clAmount;
        this.clCurrency = clCurrency;
        this.clTermAndConditions = clTermAndConditions;
        this.adjustmentPossibility = adjustmentPossibility;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getClTermAndConditions() {
        return clTermAndConditions;
    }

    public void setClTermAndConditions(String clTermAndConditions) {
        this.clTermAndConditions = clTermAndConditions;
    }

    public String getAdjustmentPossibility() {
        return adjustmentPossibility;
    }

    public void setAdjustmentPossibility(String adjustmentPossibility) {
        this.adjustmentPossibility = adjustmentPossibility;
    }
}

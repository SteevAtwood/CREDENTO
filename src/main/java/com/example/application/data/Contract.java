package com.example.application.data;

import java.sql.Timestamp;

import com.example.application.data.coveredRisksEnum.CoveredRisksEnum;
import com.example.application.data.statusEnum.StatusEnum;

import jakarta.persistence.*;

@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String insuranceContractNumber;
    private String insurer;
    private Timestamp startDateOfInsuranceCoverage;
    private Timestamp endDateOfInsuranceCoverage;
    private String supervisingUnderwriter;
    private String supervisingUOPBEmployee;
    private String policyholder;
    private String coveredCountries;
    private String insuredSharePolitical;
    private Integer waitingPeriodPolitical;
    private Integer maxCommercialCreditPeriodPolitical;
    private String insuredShareCommercial;
    private Integer waitingPeriodCommercial;
    private Integer maxCommercialCreditPeriodCommercial;
    private String clientName;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    private CoveredRisksEnum coveredRisks;

    public Contract() {
    }

    public Contract(String insuranceContractNumber, String insurer, StatusEnum status,
            Timestamp startDateOfInsuranceCoverage,
            Timestamp endDateOfInsuranceCoverage, String supervisingUnderwriter, String supervisingUOPBEmployee,
            String policyholder, String coveredCountries, CoveredRisksEnum coveredRisks, String insuredSharePolitical,
            Integer waitingPeriodPolitical, Integer maxCommercialCreditPeriodPolitical, String insuredShareCommercial,
            Integer waitingPeriodCommercial, Integer maxCommercialCreditPeriodCommercial, String clientName) {
        this.insuranceContractNumber = insuranceContractNumber;
        this.insurer = insurer;
        this.status = status;
        this.startDateOfInsuranceCoverage = startDateOfInsuranceCoverage;
        this.endDateOfInsuranceCoverage = endDateOfInsuranceCoverage;
        this.supervisingUnderwriter = supervisingUnderwriter;
        this.supervisingUOPBEmployee = supervisingUOPBEmployee;
        this.policyholder = policyholder;
        this.coveredCountries = coveredCountries;
        this.coveredRisks = coveredRisks;
        this.insuredSharePolitical = insuredSharePolitical;
        this.waitingPeriodPolitical = waitingPeriodPolitical;
        this.maxCommercialCreditPeriodPolitical = maxCommercialCreditPeriodPolitical;
        this.insuredShareCommercial = insuredShareCommercial;
        this.waitingPeriodCommercial = waitingPeriodCommercial;
        this.maxCommercialCreditPeriodCommercial = maxCommercialCreditPeriodCommercial;
        this.clientName = clientName;
    }

    // Getters and setters
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

    public String getInsurer() {
        return insurer;
    }

    public void setInsurer(String insurer) {
        this.insurer = insurer;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Timestamp getStartDateOfInsuranceCoverage() {
        return startDateOfInsuranceCoverage;
    }

    public void setStartDateOfInsuranceCoverage(Timestamp startDateOfInsuranceCoverage) {
        this.startDateOfInsuranceCoverage = startDateOfInsuranceCoverage;
    }

    public Timestamp getEndDateOfInsuranceCoverage() {
        return endDateOfInsuranceCoverage;
    }

    public void setEndDateOfInsuranceCoverage(Timestamp endDateOfInsuranceCoverage) {
        this.endDateOfInsuranceCoverage = endDateOfInsuranceCoverage;
    }

    public String getSupervisingUnderwriter() {
        return supervisingUnderwriter;
    }

    public void setSupervisingUnderwriter(String supervisingUnderwriter) {
        this.supervisingUnderwriter = supervisingUnderwriter;
    }

    public String getSupervisingUOPBEmployee() {
        return supervisingUOPBEmployee;
    }

    public void setSupervisingUOPBEmployee(String supervisingUOPBEmployee) {
        this.supervisingUOPBEmployee = supervisingUOPBEmployee;
    }

    public String getPolicyholder() {
        return policyholder;
    }

    public void setPolicyholder(String policyholder) {
        this.policyholder = policyholder;
    }

    public String getCoveredCountries() {
        return coveredCountries;
    }

    public void setCoveredCountries(String coveredCountries) {
        this.coveredCountries = coveredCountries;
    }

    public CoveredRisksEnum getCoveredRisks() {
        return coveredRisks;
    }

    public void setCoveredRisks(CoveredRisksEnum coveredRisks) {
        this.coveredRisks = coveredRisks;
    }

    public String getInsuredSharePolitical() {
        return insuredSharePolitical;
    }

    public void setInsuredSharePolitical(String insuredSharePolitical) {
        this.insuredSharePolitical = insuredSharePolitical;
    }

    public Integer getWaitingPeriodPolitical() {
        return waitingPeriodPolitical;
    }

    public void setWaitingPeriodPolitical(Integer waitingPeriodPolitical) {
        this.waitingPeriodPolitical = waitingPeriodPolitical;
    }

    public Integer getMaxCommercialCreditPeriodPolitical() {
        return maxCommercialCreditPeriodPolitical;
    }

    public void setMaxCommercialCreditPeriodPolitical(Integer maxCommercialCreditPeriodPolitical) {
        this.maxCommercialCreditPeriodPolitical = maxCommercialCreditPeriodPolitical;
    }

    public String getInsuredShareCommercial() {
        return insuredShareCommercial;
    }

    public void setInsuredShareCommercial(String insuredShareCommercial) {
        this.insuredShareCommercial = insuredShareCommercial;
    }

    public Integer getWaitingPeriodCommercial() {
        return waitingPeriodCommercial;
    }

    public void setWaitingPeriodCommercial(Integer waitingPeriodCommercial) {
        this.waitingPeriodCommercial = waitingPeriodCommercial;
    }

    public Integer getMaxCommercialCreditPeriodCommercial() {
        return maxCommercialCreditPeriodCommercial;
    }

    public void setMaxCommercialCreditPeriodCommercial(Integer maxCommercialCreditPeriodCommercial) {
        this.maxCommercialCreditPeriodCommercial = maxCommercialCreditPeriodCommercial;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

}

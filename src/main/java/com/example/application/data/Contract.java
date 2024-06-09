package com.example.application.data;

import java.time.LocalDate;

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
    private LocalDate startDateOfInsuranceCoverage;
    private LocalDate endDateOfInsuranceCoverage;
    @ManyToOne
    @JoinColumn(name = "supervising_underwriter", referencedColumnName = "id")
    private User supervisingUnderwriter;

    @ManyToOne
    @JoinColumn(name = "supervising_UOPB_employee", referencedColumnName = "id")
    private User supervising_UOPB_employee;
    private String policyholder;
    private String coveredCountries;
    private String insuredSharePolitical;
    private Integer waitingPeriodPolitical;
    private Integer maxPoliticalCreditPeriod;
    private String insuredShareCommercial;
    private Integer waitingPeriodCommercial;
    private Integer maxCommercialCreditPeriod;
    private String clientName;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    private CoveredRisksEnum coveredRisks;

    public Contract() {

    }

    public Contract(String insuranceContractNumber, String insurer, StatusEnum status,
            LocalDate startDateOfInsuranceCoverage,
            LocalDate endDateOfInsuranceCoverage, User supervisingUnderwriter, User supervising_UOPB_employee,
            String policyholder, String coveredCountries, CoveredRisksEnum coveredRisks, String insuredSharePolitical,
            Integer waitingPeriodPolitical, Integer maxPoliticalCreditPeriod, String insuredShareCommercial,
            Integer waitingPeriodCommercial, Integer maxCommercialCreditPeriod, String clientName) {
        this.insuranceContractNumber = insuranceContractNumber;
        this.insurer = insurer;
        this.status = status;
        this.startDateOfInsuranceCoverage = startDateOfInsuranceCoverage;
        this.endDateOfInsuranceCoverage = endDateOfInsuranceCoverage;
        this.supervisingUnderwriter = supervisingUnderwriter;
        this.supervising_UOPB_employee = supervising_UOPB_employee;
        this.policyholder = policyholder;
        this.coveredCountries = coveredCountries;
        this.coveredRisks = coveredRisks;
        this.insuredSharePolitical = insuredSharePolitical;
        this.waitingPeriodPolitical = waitingPeriodPolitical;
        this.maxPoliticalCreditPeriod = maxPoliticalCreditPeriod;
        this.insuredShareCommercial = insuredShareCommercial;
        this.waitingPeriodCommercial = waitingPeriodCommercial;
        this.maxCommercialCreditPeriod = maxCommercialCreditPeriod;
        this.clientName = clientName;
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

    public LocalDate getStartDateOfInsuranceCoverage() {
        return startDateOfInsuranceCoverage;
    }

    public void setStartDateOfInsuranceCoverage(LocalDate startDateOfInsuranceCoverage) {
        this.startDateOfInsuranceCoverage = startDateOfInsuranceCoverage;
    }

    public LocalDate getEndDateOfInsuranceCoverage() {
        return endDateOfInsuranceCoverage;
    }

    public void setEndDateOfInsuranceCoverage(LocalDate endDateOfInsuranceCoverage) {
        this.endDateOfInsuranceCoverage = endDateOfInsuranceCoverage;
    }

    public User getSupervisingUnderwriter() {
        return supervisingUnderwriter;
    }

    public void setSupervisingUnderwriter(User supervisingUnderwriter) {
        this.supervisingUnderwriter = supervisingUnderwriter;
    }

    public User getSupervising_UOPB_employee() {
        return supervising_UOPB_employee;
    }

    public void setSupervising_UOPB_employee(User supervising_UOPB_employee) {
        this.supervising_UOPB_employee = supervising_UOPB_employee;
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

    public Integer getMaxPoliticalCreditPeriod() {
        return maxPoliticalCreditPeriod;
    }

    public void setMaxPoliticalCreditPeriod(Integer maxPoliticalCreditPeriod) {
        this.maxPoliticalCreditPeriod = maxPoliticalCreditPeriod;
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

    public Integer getMaxCommercialCreditPeriod() {
        return maxCommercialCreditPeriod;
    }

    public void setMaxCommercialCreditPeriod(Integer maxCommercialCreditPeriod) {
        this.maxCommercialCreditPeriod = maxCommercialCreditPeriod;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

}

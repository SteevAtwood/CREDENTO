package com.example.application.data;

import jakarta.persistence.*;

@Entity
public class Debtors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String companyName;
    private String address;
    private String informationProviderCode;
    private String companyRegistrationCodes;
    private String okvedCode;
    private String debtorCompanyEmail;
    private String companyStatus;
    private String ownerInformation;
    private String contactPersonDetails;

    public Debtors() {
    }

    public Debtors(String companyName, String address, String informationProviderCode, String companyRegistrationCodes,
            String okvedCode, String debtorCompanyEmail, String companyStatus, String ownerInformation,
            String contactPersonDetails) {
        this.companyName = companyName;
        this.address = address;
        this.informationProviderCode = informationProviderCode;
        this.companyRegistrationCodes = companyRegistrationCodes;
        this.okvedCode = okvedCode;
        this.debtorCompanyEmail = debtorCompanyEmail;
        this.companyStatus = companyStatus;
        this.ownerInformation = ownerInformation;
        this.contactPersonDetails = contactPersonDetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInformationProviderCode() {
        return informationProviderCode;
    }

    public void setInformationProviderCode(String informationProviderCode) {
        this.informationProviderCode = informationProviderCode;
    }

    public String getCompanyRegistrationCodes() {
        return companyRegistrationCodes;
    }

    public void setCompanyRegistrationCodes(String companyRegistrationCodes) {
        this.companyRegistrationCodes = companyRegistrationCodes;
    }

    public String getOkvedCode() {
        return okvedCode;
    }

    public void setOkvedCode(String okvedCode) {
        this.okvedCode = okvedCode;
    }

    public String getDebtorCompanyEmail() {
        return debtorCompanyEmail;
    }

    public void setDebtorCompanyEmail(String debtorCompanyEmail) {
        this.debtorCompanyEmail = debtorCompanyEmail;
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getOwnerInformation() {
        return ownerInformation;
    }

    public void setOwnerInformation(String ownerInformation) {
        this.ownerInformation = ownerInformation;
    }

    public String getContactPersonDetails() {
        return contactPersonDetails;
    }

    public void setContactPersonDetails(String contactPersonDetails) {
        this.contactPersonDetails = contactPersonDetails;
    }
}

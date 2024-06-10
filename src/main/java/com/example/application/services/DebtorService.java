package com.example.application.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.data.Debtors;
import com.example.application.data.Request;
import com.example.application.repository.DebtorRepository;
import com.example.application.repository.RequestRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import jakarta.transaction.Transactional;

@Service
public class DebtorService {

    @Autowired
    DebtorRepository debtorRepository;
    @Autowired
    RequestRepository requestRepository;

    @Transactional
    public Debtors createDebtor(String companyName, String address, String informationProviderCode,
            String companyRegistrationCodes, String okvedCode, String debtorCompanyEmail, String companyStatus,
            String ownerInformation, String contactPersonDetails) {

        Debtors debtor = new Debtors();
        debtor.setAddress(address);
        debtor.setCompanyName(companyName);
        debtor.setCompanyRegistrationCodes(companyRegistrationCodes);
        debtor.setCompanyStatus(companyStatus);
        debtor.setContactPersonDetails(contactPersonDetails);
        debtor.setDebtorCompanyEmail(debtorCompanyEmail);
        debtor.setInformationProviderCode(informationProviderCode);
        debtor.setOkvedCode(okvedCode);
        debtor.setOwnerInformation(ownerInformation);
        return debtorRepository.save(debtor);
    }

    public Debtors findDebtorById(Integer id) {
        System.out.println("Finding contract by id: " + id);
        Debtors debtors = debtorRepository.findById(id).orElse(null);
        System.out.println("contract: " + debtors);

        return debtors;
    }

    public Page<Debtors> list(Pageable pageable) {
        return debtorRepository.findAll(pageable);
    }

    public Page<Debtors> list(Pageable pageable, Specification<Debtors> filter) {
        return debtorRepository.findAll(filter, pageable);
    }

    public Optional<Debtors> get(Long id) {
        return debtorRepository.findById(id);
    }

    public Debtors update(Debtors entity) {
        return debtorRepository.save(entity);
    }

    @Transactional
    public void deleteDebtorById(Integer id) {
        debtorRepository.deleteById(id);
    }

    public List<Debtors> getDebtors() {
        return debtorRepository.findAll();
    }

    public Page<Request> getAcceptedRequestsByDebtorRegistrationCode(String registrationCode, Pageable pageable) {
        return requestRepository.getAcceptedRequestsByRegistrationCode(registrationCode, pageable);
    }
}

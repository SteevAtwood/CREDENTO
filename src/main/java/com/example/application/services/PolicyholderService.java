package com.example.application.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.data.Policyholder;
import com.example.application.repository.PolicyholderRepository;
import com.example.application.repository.RequestRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import jakarta.transaction.Transactional;

@Service
public class PolicyholderService {

    @Autowired
    PolicyholderRepository policyholderRepository;
    @Autowired
    RequestRepository requestRepository;

    @Transactional
    public Policyholder createPolicyholder(String companyName, String address, String informationProviderCode,
            String companyRegistrationCodes, String okvedCode, String policyholderCompanyEmail, String companyStatus,
            String ownerInformation, String contactPersonDetails) {

        Policyholder policyholder = new Policyholder();
        policyholder.setAddress(address);
        policyholder.setCompanyName(companyName);
        policyholder.setCompanyRegistrationCodes(companyRegistrationCodes);
        policyholder.setCompanyStatus(companyStatus);
        policyholder.setContactPersonDetails(contactPersonDetails);
        policyholder.setPolicyholderCompanyEmail(policyholderCompanyEmail);
        policyholder.setInformationProviderCode(informationProviderCode);
        policyholder.setOkvedCode(okvedCode);
        policyholder.setOwnerInformation(ownerInformation);
        return policyholderRepository.save(policyholder);
    }

    public Policyholder findPolicyholderById(Integer id) {
        Policyholder policyholder = policyholderRepository.findById(id).orElse(null);
        return policyholder;
    }

    public Page<Policyholder> list(Pageable pageable) {
        return policyholderRepository.findAll(pageable);
    }

    public Page<Policyholder> list(Pageable pageable, Specification<Policyholder> filter) {
        return policyholderRepository.findAll(filter, pageable);
    }

    public Optional<Policyholder> get(Long id) {
        return policyholderRepository.findById(id);
    }

    public Policyholder update(Policyholder entity) {
        return policyholderRepository.save(entity);
    }

    @Transactional
    public void deletePolicyholderById(Integer id) {
        policyholderRepository.deleteById(id);
    }

    public List<Policyholder> getPolicyholders() {
        return policyholderRepository.findAll();
    }

    public List<Policyholder> getAllPolicyholders() {
        return policyholderRepository.getPolicyholders();
    }
}

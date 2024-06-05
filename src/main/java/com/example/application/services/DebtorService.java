package com.example.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.data.Debtors;
import com.example.application.repository.DebtorRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import jakarta.transaction.Transactional;

@Service
public class DebtorService {

    @Autowired
    DebtorRepository debtorRepository;

    @Transactional
    public Debtors createDebtor(String companyName, String address, String informationProviderCode,
            String companyRegistrationCodes, String okvedCode, String debtorCompanyEmail, String companyStatus,
            String ownerInformation, String contactPersonDetails) {

        Debtors debtor = new Debtors(companyName, address, informationProviderCode, companyRegistrationCodes, okvedCode,
                debtorCompanyEmail, companyStatus, ownerInformation, contactPersonDetails);
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
}

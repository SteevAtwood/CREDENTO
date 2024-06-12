package com.example.application.services;

import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.data.Contract;
import com.example.application.data.Policyholder;
import com.example.application.data.User;
import com.example.application.data.coveredRisksEnum.CoveredRisksEnum;
import com.example.application.data.statusEnum.StatusEnum;
import com.example.application.repository.ContractsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import jakarta.transaction.Transactional;

@Service
public class ContractService {

    @Autowired
    ContractsRepository contractsRepository;

    @Transactional
    public Contract createContract(
            String insuranceContractNumber,
            String insurer,
            StatusEnum status,
            LocalDate startDateOfInsuranceCoverage,
            LocalDate endDateOfInsuranceCoverage,
            User supervisingUnderwriter,
            User supervisingUOPBEmployee,
            Policyholder policyholder,
            Set<String> coveredCountries,
            CoveredRisksEnum coveredRisks,
            String insuredSharePolitical,
            Integer waitingPeriodPolitical,
            Integer maxPoliticalCreditPeriod,
            String insuredShareCommercial,
            Integer waitingPeriodCommercial,
            Integer maxCommercialCreditPeriod,
            String clientName) {

        Contract contract = new Contract();
        contract.setInsuranceContractNumber(insuranceContractNumber);
        contract.setInsurer(insurer);
        contract.setStatus(status);
        contract.setStartDateOfInsuranceCoverage(startDateOfInsuranceCoverage);
        contract.setEndDateOfInsuranceCoverage(endDateOfInsuranceCoverage);
        contract.setSupervisingUnderwriter(supervisingUnderwriter);
        contract.setSupervising_UOPB_employee(supervisingUOPBEmployee);
        contract.setPolicyholder(policyholder);

        String countries = String.join(",", coveredCountries);
        contract.setCoveredCountries(countries);

        contract.setCoveredRisks(coveredRisks);
        contract.setInsuredSharePolitical(insuredSharePolitical);
        contract.setWaitingPeriodPolitical(waitingPeriodPolitical);
        contract.setMaxPoliticalCreditPeriod(maxPoliticalCreditPeriod);
        contract.setInsuredShareCommercial(insuredShareCommercial);
        contract.setWaitingPeriodCommercial(waitingPeriodCommercial);
        contract.setMaxCommercialCreditPeriod(maxCommercialCreditPeriod);
        contract.setClientName(clientName);

        return contractsRepository.save(contract);
    }

    public Page<Contract> list(Pageable pageable) {
        return contractsRepository.findAll(pageable);
    }

    public Page<Contract> list(Pageable pageable, Specification<Contract> filter) {
        return contractsRepository.findAll(filter, pageable);
    }

    public Contract findContractById(Integer id) {
        Contract contract = contractsRepository.findById(id).orElse(null);
        return contract;
    }

    public Contract update(Contract entity) {
        return contractsRepository.save(entity);
    }

    // public Contract update(String insuranceContractNumber,
    // String insurer,
    // StatusEnum status,
    // LocalDate startDateOfInsuranceCoverage,
    // LocalDate endDateOfInsuranceCoverage,
    // Integer supervisingUnderwriter,
    // Integer supervisingUOPBEmployee,
    // String policyholder,
    // String coveredCountries,
    // CoveredRisksEnum coveredRisks,
    // String insuredSharePolitical,
    // Integer waitingPeriodPolitical,
    // Integer maxPoliticalCreditPeriod,
    // String insuredShareCommercial,
    // Integer waitingPeriodCommercial,
    // Integer maxCommercialCreditPeriod,
    // String clientName) {

    // Contract contract = new Contract();
    // contract.setInsuranceContractNumber(insuranceContractNumber);
    // contract.setInsurer(insurer);
    // contract.setStatus(status);
    // contract.setStartDateOfInsuranceCoverage(startDateOfInsuranceCoverage);
    // contract.setEndDateOfInsuranceCoverage(endDateOfInsuranceCoverage);
    // contract.setSupervisingUnderwriter(supervisingUnderwriter);
    // contract.setSupervising_UOPB_employee(supervisingUOPBEmployee);
    // contract.setPolicyholder(policyholder);
    // contract.setCoveredCountries(coveredCountries);
    // contract.setCoveredRisks(coveredRisks);
    // contract.setInsuredSharePolitical(insuredSharePolitical);
    // contract.setWaitingPeriodPolitical(waitingPeriodPolitical);
    // contract.setMaxPoliticalCreditPeriod(maxPoliticalCreditPeriod);
    // contract.setInsuredShareCommercial(insuredShareCommercial);
    // contract.setWaitingPeriodCommercial(waitingPeriodCommercial);
    // contract.setMaxCommercialCreditPeriod(maxCommercialCreditPeriod);
    // contract.setClientName(clientName);

    // return contractsRepository.save(contract);
    // }

    public Optional<Contract> get(Long id) {
        return contractsRepository.findById(id);
    }

    @Transactional
    public void deleteContractById(Integer id) {
        contractsRepository.deleteById(id);
    }
}

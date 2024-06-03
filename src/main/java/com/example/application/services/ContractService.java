package com.example.application.services;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.data.Contract;
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
    public Contract createContract(String insuranceContractNumber, String insurer, StatusEnum status,
            Timestamp startDateOfInsuranceCoverage,
            Timestamp endDateOfInsuranceCoverage, String supervisingUnderwriter, String supervising_UOPB_employee,
            String policyholder, String coveredCountries, CoveredRisksEnum coveredRisks, String insuredSharePolitical,
            Integer waitingPeriodPolitical, Integer maxCommercialCreditPeriodPolitical, String insuredShareCommercial,
            Integer waitingPeriodCommercial, Integer maxCommercialCreditPeriodCommercial, String clientName) {

        Contract contract = new Contract();
        contract.setClientName(clientName);
        contract.setCoveredCountries(coveredCountries);
        contract.setCoveredRisks(coveredRisks);
        contract.setEndDateOfInsuranceCoverage(endDateOfInsuranceCoverage);
        contract.setInsuranceContractNumber(insuranceContractNumber);
        contract.setInsuredSharePolitical(insuredSharePolitical);
        contract.setInsurer(insurer);
        contract.setMaxCommercialCreditPeriodCommercial(maxCommercialCreditPeriodCommercial);
        contract.setMaxCommercialCreditPeriodPolitical(maxCommercialCreditPeriodPolitical);
        contract.setPolicyholder(policyholder);
        contract.setStartDateOfInsuranceCoverage(startDateOfInsuranceCoverage);
        contract.setStatus(status);
        contract.setSupervising_UOPB_employee(supervising_UOPB_employee);
        contract.setSupervisingUnderwriter(supervisingUnderwriter);
        contract.setWaitingPeriodCommercial(waitingPeriodCommercial);
        contract.setWaitingPeriodPolitical(waitingPeriodPolitical);
        return contractsRepository.save(contract);
    }

    public Page<Contract> list(Pageable pageable) {
        return contractsRepository.findAll(pageable);
    }

    public Page<Contract> list(Pageable pageable, Specification<Contract> filter) {
        return contractsRepository.findAll(filter, pageable);
    }

}

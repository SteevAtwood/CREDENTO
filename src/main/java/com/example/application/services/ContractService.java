package com.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.data.Contract;
import com.example.application.repository.ContractsRepository;

@Service
public class ContractService {

    @Autowired
    ContractsRepository contractsRepository;

    public Contract createContract(Contract newContract) {
        return contractsRepository.save(newContract);
    }

}

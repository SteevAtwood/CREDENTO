package com.example.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.application.data.Contract;

@Repository
public interface ContractsRepository extends JpaRepository<Contract, Integer>, JpaSpecificationExecutor<Contract> {

    Optional<Contract> findById(Long id);

    Optional<Contract> findByInsuranceContractNumber(String insuranceContractNumber);

    boolean existsByInsuranceContractNumber(String insuranceContractNumber);
}

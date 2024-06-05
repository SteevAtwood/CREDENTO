package com.example.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.application.data.Debtors;

@Repository
public interface DebtorRepository extends JpaRepository<Debtors, Integer>, JpaSpecificationExecutor<Debtors> {
    Optional<Debtors> findById(Long id);
}

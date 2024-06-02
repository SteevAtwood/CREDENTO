package com.example.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.application.data.Contract;

@Repository
public interface ContractsRepository extends JpaRepository<Contract, Integer> {

}

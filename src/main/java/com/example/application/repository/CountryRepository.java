package com.example.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.application.data.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer>, JpaSpecificationExecutor<Country> {

}

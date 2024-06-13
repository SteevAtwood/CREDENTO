package com.example.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.application.data.Debtors;

@Repository
public interface DebtorRepository extends JpaRepository<Debtors, Integer>, JpaSpecificationExecutor<Debtors> {

    Optional<Debtors> findById(Long id);

    @Query(nativeQuery = true, value = """
              select d.* from debtors d
              join contract c ON d.insurance_contract_number = c.id
              join policyholder ph ON c.policyholder = ph.id where ph.company_name = :companyName
            """)
    List<Debtors> getPolicyholdersByDebtor(String companyName);
}

// SELECT d.*
// FROM debtors d
// JOIN contract c ON d.insurance_contract_number = c.id
// JOIN policyholder ph ON c.policyholder = ph.id
// WHERE ph.company_name = 'Formula 1';

package com.example.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.application.data.Policyholder;

@Repository
public interface PolicyholderRepository
        extends JpaRepository<Policyholder, Integer>, JpaSpecificationExecutor<Policyholder> {

    Optional<Policyholder> findById(Long id);

    @Query(nativeQuery = true, value = """
              select * from policyholder
            """)
    List<Policyholder> getPolicyholders();
}

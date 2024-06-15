package com.example.application.repository;

import com.example.application.data.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface RequestRepository extends JpaRepository<Request, Integer>, JpaSpecificationExecutor<Request> {

    @Query(nativeQuery = true, value = """
                select * from request (where status = 'accepted' or status = 'declined') and debtor = :debtorId
            """)
    Page<Request> getAcceptedRequestsByDebtorId(Integer debtorId, Pageable pageable);

    @Query(nativeQuery = true, value = """
                select * from request where status = 'pending' and debtor = :debtorId
            """)
    Page<Request> getPendingRequestsByDebtorId(Integer debtorId, Pageable pageable);

    @Query(nativeQuery = true, value = """
                select * from request (where status = 'accepted' or status = 'declined') and insurance_contract_number = :insuranceContractNumber
            """)
    Page<Request> getAcceptedRequestsByContract(Integer insuranceContractNumber, Pageable pageable);

    @Query(nativeQuery = true, value = """
                select * from request where status = 'pending' and insurance_contract_number = :insuranceContractNumber
            """)
    Page<Request> getPendingRequestsByContract(Integer insuranceContractNumber, Pageable pageable);
}

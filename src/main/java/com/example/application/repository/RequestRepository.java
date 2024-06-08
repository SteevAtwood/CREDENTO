package com.example.application.repository;

import com.example.application.data.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface RequestRepository extends JpaRepository<Request, Integer>, JpaSpecificationExecutor<Request> {

    @Query(nativeQuery = true, value = """
              select * from request where status = 'accepted'
            """)
    Page<Request> getAcceptedRequestsByDebtor(Pageable pageable);
}

package com.example.application.repository;

import com.example.application.data.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface RequestRepository extends JpaRepository<Request, Integer>, JpaSpecificationExecutor<Request> {

  @Query(nativeQuery = true, value = """
          select * from request where status = 'accepted' and status = 'declined' and registration_code = :registrationCode
      """)
  Page<Request> getAcceptedRequestsByRegistrationCode(String registrationCode, Pageable pageable);

  @Query(nativeQuery = true, value = """
          select * from request where status = 'pending' and registration_code = :registrationCode
      """)
  Page<Request> getPendingRequestsByRegistrationCode(String registrationCode, Pageable pageable);
}

package com.example.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.application.data.User;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

  User findByUsername(String username);

  @Query(nativeQuery = true, value = """
        select * from user where position = 'mainUnderwriter'
      """)
  List<User> getUsersWithRoleMainUnderwriter();

  @Query(nativeQuery = true, value = """
        select * from user where position = 'underwriter'
      """)
  List<User> getUsersWithRoleUnderwriter();

  @Query(nativeQuery = true, value = """
        select * from user where position = 'supervisingUOPBemployee'
      """)
  List<User> getUsersWithRoleSupervisingUOPBEmployee();

  User findByName(String name);

}

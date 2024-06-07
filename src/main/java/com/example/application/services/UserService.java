package com.example.application.services;

import com.example.application.data.User;
import com.example.application.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }

    public User update(User entity) {
        return userRepository.save(entity);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public Page<User> list(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> list(Pageable pageable, Specification<User> filter) {
        return userRepository.findAll(filter, pageable);
    }

    public int count() {
        return (int) userRepository.count();
    }

    public List<User> getUsersWithRoleMainUndewtirher() {
        return userRepository.getUsersWithRoleMainUndewtirher();
    }

    public List<User> getUsersWithRoleSupervisingUOPBemployee() {
        return userRepository.getUsersWithRoleSupervisingUOPBemployee();
    }

}

package com.example.application.services;

import com.example.application.data.User;
import com.example.application.repository.UserRepository;

import java.util.List;

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

    public User get(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User update(User entity) {
        return userRepository.save(entity);
    }

    public void delete(Integer id) {
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

    public List<User> getUsersWithRoleMainUnderwriter() {
        return userRepository.getUsersWithRoleMainUnderwriter();
    }

    public List<User> getUsersWithRoleSupervisingUOPBEmployee() {
        return userRepository.getUsersWithRoleSupervisingUOPBEmployee();
    }

    public Integer getUserIdByName(String name) {
        User user = userRepository.findByName(name);
        if (user != null) {
            return user.getId();
        } else {
            return 0;
        }
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    // public User getUserByName(String userName) {
    // for (User user : userList) {
    // if (user.getName().equals(userName)) {
    // return user;
    // }
    // }
    // return null;
}

package com.example.application.services;

import com.example.application.data.User;
import com.example.application.data.positionEnum.PositionEnum;
import com.example.application.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public User createUser(String username, String name, PositionEnum position, String hashedPassword) {
        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setPosition(position);
        user.setHashedPassword(hashPassword(user.getHashedPassword()));
        return userRepository.save(user);
    }

    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка при хешировании пароля", e);
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
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

    public List<User> getUsersWithRoleUnderwriter() {
        return userRepository.getUsersWithRoleUnderwriter();
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

}

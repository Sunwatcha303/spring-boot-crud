package com.main.spring_boot_crud.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.spring_boot_crud.model.User;
import com.main.spring_boot_crud.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User updateUser(User user) {
        User findUser = userRepository.findById(user.getId()).orElse(null);
        if (findUser != null) {
            findUser.setUsername(user.getUsername());
            findUser.setEmail(user.getEmail());
            findUser.setPassword(user.getPassword());
            return userRepository.save(findUser);
        } else {
            return null;
        }
    }

    @Transactional
    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }
}

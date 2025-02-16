package com.main.spring_boot_crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.main.spring_boot_crud.model.User;
import com.main.spring_boot_crud.model.UserRequest;
import com.main.spring_boot_crud.model.UserResponse;
import com.main.spring_boot_crud.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(user.toResponse());
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers().stream().map(User::toResponse).toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delete Failed");
        }
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Delete Successfully");
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody User user) {
        if (userService.getUserById(user.getId()) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(user).toResponse());
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest newUser) {
        User user = userService.getUserByUsername(newUser.getUsername());
        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        user = new User(newUser);
        User userRes = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRes.toResponse());
    }
}

package com.shayan.journalyt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.*;

import com.shayan.journalyt.entity.User;
import com.shayan.journalyt.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        return ResponseEntity.ok(new ApiResponse(userService.getAll(), "Succesfully retrieved all users"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(new ApiResponse(userService.getUserById(id), "Succesfully retrieved user"));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "User not found"));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createNewUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return ResponseEntity.status(CREATED).body(new ApiResponse(user, "Created new User"));
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse(null, "Failed to create user"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "User not found"));
        }
    }
}

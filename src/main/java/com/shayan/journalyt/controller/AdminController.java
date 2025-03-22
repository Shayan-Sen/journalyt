package com.shayan.journalyt.controller;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shayan.journalyt.entity.User;
import com.shayan.journalyt.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<User> users = userService.getAll();
        if (users != null && !users.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse( users, "Users retrieved successfully"));
        }else{
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null,"No users found"));
        }
    }
}

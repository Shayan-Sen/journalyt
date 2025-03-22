package com.shayan.journalyt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shayan.journalyt.entity.User;
import com.shayan.journalyt.service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "I am alive";
    }

    @PostMapping("/create-user")
    public ResponseEntity<ApiResponse> createNewUser(@RequestBody User user) {
        try {
            userService.saveNewUser(user);
            return ResponseEntity.status(CREATED).body(new ApiResponse(user, "Created new User"));
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(new ApiResponse(null, "Failed to create user: " + e.getMessage()));
        }
    }
}

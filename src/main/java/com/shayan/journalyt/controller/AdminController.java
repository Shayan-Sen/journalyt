package com.shayan.journalyt.controller;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shayan.journalyt.entity.JournalEntry;
import com.shayan.journalyt.entity.User;
import com.shayan.journalyt.service.JournalEntryService;
import com.shayan.journalyt.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/all-users")
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<User> users = userService.getAll();
        if (users != null && !users.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(users, "Users retrieved successfully"));
        } else {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "No users found"));
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable ObjectId id) {
        try {
            return ResponseEntity.ok(new ApiResponse(userService.getUserById(id),
                    "Succesfully retrieved user"));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "User not found"));
        }
    }

    @GetMapping("/all-entries")
    public ResponseEntity<ApiResponse> getAllEntries(){
        List<JournalEntry> entries = journalEntryService.getAll();
        if (entries != null && !entries.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(entries, "Entries retrieved successfully"));
        }else{
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "No entries found"));
        }
    }

    @PostMapping("/create-admin")
    public ResponseEntity<ApiResponse> createAdmin(@RequestBody User user) {
        try {
            userService.saveAdmin(user);
            return ResponseEntity.status(CREATED).body(new ApiResponse(user, "Added new admin"));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(null, e.getMessage()));
        }
    }
}

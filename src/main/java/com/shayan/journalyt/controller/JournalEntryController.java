package com.shayan.journalyt.controller;

import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shayan.journalyt.entity.JournalEntry;
import com.shayan.journalyt.entity.User;
import com.shayan.journalyt.service.JournalEntryService;
import com.shayan.journalyt.service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;



    @Autowired
    private UserService userService;

    @GetMapping("/user/{username}")
    public ResponseEntity<ApiResponse> getAllJournalEntriesOfUser(@PathVariable String username) {

        User user = userService.findByUsername(username);
        List<JournalEntry> entries = user.getJournalEntries();

        return ResponseEntity.ok(new ApiResponse(entries, "Successfully Retrieved All Journal Entries of " + username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getJournalEntrybyId(@PathVariable String id) {
        try {
            JournalEntry entry = journalEntryService.getEntryById(id);

            return ResponseEntity
                    .ok(new ApiResponse(entry, "Successfully Retrieved Journal Entry"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            journalEntryService.saveEntry(journalEntry);

            return ResponseEntity.status(CREATED).body(new ApiResponse(journalEntry, "New Journal Entry Created"));
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateEntry(@PathVariable String id, @RequestBody JournalEntry journalEntry) {
        try {
            JournalEntry updatedEntry = journalEntryService.updateById(id, journalEntry);

            return ResponseEntity.status(ACCEPTED)
                    .body(new ApiResponse(updatedEntry, "Changed Journal Entry with id:" + id));
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteEntry(@PathVariable String id) {
        try {
            journalEntryService.deleteEntry(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }
}
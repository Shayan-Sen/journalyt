package com.shayan.journalyt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.http.HttpStatus.*;

import java.util.List;
import java.util.stream.Collectors;

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
import com.shayan.journalyt.service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private JournalAssembler assembler;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        List<EntityModel<JournalEntry>> entries = journalEntryService.getAll().stream().map(assembler::toModel)
                .collect(Collectors.toList());
        CollectionModel<EntityModel<JournalEntry>> collectionModel = CollectionModel.of(entries,
                linkTo(methodOn(JournalEntryController.class).getAll()).withSelfRel());
        return ResponseEntity.ok(new ApiResponse(collectionModel, "Successfully Retrieved All Journal Entries"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getJournalEntrybyId(@PathVariable String id) {
        try {
            EntityModel<JournalEntry> entry = assembler.toModel(journalEntryService.getEntryById(id));

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
            EntityModel<JournalEntry> entry = assembler.toModel(journalEntry);
            return ResponseEntity.status(CREATED).body(new ApiResponse(entry, "New Journal Entry Created"));
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateEntry(@PathVariable String id, @RequestBody JournalEntry journalEntry) {
        try {
            JournalEntry updatedEntry = journalEntryService.updateById(id, journalEntry);
            EntityModel<JournalEntry> entry = assembler.toModel(updatedEntry);
            return ResponseEntity.status(ACCEPTED)
                    .body(new ApiResponse(entry, "Changed Journal Entry with id:" + id));
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
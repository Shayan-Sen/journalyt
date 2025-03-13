package com.shayan.journalyt.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.shayan.journalyt.entity.JournalEntry;

@Component
public class JournalAssembler implements RepresentationModelAssembler<JournalEntry, EntityModel<JournalEntry>> {

    @Override
    @NonNull
    public EntityModel<JournalEntry> toModel(@NonNull JournalEntry journalEntry) {
        return EntityModel.of(journalEntry,
                linkTo(methodOn(JournalEntryController.class).getJournalEntrybyId(journalEntry.getId())).withSelfRel(),
                linkTo(methodOn(JournalEntryController.class).getAll()).withRel("journal_entries"));
    }
}

package com.shayan.journalyt.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shayan.journalyt.entity.JournalEntry;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {

}

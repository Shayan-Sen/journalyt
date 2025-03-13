package com.shayan.journalyt.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NonNull;

@Data
@Document(collection = "journal_entries")
public class JournalEntry {

    @Id
    private String id = UUID.randomUUID().toString();

    @NonNull
    private String title;

    private String content;
    
    private LocalDateTime date = LocalDateTime.now();
}

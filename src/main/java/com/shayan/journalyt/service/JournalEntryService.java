package com.shayan.journalyt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shayan.journalyt.entity.JournalEntry;
import com.shayan.journalyt.repository.JournalEntryRepository;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public JournalEntry getEntryById(String id) {
        return journalEntryRepository.findById(id).orElseThrow(() -> new RuntimeException("Entry not found"));
    }

    public JournalEntry updateById(String id, JournalEntry updatedEntry) {
        JournalEntry entry = journalEntryRepository.findById(id)
                .orElse(null);
        if (entry != null) {
            entry.setTitle(updatedEntry.getTitle());
            entry.setContent(updatedEntry.getContent());
            entry.setDate(updatedEntry.getDate());
        } else {
            entry = updatedEntry;
            entry.setId(id);
        }
        journalEntryRepository.save(entry);
        return entry;
    }

    public void deleteEntry(String id) {
        journalEntryRepository.deleteById(id);
    }

}

// controller ---> service ---> repository ---> database
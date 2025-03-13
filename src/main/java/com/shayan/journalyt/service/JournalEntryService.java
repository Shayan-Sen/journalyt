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
        try {
            journalEntryRepository.save(journalEntry);
        } catch (Exception e) {
            throw new RuntimeException("Error saving entry: " + e.getMessage());
        }
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public JournalEntry getEntryById(String id) {
        try {
            return journalEntryRepository.findById(id).orElseThrow(() -> new RuntimeException("Entry not found"));
        } catch (Exception e) {
            throw new RuntimeException("Error getting entry: " + e.getMessage());
        }
    }

    public JournalEntry updateById(String id, JournalEntry updatedEntry) {
        try {
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
        } catch (Exception e) {
            throw new RuntimeException("Error updating entry: " + e.getMessage());
        }
    }

    public void deleteEntry(String id) {
        try {
            journalEntryRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting entry: " + e.getMessage());
        }
    }

}

// controller ---> service ---> repository ---> database
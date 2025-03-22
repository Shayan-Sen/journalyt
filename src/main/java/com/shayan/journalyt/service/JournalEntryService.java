package com.shayan.journalyt.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shayan.journalyt.entity.JournalEntry;
import com.shayan.journalyt.entity.User;
import com.shayan.journalyt.repository.JournalEntryRepository;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

// not implemented yet
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        try {
            User user = userService.findByUsername(username);
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Error saving entry: " + e.getMessage());
        }
    }

// not implemented yet
    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

// journal
    public JournalEntry getEntryById(ObjectId id, String username) {
        try {
            User user = userService.findByUsername(username);
            JournalEntry entry = journalEntryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Entry not found"));
            if (user.getJournalEntries().contains(entry)) return entry;
            else throw new RuntimeException("Entry not found");
        } catch (Exception e) {
            throw new RuntimeException("Error getting entry: " + e.getMessage());
        }
    }

// not implemented yet
    @Transactional
    public JournalEntry updateById(ObjectId id, JournalEntry updatedEntry, String username) {
        try {
            User user = userService.findByUsername(username);
            JournalEntry journalEntry = journalEntryRepository.findById(id)
                    .orElse(null);
            JournalEntry entry = user.getJournalEntries().contains(journalEntry) ? journalEntry : null;
            if (entry != null) {
                entry.setTitle(updatedEntry.getTitle());
                entry.setContent(updatedEntry.getContent());
                entry.setDate(updatedEntry.getDate());
            } else {
                entry = updatedEntry;
            }
            journalEntryRepository.save(entry);
            return entry;
        } catch (Exception e) {
            throw new RuntimeException("Error updating entry: " + e.getMessage());
        }
    }

// not implemented yet
    @Transactional
    public void deleteEntry(ObjectId id, String username) {
        try {
            User user = userService.findByUsername(username);
            boolean ifremoved = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (ifremoved) {
                journalEntryRepository.deleteById(id);
                userService.saveUser(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting entry: " + e.getMessage());
        }
    }

}

// controller ---> service ---> repository ---> database
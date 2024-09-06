package com.gbuxss.journalApp.service;

import com.gbuxss.journalApp.entity.JournalEntry;
import com.gbuxss.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries() {

        return journalEntryRepository.findAll();
    }

    public JournalEntry getEntryByID(ObjectId ID) {
        return journalEntryRepository.findById(ID).orElse(null);
    }

    public void deleteByID(ObjectId ID) {
        journalEntryRepository.deleteById(ID);
    }
}

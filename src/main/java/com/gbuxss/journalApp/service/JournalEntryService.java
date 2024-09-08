package com.gbuxss.journalApp.service;

import com.gbuxss.journalApp.entity.JournalEntry;
import com.gbuxss.journalApp.entity.User;
import com.gbuxss.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User userInDB = userService.findByUserName(userName);
            JournalEntry savedJournal = journalEntryRepository.save(journalEntry);
            userInDB.getUserJournal().add(savedJournal);
            userService.saveEntry(userInDB);
        } catch (Exception e) {
                throw new RuntimeException("Something went wrong");
        }

    }

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

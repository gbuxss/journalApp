package com.gbuxss.journalApp.controller;

import com.gbuxss.journalApp.entity.JournalEntry;
import com.gbuxss.journalApp.entity.User;
import com.gbuxss.journalApp.service.JournalEntryService;

import com.gbuxss.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController()
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalOfUser(@PathVariable String userName) {
        User userInDB = userService.findByUserName(userName);
        List<JournalEntry> allEntries = userInDB.getUserJournal();
        if (allEntries != null && !allEntries.isEmpty()) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String userName) {
        try {

            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalEntry, userName);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{entryID}")
    public ResponseEntity<?> getJournalEntryByID(@PathVariable ObjectId entryID) {
        JournalEntry entryByID = journalEntryService.getEntryByID(entryID);
        if (entryByID != null) {
            return new ResponseEntity<>(entryByID, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("id/{userName}/{entryID}")
    public ResponseEntity<?> deleteJournalEntryByID(@PathVariable ObjectId entryID, @PathVariable String userName) {
        User userInDB = userService.findByUserName(userName);
        userInDB.getUserJournal().removeIf(x -> x.getId().equals(entryID));
        userService.saveEntry(userInDB);
        journalEntryService.deleteByID(entryID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{entryID}")
    public ResponseEntity<?> updateJournalEntryByID(@PathVariable ObjectId entryID, @RequestBody JournalEntry UpdatedJournalEntry, @PathVariable String userName) {
        JournalEntry oldEntry = journalEntryService.getEntryByID(entryID);
        if (oldEntry != null) {
            oldEntry.setTitle(UpdatedJournalEntry.getTitle() != null && !UpdatedJournalEntry.getTitle().isEmpty() ? UpdatedJournalEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(UpdatedJournalEntry.getContent() != null && !UpdatedJournalEntry.getContent().isEmpty() ? UpdatedJournalEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

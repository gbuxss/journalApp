package com.gbuxss.journalApp.controller;

import com.gbuxss.journalApp.entity.JournalEntry;
import com.gbuxss.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping
    public ResponseEntity<?> getAll() {
        List<JournalEntry> allEntries = journalEntryService.getAllEntries();
        if (allEntries != null && !allEntries.isEmpty()) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalEntry);
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

    @DeleteMapping("id/{entryID}")
    public ResponseEntity<?> deleteJournalEntryByID(@PathVariable ObjectId entryID) {
        journalEntryService.deleteByID(entryID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{entryID}")
    public ResponseEntity<?> updateJournalEntryByID(@PathVariable ObjectId entryID, @RequestBody JournalEntry UpdatedJournalEntry) {
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

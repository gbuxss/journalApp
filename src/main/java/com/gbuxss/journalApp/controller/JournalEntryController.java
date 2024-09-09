package com.gbuxss.journalApp.controller;

import com.gbuxss.journalApp.entity.JournalEntry;
import com.gbuxss.journalApp.entity.User;
import com.gbuxss.journalApp.service.JournalEntryService;

import com.gbuxss.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDB = userService.findByUserName(userName);
        List<JournalEntry> allEntries = userInDB.getUserJournal();
        if (allEntries != null && !allEntries.isEmpty()) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalEntry, userName);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{entryID}")
    public ResponseEntity<?> getJournalEntryByID(@PathVariable ObjectId entryID) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> listOfJournalEntries = user.getUserJournal().stream().filter(journalEntry -> journalEntry.getId().equals(entryID)).collect(Collectors.toList());
        if (!listOfJournalEntries.isEmpty()) {
            JournalEntry entryByID = journalEntryService.getEntryByID(entryID);
            if (entryByID != null) {
                return new ResponseEntity<>(entryByID, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{entryID}")
    public ResponseEntity<?> deleteJournalEntryByID(@PathVariable ObjectId entryID) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Boolean removed = journalEntryService.deleteByID(entryID, userName);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{entryID}")
    public ResponseEntity<?> updateJournalEntryByID(@PathVariable ObjectId entryID, @RequestBody JournalEntry UpdatedJournalEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> journalEntries = user.getUserJournal().stream().filter(journalEntry -> journalEntry.getId().equals(entryID)).collect(Collectors.toList());
        if (!journalEntries.isEmpty()) {
            JournalEntry oldEntry = journalEntryService.getEntryByID(entryID);
            if (oldEntry != null) {
                oldEntry.setTitle(UpdatedJournalEntry.getTitle() != null && !UpdatedJournalEntry.getTitle().isEmpty() ? UpdatedJournalEntry.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(UpdatedJournalEntry.getContent() != null && !UpdatedJournalEntry.getContent().isEmpty() ? UpdatedJournalEntry.getContent() : oldEntry.getContent());
                journalEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

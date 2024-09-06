package com.gbuxss.journalApp.controller;

import com.gbuxss.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/journals")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntryMap = new HashMap<>();


    @GetMapping
    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntryMap.values());
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry journalEntry) {
        return journalEntryMap.put(journalEntry.getId(), journalEntry);
    }

    @GetMapping("id/{entryID}")
    public JournalEntry getJournalEntryByID(@PathVariable long entryID) {
        return journalEntryMap.get(entryID);
    }

    @DeleteMapping("id/{entryID}")
    public String deleteJournalEntryByID(@PathVariable long entryID) {

        journalEntryMap.remove(entryID);
        return entryID + "Deleted";
    }

    @PutMapping("id/{entryID}")
    public JournalEntry updateJournalEntryByID(@PathVariable long entryID, @RequestBody JournalEntry journalEntry) {

        return journalEntryMap.put(journalEntry.getId(), journalEntry);
    }
}

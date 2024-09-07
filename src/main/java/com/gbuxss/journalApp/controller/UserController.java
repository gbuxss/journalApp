package com.gbuxss.journalApp.controller;

import com.gbuxss.journalApp.entity.JournalEntry;
import com.gbuxss.journalApp.service.JournalEntryService;
import com.gbuxss.journalApp.service.UserService;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController()
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public void createNewUser(@RequestBody User newUser) {
        userService.saveEntry(newUser);
    }

    @PutMapping
    public ResponseEntity<?> updateUser (@RequestBody User user) {
        User userInDB = userService.findByUserName(user.getUsername());
        if (userInDB != null) {
            userInDB.setUsername(user.getUsername());
            userInDB.setPassword(user.getPassword());
            userService.saveEntry(userInDB);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}

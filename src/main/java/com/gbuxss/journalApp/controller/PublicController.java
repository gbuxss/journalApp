package com.gbuxss.journalApp.controller;

import com.gbuxss.journalApp.entity.User;
import com.gbuxss.journalApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final UserService userService;

    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }


    @PostMapping("create-user")
    public ResponseEntity<?> createNewUser(@RequestBody User newUser) {
        try {
            userService.saveNewUser(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

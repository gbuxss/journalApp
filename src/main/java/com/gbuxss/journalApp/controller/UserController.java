package com.gbuxss.journalApp.controller;

import com.gbuxss.journalApp.entity.User;
import com.gbuxss.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser (@RequestBody User user, @PathVariable String userName) {
        User userInDB = userService.findByUserName(userName);
        if (userInDB != null) {
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());
            userService.saveEntry(userInDB);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }






}

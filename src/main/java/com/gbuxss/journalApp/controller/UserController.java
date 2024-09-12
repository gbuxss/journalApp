package com.gbuxss.journalApp.controller;

import com.gbuxss.journalApp.api.response.WeatherResponse;
import com.gbuxss.journalApp.entity.User;
import com.gbuxss.journalApp.repository.UserRepository;
import com.gbuxss.journalApp.service.UserService;
import com.gbuxss.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController()
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final WeatherService weatherService;


    public UserController(UserService userService, UserRepository userRepository, WeatherService weatherService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.weatherService = weatherService;

    }



    @PutMapping
    public ResponseEntity<?> updateUser (@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDB = userService.findByUserName(userName);
        userInDB.setUserName(user.getUserName());
        userInDB.setPassword(user.getPassword());
        userService.saveNewUser(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping
    public ResponseEntity<?> getWeatherData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        WeatherResponse response = weatherService.getWeather(user.getCity());
        String greeting = "";
        if (response != null) {
            greeting = " Weather feels like " + response.getCurrent().getFeelslike() ;
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greeting , HttpStatus.OK);

    }






}

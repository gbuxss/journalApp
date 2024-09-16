package com.gbuxss.journalApp.controller;

import com.gbuxss.journalApp.entity.User;
import com.gbuxss.journalApp.service.UserDetailsServiceImp;
import com.gbuxss.journalApp.service.UserService;
import com.gbuxss.journalApp.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private JWTUtil jwtUtil;

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImp userDetailsServiceImp;
    // final JWTUtil jwtUtil;

    public PublicController(UserService userService, AuthenticationManager authenticationManager, UserDetailsServiceImp userDetailsServiceImp) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsServiceImp = userDetailsServiceImp;
        //this.jwtUtil = jwtUtil;
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User newUser) {
        try {
            userService.saveNewUser(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody User user) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            log.info("User is successfully authenticated");
            UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(user.getUserName());
            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception occurred "+ e);
            return new ResponseEntity<>("User cannot be successfully authenticated, Incorrect Username Password",HttpStatus.BAD_REQUEST);
        }
    }
}

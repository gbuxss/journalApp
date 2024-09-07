package com.gbuxss.journalApp.service;

import com.gbuxss.journalApp.repository.UserRepository;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user) {

        userRepository.save(user);
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public User getUserByID(ObjectId ID) {

        return userRepository.findById(ID).orElse(null);
    }

    public void deleteUserByID(ObjectId ID) {

        userRepository.deleteById(ID);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }
}

package com.github.spring.controller;

import com.github.spring.model.UserProfile;
import com.github.spring.repository.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-profile")
class UserProfileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);

    @Autowired
    private UserProfileRepository repository;

    @PostMapping("/")
    public UserProfile add(@RequestBody UserProfile userProfile) {
        LOGGER.info("UserProfile add: {}", userProfile);
        return repository.add(userProfile);
    }

    @GetMapping("/{id}")
    public UserProfile findById(@PathVariable("id") Long id) {
        LOGGER.info("UserProfile find: id={}", id);
        return repository.findById(id);
    }

    @GetMapping("/")
    public List<UserProfile> findAll() {
        LOGGER.info("UserProfile find");
        return repository.findAll();
    }

}

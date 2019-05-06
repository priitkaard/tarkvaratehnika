package com.qaengine.controllers;

import com.qaengine.database.UserRepository;
import com.qaengine.database.VoteRepository;
import com.qaengine.exceptions.BadRequestException;
import com.qaengine.models.ApplicationUser;
import com.qaengine.models.DTO.ApplicationUserDTO;
import com.qaengine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/sign-up")
    public ApplicationUser signUp(@Valid @RequestBody ApplicationUserDTO userInput) {
        userService.findByUsername(userInput.getUsername()).ifPresent(user -> {
            throw new BadRequestException("ApplicationUser with given username already exists.");
        });
        return userService.createUser(userInput);
    }

    @GetMapping("/user/points/{username}")
    public int points(@PathVariable String username){
        return userService.getUserScore(username);
    }
}
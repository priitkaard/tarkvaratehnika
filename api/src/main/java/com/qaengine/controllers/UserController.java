package com.qaengine.controllers;

import com.qaengine.exceptions.BadRequestException;
import com.qaengine.models.ApplicationUser;
import com.qaengine.models.DTO.ApplicationUserDTO;
import com.qaengine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
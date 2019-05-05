package com.qaengine.controllers;

import com.qaengine.database.UserRepository;
import com.qaengine.exceptions.BadRequestException;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.ApplicationUser;
import com.qaengine.models.inputs.ApplicationUserInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/user/sign-up")
    public ApplicationUser signUp(@Valid @RequestBody ApplicationUserInput userInput) {
        if (userRepository.findByUsername(userInput.getUsername()) != null) {
            throw new BadRequestException("ApplicationUser with given username already exists.");
        }
        ApplicationUser user = (ApplicationUser)HelperFunctions.copyProperties(new ApplicationUser(), userInput);
        user.setPassword(bCryptPasswordEncoder.encode(userInput.getPassword()));
        userRepository.save(user);
        return user;
    }
}
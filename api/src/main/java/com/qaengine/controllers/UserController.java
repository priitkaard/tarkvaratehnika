package com.qaengine.controllers;

import com.qaengine.database.UserRepository;
import com.qaengine.exceptions.BadRequestException;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.User;
import com.qaengine.models.inputs.UserInput;
import com.qaengine.models.outputs.UserOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/signUp")
    public UserOutput signUp(@RequestBody UserInput userInput) {
        if (userRepository.findByUsername(userInput.getUsername()) != null) {
            throw new BadRequestException("User with given username already exists.");
        }
        User user = (User)HelperFunctions.copyProperties(new User(), userInput);
        user.setPassword(bCryptPasswordEncoder.encode(userInput.getPassword()));
        userRepository.save(user);
        return (UserOutput)HelperFunctions.copyProperties(new UserOutput(), user);
    }
}
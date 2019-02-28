package com.qaengine.controllers;

import com.qaengine.database.UserRepository;
import com.qaengine.exceptions.BadRequestException;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.ApplicationUser;
import com.qaengine.models.inputs.ApplicationUserInput;
import com.qaengine.models.outputs.ApplicationUserOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/users/sign-up")
    public ApplicationUserOutput signUp(@RequestBody ApplicationUserInput userInput) {
        if (userRepository.findByUsername(userInput.getUsername()) != null) {
            throw new BadRequestException("ApplicationUser with given username already exists.");
        }
        ApplicationUser user = (ApplicationUser)HelperFunctions.copyProperties(new ApplicationUser(), userInput);
        user.setPassword(bCryptPasswordEncoder.encode(userInput.getPassword()));
        userRepository.save(user);
        return (ApplicationUserOutput)HelperFunctions.copyProperties(new ApplicationUserOutput(), user);
    }
}
package com.qaengine.controllers;

import com.qaengine.database.ApplicationUserRepository;
import com.qaengine.exceptions.BadRequestException;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.ApplicationUser;
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
public class ApplicationUserController {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/sign-up")
    public UserOutput signUp(@RequestBody UserInput userInput) {
        if (applicationUserRepository.findByUsername(userInput.getUsername()) != null) {
            throw new BadRequestException("ApplicationUser with given username already exists.");
        }
        ApplicationUser applicationUser = (ApplicationUser)HelperFunctions.copyProperties(new ApplicationUser(), userInput);
        applicationUser.setPassword(bCryptPasswordEncoder.encode(userInput.getPassword()));
        applicationUserRepository.save(applicationUser);
        return (UserOutput)HelperFunctions.copyProperties(new UserOutput(), applicationUser);
    }
}
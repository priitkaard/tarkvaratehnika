package com.qaengine.services;

import com.qaengine.database.UserRepository;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.ApplicationUser;
import com.qaengine.models.DTO.ApplicationUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Long getTotalUsers() {
        return userRepository.count();
    }

    public ApplicationUser getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<ApplicationUser> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public ApplicationUser createUser(ApplicationUserDTO userInput) {
        ApplicationUser user = (ApplicationUser) HelperFunctions.copyProperties(new ApplicationUser(), userInput);
        user.setPassword(bCryptPasswordEncoder.encode(userInput.getPassword()));
        userRepository.save(user);
        return user;
    }
}

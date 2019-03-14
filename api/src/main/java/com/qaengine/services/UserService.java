package com.qaengine.services;

import com.qaengine.database.UserRepository;
import com.qaengine.models.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ApplicationUser getUser(String username) {
        return userRepository.findByUsername(username);
    }

}

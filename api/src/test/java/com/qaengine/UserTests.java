package com.qaengine;

import com.qaengine.controllers.UserController;
import com.qaengine.database.UserRepository;
import com.qaengine.exceptions.BadRequestException;
import com.qaengine.models.DTO.ApplicationUserDTO;
import com.qaengine.services.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserController userController;
    //UserController TEST
    @Test(expected = BadRequestException.class)
    public void signUpWithSameNameTest()
    {
        userController.signUp(new ApplicationUserDTO("test", "test"));
        userController.signUp(new ApplicationUserDTO("test", "test"));
    }

}

package com.st.app;

import com.st.app.dao.UserService;
import com.st.app.dto.UserInfo;
import com.st.app.model.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AppAuthenticationTests {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(AppAuthenticationTests.class);
    @Test
    void contextLoads() {

    }


    @Test
    @Disabled
    void testAuthenticateUser() {
        // positive AuthenticateUser test
        String email = "AndreyAndreev@mail.com";
        String password = "qwe123";
        UserInfo userInfo = userService.authenticate(email,password);
        assertNotNull(userInfo.getUser());
        assertNull(userInfo.getMessage());

    }

    @Test
    @Disabled
    void testAuthenticateUserFail() {
        // negative test WrongPassCount
        UserInfo userInfo=new UserInfo();
        User testUser = new User();
        testUser.setEmail("AndreyAndreev@mail.com");
        testUser.setActive(true);
        testUser.setWrongPassCount(15);
        userService.validateAuthenticateUser(testUser,userInfo);
        assertNull(userInfo.getUser());
        assertNotNull(userInfo.getMessage());

    }
}

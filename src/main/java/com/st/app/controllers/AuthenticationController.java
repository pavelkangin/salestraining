package com.st.app.controllers;

import com.st.app.dao.MailService;
import com.st.app.dao.UserService;
import com.st.app.dto.*;
import com.st.app.model.User;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class AuthenticationController {


    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @PostMapping("/api/auth")
    public DefaultResponse authenticate(@RequestBody AuthInfo info, HttpSession session) {
        AuthResponse resp = new AuthResponse();
        logger.info("User authenticate started: " + info.getEmail());
        UserInfo userInfo = userService.authenticate(info.getEmail(), info.getPassword());

        if (userInfo.getUser() != null) {
            session.setAttribute("user", userInfo.getUser());
            userInfo.getUser().setWrongPassCount(0);
            userService.update(userInfo.getUser());
            logger.info("User authenticate completed: " + info.getEmail());
        } else {
            if (userInfo.getMessage() != null) {
                resp.setStatus(-2);
                resp.setMessage(userInfo.getMessage());
                logger.info("User authenticate failed: " + info.getEmail() + userInfo.getMessage());
            } else {
                resp.setStatus(-1);
                resp.setMessage("Wrong authentication. Check email or password.");
            }
        }
        return resp;
    }

    @PostMapping("/api/reset/request")
    public DefaultResponse requestCode(@RequestBody AuthInfo info) {
        //TODO generate request code and send email to the email from AuthInfo field only
        return null;
    }

    @PostMapping("/api/reset/apply")
    public DefaultResponse changePass(@RequestBody ChangePassInfo info) {
        //TODO update password by code
        return null;
    }

    @GetMapping("/api/logout")
    public DefaultResponse logout(HttpSession session) {
        //TODO terminate user session
        session.removeAttribute("user");
        return new DefaultResponse();
    }

    @PostMapping("/api/register")
    public User register(@RequestBody RegisterInfo info, HttpSession session) {
        //TODO register new user

        /*User user=userService.authenticate("","");
        user.setWrongPassCount(user.getWrongPassCount()+1);
        userService.update(user);*/

        //List<User> list=userService.findAll();
        // select * from (select * from aaa) where  aaa in (select * from bbb)
        //return list;
        return null;
    }

}

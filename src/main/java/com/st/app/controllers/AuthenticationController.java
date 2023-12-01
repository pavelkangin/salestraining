package com.st.app.controllers;

import com.st.app.dao.MailService;
import com.st.app.dao.RoleService;
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

    @Autowired
    private RoleService roleService;

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
    public DefaultResponse requestCode(@RequestBody AuthInfo info){
        //TODO generate request code and send email to the email from AuthInfo field only
        return null;
    }

    @PostMapping("/api/reset/apply")
    public DefaultResponse changePass(@RequestBody ChangePassInfo info){
        //TODO update password by code
        return null;
    }

    @GetMapping("/api/logout")
    public DefaultResponse logout(HttpSession session){
        //TODO terminate user session
        session.removeAttribute("user");
        return new DefaultResponse();
    }

    @PostMapping("/api/register")
    public DefaultResponse register(@RequestBody RegisterInfo info, HttpSession session){
        DefaultResponse response = new DefaultResponse();
        logger.info("User registration started: " + info.getName() + "  " + info.getEmail());
        User user = new User (info);
        user.setRole(roleService.getManager());
        String message = userService.validate(user);
        if (message ==null) {
            userService.create(user);
            //mailService.send(user.getEmail(), "Ваша регистрация прошла успешно!", "Активируйте аккаунт до"+ user.getExpiryDate());
            response.setStatus(200);
            response.setMessage("Registration Successfully Completed");
            logger.info("User registration successful: " + info.getName() + "  " + info.getEmail());
        }
        else {
            response.setStatus(400);
            response.setMessage(message);
            logger.info("User registration failed: " + info.getName() + "  " + info.getEmail());
        }
        return response;
    }

}

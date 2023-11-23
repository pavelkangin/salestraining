package com.st.app.controllers;

import com.st.app.dao.MailService;
import com.st.app.dao.RoleService;
import com.st.app.dao.UserService;
import com.st.app.dto.*;
import com.st.app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;

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
    public DefaultResponse authenticate(@RequestBody AuthInfo info, HttpSession session){
        AuthResponse resp=new AuthResponse();
        //TODO do user login


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
        //TODO register new user
        logger.info("User registration: " + info.getName() + "  " + info.getEmail());
        User user = new User();
        user.setEmail(info.getEmail());
        user.setName(info.getName());
        user.setPassword(info.getPassword());
        user.setActive(false);
        user.setExpiryDate(Date.valueOf(LocalDate.now().plusDays(1)));
        user.setRole(roleService.getManager());
        user.setWrongPassCount(0);
        userService.create(user);
        //mailService.send(user.getEmail(), "Ваша регистрация прошла успешно!", "Активируйте аккаунт до"+ user.getExpiryDate());
        DefaultResponse response = new DefaultResponse();
        response.setStatus(200);
        response.setMessage("Registration Successfully Completed");
        return response;
    }
}

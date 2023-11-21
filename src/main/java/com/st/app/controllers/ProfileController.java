package com.st.app.controllers;

import com.st.app.dao.ImageService;
import com.st.app.dao.MailService;
import com.st.app.dao.RoleService;
import com.st.app.dao.UserService;
import com.st.app.dto.AuthInfo;
import com.st.app.dto.DefaultResponse;
import com.st.app.dto.RegisterInfo;
import com.st.app.dto.SupportInfo;
import com.st.app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RestController
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private MailService mailService;

    @PostMapping("/api/profile/save")
    public DefaultResponse saveProfile(@RequestBody AuthInfo info,HttpSession session){
        //TODO update user profile

        return null;
    }

    @PostMapping("/api/profile/avatar/upload")
    public DefaultResponse uploadAvatar(MultipartFile image, HttpSession session){
        //TODO resize and save to the directory avatar image

        return null;
    }

    @PostMapping("/api/profile/remove")
    public DefaultResponse removeAccount(HttpSession session){
        //TODO resize and save to the directory avatar image

        return null;
    }


    @PostMapping("/api/support/send")
    public DefaultResponse sendSupport(@RequestBody SupportInfo info,MultipartFile[] file, HttpSession session){
        //TODO send email to support

        return null;
    }

}

package com.st.app.controllers;

import com.st.app.dao.ImageService;
import com.st.app.dao.MailService;
import com.st.app.dao.RoleService;
import com.st.app.dao.UserService;
import com.st.app.dto.AuthInfo;
import com.st.app.dto.DefaultResponse;
import com.st.app.dto.SupportInfo;
import com.st.app.model.Constants;
import com.st.app.model.User;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

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
    public DefaultResponse uploadAvatar(@RequestParam("image") MultipartFile image, HttpSession session) {
        //resize and save to the directory avatar image
        DefaultResponse resp = new DefaultResponse();
        logger.info("uploadAvatar started");
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.setStatus(-1);
            resp.setMessage("Пользователь не аутентифицирован!");
            logger.info("uploadAvatar. empty user in session, not authenticated");
            return resp;
        }
        if (userService.validateUserByEmail(user.getEmail()).getUser() == null) {
            resp.setStatus(-1);
            resp.setMessage("Пользователь не валидирован!");
            logger.info("uploadAvatar. user not validated by email");
            return resp;
        }

        try {
            File avatarFile = imageService.resize(image.getInputStream(), image.getContentType(), 512, 512);
            logger.info("Avatar file saved on server");
            user.setAvatarContentType(image.getContentType());
            user.setAvatarFile(avatarFile.getName());
            userService.update(user);
            resp.setStatus(0);
            resp.setMessage("Avatar успешно загружен.");

        } catch (Exception e) {
            logger.info("got exception in uploadAvatar");
            logger.error(e.getMessage());
        }
        return resp;
    }
    @GetMapping("/api/profile/avatar")
    public void getAvatar (HttpSession session, HttpServletResponse response) {
        //upload user avatar image into user profile
        logger.info("getAvatar started");
        File errorFile = new File(Constants.avatarPath + "/" + "mock_avatar.jpeg");
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.setStatus(404);
            logger.info("getAvatar. empty user in session, not authenticated");
            return;
        }

        try {
            if (user.getAvatarFile() == null) {
                logger.info("getAvatar. user property avatar File is null");
                sendResponse(response, 404, user.getAvatarContentType(), errorFile);
            } else {
                File avatarFile = new File(Constants.avatarPath + "/" + user.getAvatarFile());
                if (!avatarFile.exists()) {
                    logger.info("getAvatar. user avatar File doesn't exist on disk");
                    sendResponse(response, 404, user.getAvatarContentType(), errorFile);
                } else {
                    sendResponse(response, 200, user.getAvatarContentType(), avatarFile);
                }
            }
        } catch (Exception e) {
            logger.info("got exception in getAvatar");
            logger.error(e.getMessage());
            try {
                sendResponse(response, 404, user.getAvatarContentType(), errorFile);
            } catch (Exception ex) { }
        }
    }
    private void sendResponse(HttpServletResponse response, int responseStatus, String contentType, File responseFile) throws Exception {
        response.setStatus(responseStatus);
        response.setContentType(contentType);
        FileInputStream fis = new FileInputStream(responseFile);
        OutputStream oos = response.getOutputStream();
        IOUtils.copyLarge(fis, oos);
        fis.close();
        oos.flush();
        oos.close();
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

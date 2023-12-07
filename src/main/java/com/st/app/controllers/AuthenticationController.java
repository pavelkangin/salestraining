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
    public DefaultResponse authenticate(@RequestBody AuthInfo info, HttpSession session) {
        AuthResponse resp = new AuthResponse();
        logger.info("User authenticate started: " + info.getEmail());
        UserInfo userInfo = userService.authenticate(info.getEmail(), info.getPassword());

        if (userInfo.getUser() != null) {
            session.setAttribute("user", userInfo.getUser());
            userInfo.getUser().setWrongPassCount(0);
            userService.update(userInfo.getUser());
            logger.info("User authenticate completed, session opened: " + info.getEmail());
            resp.setName(userInfo.getUser().getName());
            resp.setEmail(userInfo.getUser().getEmail());
        } else {
            if (userInfo.getMessage() != null) {
                resp.setStatus(-2);
                resp.setMessage(userInfo.getMessage());
                logger.info("User authenticate failed: " + info.getEmail() + userInfo.getMessage());
            } else {
                resp.setStatus(-1);
                resp.setMessage("Wrong authentication. Check email or password.");
                logger.info("Wrong User authentication");
            }
        }
        return resp;
    }

    @PostMapping("/api/reset/request")
    public DefaultResponse requestCode(@RequestBody AuthInfo info){
        //generate request code and send email to the email from AuthInfo field only
        DefaultResponse resp = new DefaultResponse();
        String  emailSubject = "Восстановление Пароля";
        logger.info("user search by email and validate started" + info.getEmail());
        UserInfo userInfo = userService.validateUserByEmail(info.getEmail());

        if (userInfo.getUser() != null) {
            userInfo.getUser().setResetCode(userService.generateUniqueResetCode());
            userInfo.getUser().setExpiryDate(Date.valueOf(LocalDate.now().plusDays(1)));
            userService.update(userInfo.getUser());
            logger.info("user by email found and updated" + info.getEmail());

            String  emailText    = "<h2 style=\"color:DarkSlateBlue;\">Уважаемый " + userInfo.getUser().getName() +"," +
                   "</h2> <p style=\"color:DarkSlateBlue;\"> Вы получили это письмо с целью " +
                   "<b>восстановления пароля</b>.<br> Пройдите, пожалуйста, по ссылке " +
                   "<a href=\"http://st.iptp.net/api/apply/" + userInfo.getUser().getResetCode() +
                   "\">восстановить пароль</a>, чтобы изменить пароль.<br><br> Если Вы не инициировали смену пароля," +
                   " игнорируйте данное письмо. <br><br><br> <i>С уважением,</i><br> Команда \"IMPROV\" <br><br>  </p>";
            mailService.send(info.getEmail(), emailSubject, emailText);
            logger.info("email with link sent to user" + info.getEmail());

        } else {
            if (userInfo.getMessage() != null) {
                resp.setStatus(-2);
                resp.setMessage(userInfo.getMessage());
                logger.info("User  not found: " + info.getEmail() + userInfo.getMessage());

                String  emailText    = "Пользователь не найден!";
                mailService.send(info.getEmail(), emailSubject, emailText);
                logger.info("email with error msg sent to user " + info.getEmail());
            }
        }
        return resp;
    }

    @PostMapping("/api/reset/apply")
    public DefaultResponse changePass(@RequestBody ChangePassInfo info){
        // update password by code
        DefaultResponse resp = new DefaultResponse();
        String  emailSubject = "Изменение Пароля";
        logger.info("user search by reset_code and validate expiry_date started" + info.getCode());
        UserInfo userInfo = userService.validateUserByResetCode(info.getCode());

        if (userInfo.getUser() != null) {
            userInfo.getUser().setResetCode(null);
            userInfo.getUser().setExpiryDate(null);
            userInfo.getUser().setWrongPassCount(0);
            userInfo.getUser().setPassword(info.getPassword());
            userService.saveUserWithEncodePass(userInfo.getUser());
            logger.info("user by reset_code found and updated" + userInfo.getUser().getName());

            String  emailText    = "<h2 style=\"color:DarkSlateBlue;\">Уважаемый " + userInfo.getUser().getName() +"," +
                    "</h2> <p style=\"color:DarkSlateBlue;\"> Ваш пароль <b>изменен</b>. " +
                    "<br><br><br> <i>С уважением,</i><br> Команда \"IMPROV\" <br><br>  </p>";
            mailService.send(userInfo.getUser().getEmail(), emailSubject, emailText);
            logger.info("email with confirmation sent to user " + userInfo.getUser().getEmail());

        } else {
            if (userInfo.getMessage() != null) {
                logger.info("User by reset_code not found or invalidate by expiry_date: "
                        + info.getCode() + userInfo.getMessage());
                resp.setStatus(-2);
                resp.setMessage(userInfo.getMessage());
                String  emailText    = "Пользователь не найден!";
                mailService.send(userInfo.getUser().getEmail(), emailSubject, emailText);
                logger.info("email with error msg sent to user " + userInfo.getUser().getEmail());
            }
        }
        return resp;
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

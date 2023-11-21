package com.st.app.controllers;

import com.st.app.dao.AuthResponse;
import com.st.app.dao.UserService;
import com.st.app.dto.AuthInfo;
import com.st.app.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/auth")
    public AuthResponse authenticate(@RequestBody AuthInfo info, HttpSession session) {
        AuthResponse resp = new AuthResponse();
        UserInfo userInfo = userService.findUser2(info.getEmail(), info.getPassword());

        if (userInfo.getUser() != null)   {
            session.setAttribute("user", userInfo.getUser());
            userInfo.getUser().setWrongPasswordCounter(0);
            userService.update(userInfo.getUser());
        }
        else {
            if (userInfo.getMessage() !=null) {
                resp.setStatus("-2");
                resp.setMessage(userInfo.getMessage());
            }
            else {
                resp.setStatus("-1");
                resp.setMessage("Wrong authentification. Check email or password");
                ///
            }
        }
        return resp;

    }


}

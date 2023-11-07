package com.st.app.controllers;

import com.st.app.dto.AuthInfo;
import com.st.app.dto.AuthResponse;
import com.st.app.dto.DefaultResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class AuthenticationController {

    @PostMapping("/api/auth")
    public DefaultResponse authenticate(@RequestBody AuthInfo info, HttpSession session){
        AuthResponse resp=new AuthResponse();


        return resp;
    }

    public DefaultResponse logout(HttpSession session){
        session.removeAttribute("user");
        return new DefaultResponse();
    }
}

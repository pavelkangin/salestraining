package com.st.app.controllers;

import com.st.app.dao.UserDAO;
import com.st.app.dto.AuthInfo;
import com.st.app.dto.AuthResponse;
import com.st.app.dto.DefaultResponse;
import com.st.app.model.Greeting;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class AuthenticationController {

    @PostMapping("/api/auth")
    public DefaultResponse authenticate(@RequestBody AuthInfo info, HttpSession session){
        AuthResponse resp=new AuthResponse();
        User user=UserDAO.findUser(info);
        if (user!=null){
            session.setAttribute("user",user);
        }
        else {
            resp.setStatus(-1);
            resp.setMessage("Wrong email or password!");
        }
        return resp;
    }

    public DefaultResponse logout(HttpSession session){
        session.removeAttribute("user");
        return new DefaultResponse();
    }
}

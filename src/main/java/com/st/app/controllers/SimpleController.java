package com.st.app.controllers;

import com.st.app.dao.GreetingDAO;
import com.st.app.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SimpleController {

    @GetMapping("/hello")
    public Greeting helloWorld(){
        //
        return new Greeting("Ivan","Hello!");
    }

    @PostMapping("/auth")
    public Greeting authenticate(HttpSession session){
        session.setAttribute("user","ivan");
        session.setAttribute("user",null);
        session.setAttribute("reportFile","/home/report.pdf");
        session.removeAttribute("user");
        return new Greeting("Ivan","Hello!");
    }

    @GetMapping
    public List<Greeting> getLessons(String searchText, HttpSession session){

        if (session.getAttribute("user")!=null){
            // do serch
        }
        else {
            // return error
        }
        /*

        GreetingValidate.check(searchText)























         */
        return GreetingDAO.getGreetings();
    }
}

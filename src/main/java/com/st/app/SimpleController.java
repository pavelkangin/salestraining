package com.st.app;

import com.st.app.dao.GreetingDAO;
import com.st.app.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Greeting authenticate(){

        return new Greeting("Ivan","Hello!");
    }

    @GetMapping
    public List<Greeting> getLessons(String searchText){
        /*

        GreetingValidate.check(searchText)























         */
        return GreetingDAO.getGreetings();
    }
    @PostMapping("/register")
    public Greeting registration(){

        return new Greeting("Ivan","Hello!");
    }
}

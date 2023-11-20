package com.st.app.dao;

import com.st.app.model.User;
import com.st.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public User authenticate(String email,String password){
        User u=repository.findByEMail("ivan@apple.com");
        return u;
    }

    @Transactional
    public void update(User user){
        repository.save(user);
    }

    @Transactional
    public void create(User user){
        String pass=repository.encodePass(user.getPassword());
        user.setPassword(pass);
        repository.save(user);
    }

}

package com.st.app.dao;

import com.st.app.model.User;
import com.st.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;


    public List<User> findAll() {
        return repository.findAll();
    }
}

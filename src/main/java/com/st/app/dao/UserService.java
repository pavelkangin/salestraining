package com.st.app.dao;

import com.st.app.dto.UserInfo;
import com.st.app.model.User;
import com.st.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findUser (String email, String pwd) {
        List<User> users = userRepository.findAll();
        for (User user: users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(pwd)) {
               return user;
            }
        }
        return null;
    }
    public UserInfo findUser2 (String email, String pwd){
        UserInfo userInfo = new UserInfo();
        User user = userRepository.findByEmail(email, pwd);
        if (user !=null) {
            if (user.getWrongPasswordCounter()>10) {
                userInfo.setMessage("Too many login attempts. Your Accaunt is blocked.");
            }
            else {
                userInfo.setUser(user);
            }
        }
        return userInfo;
    }

    public void update(User user) {
        userRepository.save(user);
    }

}

package com.st.app.dao;

import com.st.app.model.User;
import com.st.app.repository.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
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
    public String validate (User user) {
        String message = null;
        if (Strings.isBlank(user.getName())){
            message = "Имя пользователя не может быть пустым";
        } else if (Strings.isBlank(user.getPassword())){
            message = "Пароль не может быть пустым";
        } else if (Strings.isBlank(user.getEmail())){
            message = "Email не может быть пустым";
        } else if (!Pattern.compile(EMAIL_PATTERN)
                .matcher(user.getEmail())
                .matches()) {
                message = "Адрес почты задан в неверном формате";
            }
            else if (repository.findByEMail(user.getEmail()) != null) {
                message = "Пользователь с таким адресом уже существует";
            }
            return message;
        }
    }


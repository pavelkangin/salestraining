package com.st.app.dao;

import com.st.app.dto.UserInfo;
import com.st.app.model.User;
import com.st.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public UserInfo authenticate(String email,String password) {
        UserInfo userInfo; // = new UserInfo();
        User user = repository.findByEMail(email);

        userInfo = validateAuthenticateUser(user);

        if (userInfo.getMessage() !=null) {
            return userInfo;
        }

        User user2 = repository.findByEMailAndPassword(email, password);

        if (user2 == null) {
            logger.info(" 5. user&pass == null, update WrongPassCount");
            user.setWrongPassCount(user.getWrongPassCount() + 1);
            update(user);
            userInfo.setMessage("Неправильно введен email или пароль!");
            return userInfo;
        }

        logger.info("6. positive case, user found ");
        userInfo.setUser(user);
        return userInfo;
    }

    public UserInfo validateAuthenticateUser (User user) {
        UserInfo userInfo = new UserInfo();

        if (user.getEmail() == null) {
            logger.info(" 1. user == null ");
            userInfo.setMessage("Пользователь не найден!");
            return userInfo;
        }

        if (!user.isActive()) {
            if (user.getExpiryDate().before(Date.valueOf(LocalDate.now()))) {
                logger.info(" 2. user !active, ExpiryDate - expired ");
                userInfo.setMessage("Дата активации аккаунта - истекла!");
                return userInfo;
            }
        }
        else {
            if (user.getWrongPassCount() > 10) {
                logger.info(" 3. user active, WrongPassCount > 10 ");
                userInfo.setMessage("Превышено количество попыток входа. Аккаунт заблокирован. Нажмите кнопку восстановить.");
                return userInfo;
            }
            logger.info(" 4. OK - user checked ");
        }
        return userInfo;
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

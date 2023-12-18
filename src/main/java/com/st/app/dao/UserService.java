package com.st.app.dao;

import com.st.app.dto.TokenValidationRequest;
import com.st.app.dto.TokenValidationResponse;
import com.st.app.dto.UserInfo;
import com.st.app.exception.BadRequestException;
import com.st.app.model.User;
import com.st.app.repository.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Environment environment;
    private static final String EMAIL_PATTERN = "([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)";
    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public UserInfo authenticate(String email,String password) {
        UserInfo userInfo = new UserInfo();
        User user = repository.findByEMail(email);

        if (user==null) {
            userInfo.setMessage("Неправильно введен email или пароль!");
            return userInfo;
        }
        validateAuthenticateUser(user,userInfo);

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

    public void validateAuthenticateUser (User user,UserInfo userInfo) {
        if (user.getEmail() == null) {
            logger.info(" 1. user == null ");
            userInfo.setMessage("Пользователь не найден!");
            return ;
        }

        if (!user.isActive()) {
            if (user.getExpiryDate().before(Date.valueOf(LocalDate.now()))) {
                logger.info(" 2. user !active, ExpiryDate - expired ");
                userInfo.setMessage("Дата активации аккаунта - истекла!");
                return ;
            }
        }
        else {
            if (user.getWrongPassCount() > 10) {
                logger.info(" 3. user active, WrongPassCount > 10 ");
                userInfo.setMessage("Превышено количество попыток входа. Аккаунт заблокирован. Нажмите кнопку восстановить.");
                return ;
            }
            logger.info(" 4. OK - user checked ");
        }
        return;
    }
    public UserInfo validateUserByEmail (String email) {
        UserInfo userInfo = new UserInfo();
        User user = repository.findByEMail(email);

        if ((user == null) || (!user.isActive()) ) {
            logger.info(" user not found or user notActive ");
            userInfo.setMessage("Пользователь не зарегистрирован!");
            return userInfo;
        }
        userInfo.setUser(user);
        return userInfo;
    }
    public UserInfo validateUserByResetCode (String reset_code) {
        UserInfo userInfo = new UserInfo();
        User user = repository.findByResetCode(reset_code);

        if (( user == null ) || (user.getExpiryDate().before(Date.valueOf(LocalDate.now())))) {
            logger.info(" user not found or expiry_date expired ");
            userInfo.setMessage("Невозможно изменить пароль, попробуйте запросить его заново!");
            return userInfo;
        }
        userInfo.setUser(user);
        return userInfo;
    }
    public String generateUniqueResetCode () {
        String upperCaseAlphabetAndNumbers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder newCode = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            char c = upperCaseAlphabetAndNumbers.charAt(random.nextInt(upperCaseAlphabetAndNumbers.length()));
            if (i > 0) {
                char lastChar;
                do { c = upperCaseAlphabetAndNumbers.charAt(random.nextInt(upperCaseAlphabetAndNumbers.length()));
                    lastChar = newCode.charAt(newCode.length() - 1);
                } while (c == lastChar);
            }
            newCode.append(c);
        }
        return newCode.toString();
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
    @Transactional
    public void saveUserWithEncodePass(User user){
        String pass=repository.encodePass(user.getPassword());
        user.setPassword(pass);
        repository.save(user);
    }
    public void validateRegisterUser(User user) {
        if (Strings.isBlank(user.getName())){
            logger.info("Имя пользователя не может быть пустым");
            throw new BadRequestException(-1,"Имя пользователя не может быть пустым");
        } else if (Strings.isBlank(user.getPassword())){
            logger.info("Пароль не может быть пустым");
            throw new BadRequestException(-1,"Пароль не может быть пустым");
        } else if (Strings.isBlank(user.getEmail())){
            logger.info("Email не может быть пустым");
            throw new BadRequestException(-1,"Email не может быть пустым");
        } else if (!Pattern.compile(EMAIL_PATTERN)
                .matcher(user.getEmail())
                .matches()) {
            logger.info("Адрес почты задан в неверном формате");
            throw new BadRequestException(-1,"Адрес почты задан в неверном формате");
            }
            else if (repository.findByEMail(user.getEmail()) != null) {
            logger.info("Пользователь с таким адресом уже существует");
            throw new BadRequestException(-1,"Пользователь с таким адресом уже существует");
            }
            logger.info(" 2. user !active, ExpiryDate - expired ");
        }

    public void validateToken(String token) {
        TokenValidationRequest request = new TokenValidationRequest();
        request.setSecret(environment.getProperty("google.recaptcha.secret"));
        request.setResponse(token);
        try {
            TokenValidationResponse response =
                    restTemplate.postForObject(Objects.requireNonNull(environment.getProperty("google.recaptcha.url")), request, TokenValidationResponse.class);
            if (response == null || !response.isSuccess()) {
                logger.info("Неверный токен "+ (response==null ?"ответ пустой":Arrays.toString(response.getError_codes())));
                throw new BadRequestException(-1,"Неверный токен!");
            }
        } catch (HttpStatusCodeException e) {
                logger.info("Возникло исключение во время проверки токена "+e.getResponseBodyAsString());
                throw new BadRequestException(-1,"Не удалось проверить токен: " + e.getResponseBodyAsString());
        }
        logger.info("Проверка токена прошла успешно");
    }
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}

package com.st.app;

import com.st.app.dao.RoleService;
import com.st.app.dao.UserService;
import com.st.app.dto.RegisterInfo;
import com.st.app.dto.TokenValidationRequest;
import com.st.app.dto.TokenValidationResponse;
import com.st.app.exception.BadRequestException;
import com.st.app.model.Role;
import com.st.app.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AppRegistrationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @MockBean
    private RestTemplate restTemplate;
    @Test
    void invalidRegisterInfoName () {
        RegisterInfo info = new RegisterInfo();
        info.setEmail("AndreyAndreev@mail.com");
        info.setPassword("qwe123");
        User user = new User(info);
        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.validateRegisterUser(user));
        assertNotNull(exception.getMessage());
        assertEquals("Имя пользователя не может быть пустым", exception.getMessage());
    }
    @Test
    void invalidRegisterInfoPassword () {
        RegisterInfo info = new RegisterInfo();
        info.setEmail("AndreyAndreev@mail.com");
        info.setName("Andrey");
        User user = new User(info);
        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.validateRegisterUser(user));
        assertNotNull(exception.getMessage());
        assertEquals("Пароль не может быть пустым", exception.getMessage());
    }
    @Test
    void invalidRegisterInfoEmail () {
        RegisterInfo info = new RegisterInfo();
        info.setPassword("Password");
        info.setName("Andrey");
        User user1 = new User(info);
        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.validateRegisterUser(user1));
        assertNotNull(exception.getMessage());
        assertEquals("Email не может быть пустым", exception.getMessage());

        info.setEmail("AndreyAndreev@mail");
        User user2 = new User(info);
        exception = assertThrows(BadRequestException.class, () -> userService.validateRegisterUser(user2));
        assertNotNull(exception.getMessage());
        assertEquals("Адрес почты задан в неверном формате", exception.getMessage());
    }
    @Test
    void registerExistingUser () {
        User newUser=new User();
        newUser.setEmail("AndreyAndreev@mail.com");
        newUser.setName("Andrey");
        newUser.setPassword("qwe123");
        newUser.setWrongPassCount(0);
        newUser.setRole(roleService.getManager());
        userService.create(newUser);
        assertNotNull(newUser.getId());

        RegisterInfo info = new RegisterInfo();
        info.setEmail("AndreyAndreev@mail.com");
        info.setPassword("qwe123");
        info.setName("Andrey");
        User user = new User(info);
        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.validateRegisterUser(user));
        assertNotNull(exception.getMessage());
        assertEquals("Пользователь с таким адресом уже существует", exception.getMessage());

        userService.delete(newUser.getId());
    }
    @Test
    void invalidToken () {
        RegisterInfo info = new RegisterInfo();
        info.setEmail("AndreyAndreev@mail.com");
        info.setPassword("qwe123");
        info.setName("Andrey");
        info.setToken("token");
        Role manager = roleService.getManager();
        User user = new User(info);
        user.setRole(manager);
        assertDoesNotThrow(() ->userService.validateRegisterUser(user));
        when(restTemplate.postForObject(anyString(),any(TokenValidationRequest.class),eq(TokenValidationResponse.class))).thenReturn(null);
        BadRequestException exception = assertThrows(BadRequestException.class, () ->userService.validateToken(info.getToken()));
        assertNotNull(exception.getMessage());
        assertEquals("Неверный токен!", exception.getMessage());
    }
    @Test
    void registerSuccessful () {
        RegisterInfo info = new RegisterInfo();
        info.setEmail("AndreyAndreev@mail.com");
        info.setPassword("qwe123");
        info.setName("Andrey");
        info.setToken("token");
        Role manager = roleService.getManager();
        User user = new User(info);
        user.setRole(manager);
        assertDoesNotThrow(() ->userService.validateRegisterUser(user));
        TokenValidationResponse response = new TokenValidationResponse();
        response.setSuccess(true);
        when(restTemplate.postForObject(anyString(),any(TokenValidationRequest.class),eq(TokenValidationResponse.class))).thenReturn(response);
        assertDoesNotThrow(() ->userService.validateToken(info.getToken()));
        userService.create(user);
        assertNotNull(user.getId());
        assertFalse(user.isActive());
        assertEquals(user.getRole(), manager);
        assertEquals(user.getExpiryDate(), Date.valueOf(LocalDate.now().plusDays(1)));
        userService.delete(user.getId());
    }
}

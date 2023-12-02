package com.st.app;

import com.st.app.dao.RoleService;
import com.st.app.dao.UserService;
import com.st.app.dto.RegisterInfo;
import com.st.app.model.Role;
import com.st.app.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AppRegistrationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Test
    void invalidRegisterInfoName () {
        RegisterInfo info = new RegisterInfo();
        info.setEmail("AndreyAndreev@mail.com");
        info.setPassword("qwe123");
        User user = new User(info);
        String message = userService.validate(user);
        assertNotNull(message);
        assertEquals("Имя пользователя не может быть пустым", message);
    }
    @Test
    void invalidRegisterInfoPassword () {
        RegisterInfo info = new RegisterInfo();
        info.setEmail("AndreyAndreev@mail.com");
        info.setName("Andrey");
        User user = new User(info);
        String message = userService.validate(user);
        assertNotNull(message);
        assertEquals("Пароль не может быть пустым", message);
    }
    @Test
    void invalidRegisterInfoEmail () {
        RegisterInfo info = new RegisterInfo();
        info.setPassword("Password");
        info.setName("Andrey");
        User user = new User(info);
        String message = userService.validate(user);
        assertNotNull(message);
        assertEquals("Email не может быть пустым", message);

        info.setEmail("AndreyAndreev@mail");
        user = new User(info);
        message = userService.validate(user);
        assertNotNull(message);
        assertEquals("Адрес почты задан в неверном формате", message);
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
        String message = userService.validate(user);
        assertNotNull(message);
        assertEquals("Пользователь с таким адресом уже существует", message);

        userService.delete(newUser.getId());
    }
    @Test
    void registerSuccessful () {
        RegisterInfo info = new RegisterInfo();
        info.setEmail("AndreyAndreev@mail.com");
        info.setPassword("qwe123");
        info.setName("Andrey");
        Role manager = roleService.getManager();
        User user = new User(info);
        user.setRole(manager);
        String message = userService.validate(user);
        assertNull(message);
        userService.create(user);
        assertNotNull(user.getId());
        assertFalse(user.isActive());
        assertEquals(user.getRole(), manager);
        assertEquals(user.getExpiryDate(), Date.valueOf(LocalDate.now().plusDays(1)));
        userService.delete(user.getId());
    }


}

package com.st.app.model.mapper;

import com.st.app.dto.RegisterInfo;
import com.st.app.model.User;

import java.sql.Date;
import java.time.LocalDate;

public class UserMapper {
    private UserMapper() {
    }
    public static User toUser(final RegisterInfo info) {
        User user = new User();
        user.setEmail(info.getEmail());
        user.setName(info.getName());
        user.setPassword(info.getPassword());
        user.setActive(false);
        user.setExpiryDate(Date.valueOf(LocalDate.now().plusDays(1)));
        user.setWrongPassCount(0);
        return user;
    }
}

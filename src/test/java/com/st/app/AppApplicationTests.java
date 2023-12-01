package com.st.app;

import com.st.app.dao.RoleService;
import com.st.app.dao.UserService;
import com.st.app.model.User;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class
AppApplicationTests {


	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	Logger logger = LoggerFactory.getLogger(AppApplicationTests.class);


	@Test
	void contextLoads() {

	}

	@Test
	@Disabled
	void testCreateUser(){
		User newUser=new User();
		newUser.setEmail("aaa@bbb.com");
		newUser.setName("aaa");
		newUser.setPassword("abc123");
		newUser.setWrongPassCount(0);
		userService.create(newUser);
		assertNotNull(newUser.getId());

	}

}

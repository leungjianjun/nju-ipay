package com.ipay.server.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ipay.server.entity.Authority;
import com.ipay.server.entity.User;
import com.ipay.server.service.IUserService;
import com.ipay.server.util.SpringJUnit45ClassRunner;

@RunWith(SpringJUnit45ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/root-context.xml","classpath:spring/appServlet/servlet-context.xml"})
public class UserServiceTest {
	
	private IUserService<User> userService;

	public IUserService<User> getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(IUserService<User> userService) {
		this.userService = userService;
	}
	
	@Test
	public void testCreate(){
		Authority authority1 = new Authority();
		authority1.setName("client");
		userService.createAuthority(authority1);
		
		Authority authority2 = new Authority();
		authority2.setName("market");
		userService.createAuthority(authority2);
		
		Authority authority3 = new Authority();
		authority3.setName("admin");
		userService.createAuthority(authority3);
		
	}

}

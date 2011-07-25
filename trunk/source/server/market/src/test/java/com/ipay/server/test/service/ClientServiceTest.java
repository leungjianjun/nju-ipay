package com.ipay.server.test.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import com.ipay.server.entity.Client;
import com.ipay.server.entity.User;
import com.ipay.server.service.IClientService;
import com.ipay.server.service.IUserService;
import com.ipay.server.util.SpringJUnit45ClassRunner;


@RunWith(SpringJUnit45ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/root-context.xml","classpath:spring/appServlet/servlet-context.xml"})
public class ClientServiceTest {
	
	private IClientService<Client> clientService;
	
	private IUserService<User> userService;

	public IUserService<User> getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(IUserService<User> userService) {
		this.userService = userService;
	}

	public IClientService<Client> getClientService() {
		return clientService;
	}

	@Autowired
	public void setClientService(IClientService<Client> clientService) {
		this.clientService = clientService;
	}
	
	@Test
	public void testCreate(){
		Client client = new Client();
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		client.setAccount("ljj");
		client.setCardnum("638409233433434");
		client.setPassword(md5.encodePassword("123456", client.getAccount()));
		client.getAuthorityList().add(userService.getAuthority("client"));
		client.setPaypass(md5.encodePassword("payapss", client.getAccount()));
		client.getClientInfo().setCreateDate(new Date());//这里不要都是一个时间的
		client.getClientInfo().setPhonenum("1599623223");
		client.getClientInfo().setRealname("杨建军");
		clientService.create(client);
	}

}

package com.ipay.server.test.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ipay.server.entity.CreditCard;
import com.ipay.server.entity.User;
import com.ipay.server.security.PasswordEncoder;
import com.ipay.server.service.IUserService;
import com.ipay.server.test.util.SpringJUnit45ClassRunner;

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
	public void testCreateUser(){
		User user = new User();
		user.setAccount("ljjx");//
		user.setAddress("南京大学鼓楼校区");
		user.setBirthday(new Date());
		user.setCreateDate(new Date());
		user.setEnbale(true);
		user.setIdnumber("23423748327948238u43");
		user.setPassword(PasswordEncoder.encode("123456", user.getAccount()));
		user.setPhonenum("15996292422");
		user.setRealname("杨建军");
		user.setRemark("没有备注");
		user.setSex(true);

		CreditCard creditCard = new CreditCard();
		creditCard.setBalance(1570.5);
		creditCard.setCardnum("837941h43bh32h");
		creditCard.setEnable(true);
		creditCard.setPaypass(PasswordEncoder.encode("paypass", user.getAccount()));
		creditCard.setPrivateKey(privateKey)
		user.setCreditCard(creditCard);
	}

}














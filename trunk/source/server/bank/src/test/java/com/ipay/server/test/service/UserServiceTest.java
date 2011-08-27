package com.ipay.server.test.service;

import java.security.KeyPair;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ipay.server.entity.CreditCard;
import com.ipay.server.entity.User;
import com.ipay.server.security.KeyManager;
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
		KeyPair keyPair = KeyManager.generatorKeypair();
		creditCard.setPrivateKey(KeyManager.encryptPrivateKey(keyPair.getPrivate(), "paypass", creditCard.getCardnum()));
		System.out.println(keyPair.getPublic().getEncoded().length);
		creditCard.setPublicKey(keyPair.getPublic().getEncoded());
		creditCard.setUser(user);
		user.setCreditCard(creditCard);
		
		userService.create(user);
		
		User user1 = new User();
		user1.setAccount("ljjmarket");//
		user1.setAddress("南京大学鼓楼校区");
		user1.setBirthday(new Date());
		user1.setCreateDate(new Date());
		user1.setEnbale(true);
		user1.setIdnumber("23423748327948238u45");
		user1.setPassword(PasswordEncoder.encode("123456", user1.getAccount()));
		user1.setPhonenum("15996294422");
		user1.setRealname("杨建军");
		user1.setRemark("没有备注");
		user1.setSex(true);

		CreditCard creditCard1 = new CreditCard();
		creditCard1.setBalance(10570.5);
		creditCard1.setCardnum("837941h43bh32i");
		creditCard1.setEnable(true);
		creditCard1.setPaypass(PasswordEncoder.encode("paypass2", user1.getAccount()));
		KeyPair keyPair1 = KeyManager.generatorKeypair();
		creditCard1.setPrivateKey(KeyManager.encryptPrivateKey(keyPair1.getPrivate(), "paypass2", creditCard1.getCardnum()));
		creditCard1.setPublicKey(keyPair1.getPublic().getEncoded());
		creditCard1.setUser(user1);
		user1.setCreditCard(creditCard1);
		
		userService.create(user1);
	}

}














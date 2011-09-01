package com.ipay.server.test.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.util.Date;

import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.ipay.server.entity.*;
import com.ipay.server.security.KeyManager;
import com.ipay.server.security.PasswordEncoder;
import com.ipay.server.service.*;

/**
 * 这个类用于初始化数据库的数据，使用前请先查看entity包里的每个类的每个field的定义，
 * 然后使用service的create方法把数据保存到数据库中
 * @author ljj
 *
 */
public class InitDatabase {
	
	/**
	 * 要用到的service实例
	 */
	private ICreditCardService<CreditCard> creditCardService;
	private ITransactionService<Transaction> transactionService;
	private IUserService<User> userService;
	
	@SuppressWarnings("unchecked")
	public InitDatabase(){
		String[] contexts = new String[] {"classpath:spring/root-context.xml","classpath:spring/appServlet/servlet-context.xml"};  
		XmlWebApplicationContext context = new XmlWebApplicationContext();  
        context.setConfigLocations(contexts);
        MockServletContext msc = new MockServletContext();  
        context.setServletContext(msc); 
        context.refresh(); 
        msc.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);  
        userService = (IUserService<User>) context.getBean("userService");
        creditCardService = (ICreditCardService<CreditCard>) context.getBean("creditCardService");
        transactionService = (ITransactionService<Transaction>) context.getBean("transactionService");
	}
	
	/**
	 * 初始化所有的表单
	 * @throws IOException
	 */
	public void init() throws IOException{
		initUser();
	}

	/**
	 * 初始化权限
	 * @throws IOException 
	 */
	private void initUser() throws IOException {
		
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("O:\\Workspace\\Eclipse\\iPay_Download\\name.txt")));
		String [] nameset=new String[120];String str="";String data=null;
		while((data=br.readLine())!=null)
		{
			str+=data;
		}
		nameset=str.split(" ");
		
		for(int i=0;i<30;i++)
		{
			User user=new User();
			user.setAccount("622848 208149873"+(4000+i));
			user.setAddress("南京市鼓楼区汉口路"+i+"号");
			user.setBirthday(new Date());
			user.setCreateDate(new Date());
			user.setEnbale(true);
			user.setIdnumber("23423748327948238u"+(10+i));
			user.setPassword(PasswordEncoder.encode("123456", user.getAccount()));
			user.setPhonenum("15996294"+i/10+"67"+i%10);
			user.setRealname(nameset[i]);
			user.setRemark("没有备注");
			user.setSex(true);
			
			CreditCard creditCard = new CreditCard();
			creditCard.setBalance(1570.5);
			creditCard.setCardnum("622848 208149873"+(4000+i));
			creditCard.setEnable(true);
			creditCard.setPaypass(PasswordEncoder.encode("paypass"+i, user.getAccount()));
			String rawpass="paypass"+i;
			KeyPair keyPair = KeyManager.generatorKeypair();
			creditCard.setPrivateKey(KeyManager.encryptPrivateKey(keyPair.getPrivate(),"paypass"+i, creditCard.getCardnum()));
			System.out.println(keyPair.getPublic().getEncoded().length);
			creditCard.setPublicKey(keyPair.getPublic().getEncoded());
			creditCard.setUser(user);
			user.setCreditCard(creditCard);
			
			userService.create(user);
		}
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		InitDatabase initDatabase = new InitDatabase();
		
		initDatabase.init();
	}
}

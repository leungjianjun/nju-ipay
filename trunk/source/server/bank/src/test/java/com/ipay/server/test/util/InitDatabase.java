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
		
		//终端用户
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
			creditCard.setCreateDate(new Date());
			creditCard.setPaypass(PasswordEncoder.encode("paypass"+i, user.getAccount()));
			
			KeyPair keyPair = KeyManager.generatorKeypair();
			creditCard.setPrivateKey(KeyManager.encryptPrivateKey(keyPair.getPrivate(),"paypass"+i, creditCard.getCardnum()));
			System.out.println(keyPair.getPublic().getEncoded().length);
			creditCard.setPublicKey(keyPair.getPublic().getEncoded());
			creditCard.setUser(user);
			user.setCreditCard(creditCard);
			
			userService.create(user);
		}
		
		//商场用户
		User user_market1=new User();
		user_market1.setAccount("market_1");
		user_market1.setAddress("南京市栖霞区亚东新区");
		user_market1.setBirthday(new Date());
		user_market1.setCreateDate(new Date());
		user_market1.setEnbale(true);
		user_market1.setIdnumber("market_1");
		user_market1.setPassword(PasswordEncoder.encode("123456", user_market1.getAccount()));
		user_market1.setPhonenum("025-84860400");
		user_market1.setRealname("苏果超市仙林店");
		user_market1.setRemark("苏果超市在仙林地区的分店，便于学生购物");
		user_market1.setSex(true);
		
		CreditCard creditCard_market1 = new CreditCard();
		creditCard_market1.setBalance(1570.5);
		creditCard_market1.setCreateDate(new Date());
		creditCard_market1.setCardnum("TM2011001");
		creditCard_market1.setEnable(true);
		creditCard_market1.setPaypass(PasswordEncoder.encode("market_1", user_market1.getAccount()));
		
		KeyPair keyPair = KeyManager.generatorKeypair();
		creditCard_market1.setPrivateKey(KeyManager.encryptPrivateKey(keyPair.getPrivate(),"market_1", creditCard_market1.getCardnum()));
		System.out.println(keyPair.getPublic().getEncoded().length);
		creditCard_market1.setPublicKey(keyPair.getPublic().getEncoded());
		creditCard_market1.setUser(user_market1);
		user_market1.setCreditCard(creditCard_market1);
		
		userService.create(user_market1);
		
		User user_market2=new User();
		user_market2.setAccount("market_2");
		user_market2.setAddress("江苏省南京市玄武区丹凤街39号");
		user_market2.setBirthday(new Date());
		user_market2.setCreateDate(new Date());
		user_market2.setEnbale(true);
		user_market2.setIdnumber("market_2");
		user_market2.setPassword(PasswordEncoder.encode("123456", user_market2.getAccount()));
		user_market2.setPhonenum("025-84869282");
		user_market2.setRealname("金润发鼓楼店");
		user_market2.setRemark("南京金润发鼓楼分店，价格比较便宜，规模一般");
		user_market2.setSex(true);
		
		CreditCard creditCard_market2 = new CreditCard();
		creditCard_market2.setBalance(1570.5);
		creditCard_market2.setCardnum("TM2011002");
		creditCard_market1.setCreateDate(new Date());
		creditCard_market2.setEnable(true);
		creditCard_market2.setPaypass(PasswordEncoder.encode("market_2", user_market2.getAccount()));
		
		KeyPair keyPair2 = KeyManager.generatorKeypair();
		creditCard_market2.setPrivateKey(KeyManager.encryptPrivateKey(keyPair2.getPrivate(),"market_2", creditCard_market2.getCardnum()));
		System.out.println(keyPair2.getPublic().getEncoded().length);
		creditCard_market2.setPublicKey(keyPair2.getPublic().getEncoded());
		creditCard_market2.setUser(user_market2);
		user_market2.setCreditCard(creditCard_market2);
		
		userService.create(user_market2);
		
		User user_market3=new User();
		user_market3.setAccount("market_3");
		user_market3.setAddress("南京市白下区洪武路88号万达购物广场2-3楼");
		user_market3.setBirthday(new Date());
		user_market3.setCreateDate(new Date());
		user_market3.setEnbale(true);
		user_market3.setIdnumber("market_3");
		user_market3.setPassword(PasswordEncoder.encode("123456", user_market3.getAccount()));
		user_market3.setPhonenum("025-84782888");
		user_market3.setRealname("南京沃尔玛新街口店");
		user_market3.setRemark("南京沃尔玛新街口店，规模较大，市中心繁华区，品种齐全");
		user_market3.setSex(true);
		
		CreditCard creditCard_market3 = new CreditCard();
		creditCard_market3.setBalance(1570.5);
		creditCard_market3.setCardnum("TM2011003");
		creditCard_market1.setCreateDate(new Date());
		creditCard_market3.setEnable(true);
		creditCard_market3.setPaypass(PasswordEncoder.encode("market_3", user_market3.getAccount()));
		
		KeyPair keyPair3 = KeyManager.generatorKeypair();
		creditCard_market3.setPrivateKey(KeyManager.encryptPrivateKey(keyPair3.getPrivate(),"market_3", creditCard_market3.getCardnum()));
		System.out.println(keyPair3.getPublic().getEncoded().length);
		creditCard_market3.setPublicKey(keyPair3.getPublic().getEncoded());
		creditCard_market3.setUser(user_market3);
		user_market3.setCreditCard(creditCard_market3);
		
		userService.create(user_market3);
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

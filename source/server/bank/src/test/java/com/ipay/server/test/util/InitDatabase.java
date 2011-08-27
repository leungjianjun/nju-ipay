package com.ipay.server.test.util;

import java.io.IOException;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.ipay.server.entity.*;
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
	 */
	private void initUser() {
		
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

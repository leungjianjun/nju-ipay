package com.ipay.server.util;

import java.util.Date;

import org.springframework.mock.web.MockServletContext;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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
	private IClientService<Client> clientService;
	private IMarketService<Market> marketService;
	private IRecordService<Record> recordService;
	private IUserService<User> userService;
	private IProductService<Product> productService;
	
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
        clientService = (IClientService<Client>) context.getBean("clientService");
        marketService = (IMarketService<Market>) context.getBean("marketService");
        recordService = (IRecordService<Record>) context.getBean("recordService");
        productService = (IProductService<Product>) context.getBean("productService");
	}
	
	/**
	 * 初始化所有的表单
	 */
	public void init(){
		initUser();
		initProduct();
		initClient();
	}

	/**
	 * 初始化权限
	 */
	private void initUser() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InitDatabase initDatabase = new InitDatabase();
		initDatabase.init();
	}
	
	/**
	 * 初始化与产品有段的表单
	 */
	private void initProduct(){
		
	}
	
	/**
	 * 初始化与客户有关的表单
	 */
	private void initClient(){
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		Client client = new Client();
		client.setAccount("admin");
		client.setPassword(md5.encodePassword("admin", "admin"));
		client.setCardnum("adhfhd24328988");
		client.setPaypass("paypass");
		ClientInfo clientInfo = new ClientInfo();
		clientInfo.setCreateDate(new Date());
		clientInfo.setPhonenum("15996292433");
		clientInfo.setRealname("张大勇");
		client.setClientInfo(clientInfo );
		clientService.create(client);
	}

}

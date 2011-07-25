package com.ipay.server.util;

import java.io.IOException;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.ipay.server.download.Main;
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
	 * @throws IOException
	 */
	public void init() throws IOException{
		initUser();
		initProduct();
		initClient();
		initMarket();
	}

	/**
	 * 初始化权限
	 */
	private void initUser() {
		User user=new User();
		
		Authority auth=new Authority();
		String name="";
		auth.setName(name);
		
		user.getAuthorityList().add(auth);
		
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		InitDatabase initDatabase = new InitDatabase();
		initDatabase.init();
	}
	
	/**
	 * 初始化与产品有段的表单
	 * 产品：图书 12×49=588条
	 * 	         食品 24×24=576条
	 * @throws IOException 
	 */
	private void initProduct() throws IOException{
		Product product=new Product();		
		ProductInfo productinfo=new ProductInfo();
		Main main=new Main();
		//电脑产品
		//Document base=Jsoup.connect("http://www.amazon.cn/s/ref=amb_link_28946932_1?ie=UTF8&rh=n%3A888483051&page=1&pf_rd_m=A1AJ19PSB66TGU&pf_rd_s=center-banner&pf_rd_r=0C9528G07S1VK1M35PAT&pf_rd_t=101&pf_rd_p=61173952&pf_rd_i=888465051").get();
		//经济类图书
		//Document base=Jsoup.connect("http://www.amazon.cn/s?ie=UTF8&rh=n%3A77817071&page=1").get();
		//运动类服装
		//Document base=Jsoup.connect("http://www.amazon.cn/s/ref=amb_link_29737092_13?ie=UTF8&rh=n%3A42786071%2Cp_n_target_audience_browse-bin%3A2039852051&pf_rd_m=A1AJ19PSB66TGU&pf_rd_s=left-2&pf_rd_r=018KJJQY2JEZT2CK5B67&pf_rd_t=101&pf_rd_p=60743632&pf_rd_i=42786071").get();
		//食品类
		Document base=Jsoup.connect("http://www.amazon.cn/gp/search/ref=sr_ex_n_1?rh=n%3A2127215051%2Cn%3A!2127216051%2Cn%3A2134651051&bbn=2134651051&ie=UTF8&qid=1311492586").get();
		String baseurl=base.baseUri().substring(0,base.baseUri().toString().length()-1);
		String flag="food";
		//String flag="book";
		if(flag=="food")
		{
			for(int i=1;i<25;i++)
			{
				//main.Download(baseurl, i,3,product,productinfo,"book");	//经济类图书
				//main.Download(baseurl, i, 2);	//电脑类产品
				//main.Download(baseurl, i, 2);	//运动类服装
				productService.create(main.Download(baseurl,i,2,product,productinfo,"food"));	//食品类
			}
		}
		if(flag=="book")
		{
			for(int i=1;i<50;i++)
			{
				productService.create(main.Download(baseurl, i, 3, product, productinfo, flag));
			}
		}
	}
	
	/**
	 * 初始化与客户有关的表单
	 * 创建100条数据
	 */
	private void initClient(){
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		Order order=new Order();
		Record record=new Record();
		Product product=new Product();		
		
		order.setCost(1000.0);
		order.setQuantity(1);		
		
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
		client.getRecords().add(record);
		
		clientService.create(client);
		
		for(int i=0;i<100;i++)
		{
			client.setAccount("Client"+i);
			client.setAccount(md5.encodePassword("Client"+i, "Client"+i));
			client.setCardnum("asdfa12"+i%10+"34"+i/10+"67");
			client.setPaypass("paypass"+i);
			
			clientInfo.setCreateDate(new Date());
			clientInfo.setPhonenum("15996294"+i/10+"67"+i%10);
			clientInfo.setRealname("张三");
			
			client.setClientInfo(clientInfo);
			
			clientService.create(client);
		}
	}
	
	/**
	 * 初始化商场表单
	 * 包括Marlet和Marketinfo
	 */
	private void initMarket(){
		Market market=new Market();
		MarketInfo marketinfo=new MarketInfo();
		
		
		String cardnum=null;
		String market_ip=null;
		String account=null;
		String password=null;
		
		
		//初始化商场1信息
		
		market.setCardnum("TM2011001");
		market.setIp(market_ip);
		market.setMarketInfo(marketinfo);
		market.setAccount("market_1");
		market.setPassword("123");
				
		
		marketService.create(market);
		
		//初始化商场2信息
		market.setCardnum("TM2011002");
		market.setAccount("market_2");
		market.setIp(market_ip);
		market.setMarketInfo(marketinfo);
		market.setPassword("123");
		
		marketService.create(market);
		
		//初始化商场3信息
		market.setCardnum("TM2011003");
		market.setIp(market_ip);
		market.setMarketInfo(marketinfo);
		market.setAccount("market_3");
		market.setPassword("123");
		
		marketService.create(market);
	}
	
	
	/*
	 * 初始化
	 */

}

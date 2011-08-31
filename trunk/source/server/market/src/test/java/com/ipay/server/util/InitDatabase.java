package com.ipay.server.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
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
		System.out.println("Init User");
		initUser();
		System.out.println("InitClient");
		initClient();
		System.out.println("InitMarket");
		initMarket();
		System.out.println("InitProduct");
		initProduct();
		//initMarket();
	}

	/**
	 * 初始化权限
	 */
	private void initUser() {
		User user=new User();
		
		Authority auth=new Authority();
		auth.setName("admin");
		userService.createAuthority(auth);
		
		Authority auth2=new Authority();
		auth2.setName("market");
		userService.createAuthority(auth2);
		
		Authority auth3=new Authority();
		auth3.setName("client");		
		userService.createAuthority(auth3);		
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

		//String flag="book";
		//String flag="book";

		String flag="food";
		if(flag=="food")
		{
			
			Market market1=marketService.find(Market.class, 31);
			Market market2=marketService.find(Market.class, 32);
			Market market3=marketService.find(Market.class, 33);
			for(int i=1;i<2;i++)
			{
				Jsoup.connect(baseurl+i).timeout(200000);
				Document html=Jsoup.connect(baseurl+i).get();
				System.out.println(html.getElementById("resultCount").text());
				System.out.println("第"+i+"页：");
						
				String title=html.title();
				System.out.println(title);
				int startindex=0;
				int length=0;String price=null;
				Attribute attr=new Attribute("","");
				Element startIndex_div=html.getElementById("atfResults");
				
				Element startIndex_div2=startIndex_div.getElementsByAttribute("name").get(0);
				String startIndex_str=startIndex_div2.attr("id");
				
				startIndex_str=startIndex_str.substring(7);
				//System.out.println(startIndex_str);
				startindex=Integer.parseInt(startIndex_str);
				
				Element length_div=html.getElementById("btfResults");
				length=length_div.getElementsByAttributeValue("class","result product").size()+4;
				
				//flag判断所属种类，并进入对应的处理程序			
								
					String banner=null;
						
					
					ProductInfo [] temp_productinfo=new ProductInfo[50];
					
					for(int k=startindex;k<length;k++)
					{
						ProductInfo productinfo=new ProductInfo();
						Element temp_div=html.getElementById("result_"+k);			
						
						Element temp_div2=temp_div.getElementsByTag("div").get(2);
						
						
						String bookname=temp_div2.getElementsByTag("a").get(0).text();
						
						price=temp_div2.parent().getElementsByTag("span").first().text().substring(1);					
						String imgUrl=temp_div.getElementsByTag("img").first().attr("src");
						String detail=temp_div2.getElementsByTag("a").get(0).attr("href");
						Elements temp_span=temp_div2.getElementsByTag("span");			
							System.out.println(bookname+" Price:"+price);				
							productinfo.setVersion(1);
							productinfo.setBanner("Produce_1");
							productinfo.setName(bookname);
							productinfo.setMinImgUrl(imgUrl);
							banner=getDetail(detail,productinfo,flag);
						
						
						
						productService.createProductInfo(productinfo);
						temp_productinfo[k]=productinfo;
						
						
						
					}
					for(int m=startindex;m<length;m++)
					{
						Product product=new Product();
						Element temp_div=html.getElementById("result_"+m);			
						
						Element temp_div2=temp_div.getElementsByTag("div").get(2);			
						String bookname=temp_div2.getElementsByTag("a").get(0).text();
						
						price=temp_div2.parent().getElementsByTag("span").first().text().substring(1);					
						
						String detail=temp_div2.getElementsByTag("a").get(0).attr("href");
						Elements temp_span=temp_div2.getElementsByTag("span");	
						
						
						product.setProductInfo(temp_productinfo[m]);
						product.setPrice(Double.parseDouble(price));
						
						if(i<7)
						 {product.setMarket(market1);}
						 if(i>=7&& i<9)
						 {product.setMarket(market2);}
						 if(i>=9)
						 {product.setMarket(market3);}
						 
						
						productService.create(product);
						if(i==1||i==8||i==9)
						{
							String intr="原价"+(Double.parseDouble(price))*1.1+"，现价"+Double.parseDouble(price)+",有木有!!!快来抢购";
							SpecialProduct special=new SpecialProduct();
							special.setIntroduction(intr);
							special.setOldprice(Double.parseDouble(price)*1.1);
							special.setProduct(product);
							marketService.addSpecialProduct(special);
						}
					}
					
					
			}
		}

		base=Jsoup.connect("http://www.amazon.cn/s?ie=UTF8&rh=n%3A77817071&page=1").get();
		baseurl=base.baseUri().substring(0,base.baseUri().toString().length()-1);
		flag="book";
		if(flag=="book")
		{
			int i=1;
			for(i=1;i<2;i++)
			{
				//main.Download(marketService,productService,baseurl, i, 3, product, productinfo, flag);
				//productService.create(main.Download(baseurl, i, 3, product, productinfo, flag));
				Jsoup.connect(baseurl+i).timeout(200000);
				Document html=Jsoup.connect(baseurl+i).get();
				System.out.println(html.getElementById("resultCount").text());
				System.out.println("第"+i+"页：");
						
				String title=html.title();
				System.out.println(title);
				int startindex=0;
				int length=0;String price=null;				
				Element startIndex_div=html.getElementById("atfResults");
				
				Element startIndex_div2=startIndex_div.getElementsByAttribute("name").get(0);
				String startIndex_str=startIndex_div2.attr("id");
				
				startIndex_str=startIndex_str.substring(7);
				//System.out.println(startIndex_str);
				startindex=Integer.parseInt(startIndex_str);
				
				Element length_div=html.getElementById("btfResults");
				length=length_div.getElementsByAttributeValue("class","result product").size()+4;
				
				String barcode=null;
					
				
				ProductInfo [] temp_productinfo=new ProductInfo[50];
				 for(int k=startindex;k<length;k++)
				  {	
					 ProductInfo productinfo=new ProductInfo();
					Element temp_div=html.getElementById("result_"+k);			
					
					Element temp_div2=temp_div.getElementsByTag("div").get(3);
					
					String bookname=temp_div2.getElementsByTag("a").get(0).text();
					String imgUrl=temp_div.getElementsByTag("img").first().attr("src");
					
					try{String bookprice=temp_div2.getElementsByTag("span").get(3).text().split(" ")[1];}
					catch(Exception e){String bookprice=temp_div2.getElementsByTag("span").get(2).text().split(" ")[1];}
					String detail=temp_div2.getElementsByTag("a").get(0).attr("href");
					Elements temp_span=temp_div2.getElementsByTag("span");			
						
						productinfo.setName(bookname);
						
						//System.out.println(temp_span.text());
						String author=temp_span.text().split(" ")[0];
						String printer=temp_span.text().split(" ")[1];				
						productinfo.getAtttributes().add(new Attribute("作者",author));				
						productinfo.getAtttributes().add(new Attribute("出版社",printer));
						System.out.println(author+" "+printer);
						productinfo.setBanner(printer);
						productinfo.setVersion(1);
						productinfo.setMinImgUrl(imgUrl);
						barcode=getDetail(detail,productinfo,flag);					
						productinfo.setBarcode(barcode);
											productService.createProductInfo(productinfo);
					temp_productinfo[k]=productinfo;					 
				  }	
				 for(int m=startindex;m<length;m++)
					{
					 Product product=new Product();
					 Element temp_div=html.getElementById("result_"+m);			
						
						Element temp_div2=temp_div.getElementsByTag("div").get(3);
						
						String bookname=temp_div2.getElementsByTag("a").get(0).text();
						String bookprice=null;
						try{bookprice=temp_div2.getElementsByTag("span").get(3).text().split(" ")[1];}
						catch(Exception e){bookprice=temp_div2.getElementsByTag("span").get(2).text().split(" ")[1];}
						
						String detail=temp_div2.getElementsByTag("a").get(0).attr("href");
						Elements temp_span=temp_div2.getElementsByTag("span");			
						System.out.println(bookname+" Price:"+bookprice);												
						
						product.setPrice(Double.parseDouble(bookprice));					
						product.setVersion(1);
						product.setQuantity(100);
						product.setProductInfo(temp_productinfo[m]);
						if(i<10)
						 {product.setMarket(marketService.find(Market.class, 31));}
						 if(i>=10&& i<15)
						 {product.setMarket(marketService.find(Market.class, 32));}
						 if(i>=15)
						 {product.setMarket(marketService.find(Market.class, 33));}
						 
						
						productService.create(product);
					}
								
			}
			
			/*for(i=15;i<40;i++)
			{
				//main.Download(marketService,productService,baseurl, i, 3, product, productinfo, flag);
				//productService.create(main.Download(baseurl, i, 3, product, productinfo, flag));
				Jsoup.connect(baseurl+i).timeout(200000);
				Document html=Jsoup.connect(baseurl+i).get();
				System.out.println(html.getElementById("resultCount").text());
				System.out.println("第"+i+"页：");
						
				String title=html.title();
				System.out.println(title);
				int startindex=0;
				int length=0;String price=null;				
				Element startIndex_div=html.getElementById("atfResults");
				
				Element startIndex_div2=startIndex_div.getElementsByAttribute("name").get(0);
				String startIndex_str=startIndex_div2.attr("id");
				
				startIndex_str=startIndex_str.substring(7);
				//System.out.println(startIndex_str);
				startindex=Integer.parseInt(startIndex_str);
				
				Element length_div=html.getElementById("btfResults");
				length=length_div.getElementsByAttributeValue("class","result product").size()+4;
				
				String barcode=null;
					
				
				ProductInfo [] temp_productinfo=new ProductInfo[50];
				 for(int k=startindex;k<length;k++)
				  {	
					 ProductInfo productinfo=new ProductInfo();
					Element temp_div=html.getElementById("result_"+k);			
					
					Element temp_div2=temp_div.getElementsByTag("div").get(3);
					
					String bookname=temp_div2.getElementsByTag("a").get(0).text();
					String imgUrl=temp_div.getElementsByTag("img").first().attr("src");
					
					String detail=temp_div2.getElementsByTag("a").get(0).attr("href");
					Elements temp_span=temp_div2.getElementsByTag("span");			
						
						productinfo.setName(bookname);
						
						//System.out.println(temp_span.text());
						String author=temp_span.text().split(" ")[0];
						String printer=temp_span.text().split(" ")[1];				
						productinfo.getAtttributes().add(new Attribute("作者",author));				
						productinfo.getAtttributes().add(new Attribute("出版社",printer));
						System.out.println(author+" "+printer);
						productinfo.setBanner(printer);
						productinfo.setVersion(1);
						productinfo.setMinImgUrl(imgUrl);
						barcode=getDetail(detail,productinfo,flag);					
						productinfo.setBarcode(barcode);
					productService.createProductInfo(productinfo);
					temp_productinfo[k]=productinfo;
					
				  }	
				 for(int m=startindex;m<length;m++)
					{
					 Product product=new Product();
					 Element temp_div=html.getElementById("result_"+m);			
						
						Element temp_div2=temp_div.getElementsByTag("div").get(3);
						
						String bookname=temp_div2.getElementsByTag("a").get(0).text();
						String bookprice=null;
						try{bookprice=temp_div2.getElementsByTag("span").get(3).text().split(" ")[1];}
						catch(Exception e){bookprice=temp_div2.getElementsByTag("span").get(2).text().split(" ")[1];}
						
						String detail=temp_div2.getElementsByTag("a").get(0).attr("href");
						Elements temp_span=temp_div2.getElementsByTag("span");			
						System.out.println(bookname+" Price:"+bookprice);												
						
						product.setPrice(Double.parseDouble(bookprice));					
						product.setVersion(1);
						product.setQuantity(100);
						product.setProductInfo(temp_productinfo[m]);
						if(i<10)
						 {product.setMarket(marketService.find(Market.class, 31));}
						 if(i>=10&& i<15)
						 {product.setMarket(marketService.find(Market.class, 32));}
						 if(i>=15)
						 {product.setMarket(marketService.find(Market.class, 33));}
						 
						
						productService.create(product);
					}
			}*/
		}
	}
	
	public static String getDetail(String url,ProductInfo productinfo,String flag) throws IOException
	{
		Jsoup.connect(url).timeout(200000);
		Document detailHtml=Jsoup.connect(url).get();
		//Jsoup.connect(url).timeout(100000);
		//System.out.println(detailHtml);
		Elements details=detailHtml.getElementsByTag("h2");
		String banner=null;
		//Element detail=details.get(17);
		//System.out.println(detail.text());
		Element temp_element;
		Element originImg=detailHtml.select("td#prodImageCell").first();
		Element originImg_img=originImg.getElementsByTag("img").first();
		String href=originImg_img.attr("src");
		System.out.println(href);
		for(int i=0;i<details.size();i++)
		{			
			if(details.get(i).text().equals("基本信息"))
			{
				Element ul=details.get(i).parent().getElementsByTag("ul").first();
				Elements lis=ul.getElementsByTag("li");
				
				if(flag=="book")
				{
					for(int m=0;m<lis.size();m++)
					{
						String []temp_array=new String[2];
						temp_array=lis.get(m).text().split(" ",2);
						if(temp_array[0].equals("ISBN:"))
							{productinfo.getAtttributes().add(new Attribute("ISBN",temp_array[1]));}
						if(temp_array[0].equals("条形码:"))
							{productinfo.setBarcode(temp_array[1]);
							banner=temp_array[1];}
						if(temp_array[0].equals("平装:"))
							{productinfo.getAtttributes().add(new Attribute("规格",temp_array[0]));							 
							 productinfo.getAtttributes().add(new Attribute("页数",temp_array[1]));}					
					}					
				}
				if(flag=="food")
				{
					String []temp_array=new String[2];
					
					for(int k=0;k<lis.size();k++)
					{
						temp_array=lis.get(k).text().split(" ",2);
						if(temp_array[0].equals("产品尺寸及重量:"))
							{productinfo.getAtttributes().add(new Attribute("产品尺寸及重量",temp_array[1]));
							 }
						if(temp_array[0].equals("ASIN:"))
							{productinfo.getAtttributes().add(new Attribute("ASIN",temp_array[1]));
							productinfo.setBarcode(temp_array[1]);banner=temp_array[1];}
							 //System.out.println(temp_array[0]+" "+temp_array[1]);}
						if(temp_array[0].equals("产地:"))
							{productinfo.getAtttributes().add(new Attribute("产地",temp_array[1]));
							 }
					}
				}
			}
		}
		return banner;
	}
	
	/**
	 * 初始化与客户有关的表单
	 * 创建100条数据
	 * @throws IOException 
	 */
	private void initClient() throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("O:\\Workspace\\Eclipse\\iPay_Download\\name.txt")));
		String [] nameset=new String[120];String str="";String data=null;
		while((data=br.readLine())!=null)
		{
			str+=data;
		}
		nameset=str.split(" ");
		
		ShaPasswordEncoder sha=new ShaPasswordEncoder();
		Order order=new Order();				
		
		order.setCost(1000.0);
		order.setQuantity(1);		
		Authority auth=new Authority();
		auth=userService.getAuthority("client");
		
		
		for(int i=0;i<30;i++)
		{
			Client client = new Client();			
			ClientInfo clientInfo = new ClientInfo();
			
			
			
			client.setAccount("Client"+i);
			client.setPassword(sha.encodePassword("Client"+i, "Client"+i));
			client.setCardnum("622848 208149873"+(4000+i));
			client.setPaypass(sha.encodePassword("paypass"+i, client.getAccount()));
			client.setPaypass("paypass"+i);
			
			client.getAuthorityList().add(auth);			
			
			clientInfo.setCreateDate(new Date());
			clientInfo.setPhonenum("15996294"+i/10+"67"+i%10);
			clientInfo.setRealname(nameset[i]);
			
			
			client.setClientInfo(clientInfo);
			
			clientService.create(client);
			System.out.println(i+" has Inserted");
		}
	}
	
	/**
	 * 初始化商场表单
	 * 包括Marlet和Marketinfo
	 */
	private void initMarket(){
			
		//初始化商场1信息
		//marketinfo.setComplainPhone("025-23893278");
		//marketinfo.setCreateDate(new Date());
		//marketinfo.setLocation("");
		Market market=new Market();
		MarketInfo marketinfo=new MarketInfo();	
		ShaPasswordEncoder sha=new ShaPasswordEncoder();
 
		String introduction="苏果超市在仙林地区的分店，便于学生购物";
		
	    marketinfo.setIntroduction(introduction);
        marketinfo.setName("苏果超市仙林店");
        marketinfo.setComplainPhone("025-84860400");
        marketinfo.setLocation("南京市栖霞区亚东新区");
        marketinfo.setServicePhone("025-84860400");
        marketinfo.setCreateDate(new Date());
        
		
		market.setCardnum("TM2011001");
		market.setIp("114.123.43.12");
		market.setMarketInfo(marketinfo);
		market.setAccount("market_1");
		market.setPassword(sha.encodePassword("market_1", "market_1"));
		
		market.getAuthorityList().add(userService.getAuthority("market"));
				
		
		marketService.create(market);
		
		//初始化商场2信息
		Market market2=new Market();
		MarketInfo marketinfo2=new MarketInfo();
		String introduction2="南京金润发鼓楼分店，价格比较便宜，规模一般";
		
		marketinfo2.setIntroduction(introduction2);
        marketinfo2.setName("金润发鼓楼店");
        marketinfo2.setComplainPhone("025-84862376");
        marketinfo2.setLocation("江苏省南京市玄武区丹凤街39号");
        marketinfo2.setServicePhone("025-84869282");
        marketinfo2.setCreateDate(new Date());
        
				
		market2.setCardnum("TM2011002");
		market2.setAccount("market_2");
		market2.setIp("143.43.38.56");
		market2.setMarketInfo(marketinfo2);
		market2.setPassword(sha.encodePassword("market_2", "market_2"));
		
		market2.getAuthorityList().add(userService.getAuthority("market"));
		
		marketService.create(market2);
		
		//初始化商场3信息
		Market market3=new Market();
		MarketInfo marketinfo3=new MarketInfo();
		String introduction3="南京沃尔玛新街口店，规模较大，市中心繁华区，品种齐全";
		
		marketinfo3.setIntroduction(introduction3);
        marketinfo3.setName("南京沃尔玛新街口店");
        marketinfo3.setComplainPhone("025-84782888");
        marketinfo3.setLocation("南京市白下区洪武路88号万达购物广场2-3楼");
        marketinfo3.setServicePhone("025-84782888");
        marketinfo3.setCreateDate(new Date());
        
		
		market3.setCardnum("TM2011003");
		market3.setIp("213.21.23.221");
		market3.setMarketInfo(marketinfo3);
		market3.setAccount("market_3");
		market3.setPassword(sha.encodePassword("market_3", "market_3"));
		
		market3.getAuthorityList().add(userService.getAuthority("market"));
				
		marketService.create(market3);
		
		System.out.println("Market Over");
	}
	
	
	/*
	 * 初始化
	 */

}

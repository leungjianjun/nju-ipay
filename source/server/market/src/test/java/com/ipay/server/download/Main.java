package com.ipay.server.download;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.ipay.server.entity.Attribute;
import com.ipay.server.entity.Client;
import com.ipay.server.entity.Market;
import com.ipay.server.entity.Product;
import com.ipay.server.entity.ProductInfo;
import com.ipay.server.entity.Record;
import com.ipay.server.entity.User;

import com.ipay.server.download.Main;
import com.ipay.server.entity.*;
import com.ipay.server.service.*;

public class Main {
	
	
	public Main()
	{				
	}
	
	
	public Product Download(IMarketService<Market> marketService,IProductService<Product> productService,String baseurl,int i,int index,Product product,ProductInfo productinfo,String flag) throws IOException
	{		
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
		if(flag=="book"){
		 for(int k=startindex;k<length;k++)
		  {
			Element temp_div=html.getElementById("result_"+k);			
			
			Element temp_div2=temp_div.getElementsByTag("div").get(index);
			
			String bookname=temp_div2.getElementsByTag("a").get(0).text();
			
			String bookprice=temp_div2.getElementsByTag("span").get(3).text().split(" ")[1];
			String detail=temp_div2.getElementsByTag("a").get(0).attr("href");
			Elements temp_span=temp_div2.getElementsByTag("span");			
				System.out.println(bookname+" Price:"+bookprice);
				productinfo.setName(bookname);
				
				//System.out.println(temp_span.text());
				String author=temp_span.text().split(" ")[0];
				String printer=temp_span.text().split(" ")[1];				
				productinfo.getAtttributes().add(new Attribute("作者",author));				
				productinfo.getAtttributes().add(new Attribute("出版社",printer));
				productinfo.setBanner(printer);
				productinfo.setVersion(1);
				String banner=getDetail(detail,attr,product,productinfo,flag);
			
			product.setPrice(Double.parseDouble(bookprice));
			product.setProductInfo(productService.getProductInfo(banner));
			product.setVersion(1);
			product.setQuantity(100);
		  }
		 if(i<10)
		 {product.setMarket(marketService.find(Market.class, 1));}
		 if(i>=10&& i<15)
		 {product.setMarket(marketService.find(Market.class, 2));}
		 if(i>=15)
		 {product.setMarket(marketService.find(Market.class, 3));}
		 
		 
		  productService.createProductInfo(productinfo);
		  productService.create(product);
		  System.out.println("insert");
		}
		
		if(flag=="food")
		{
			for(int k=startindex;k<length;k++)
			{
				Element temp_div=html.getElementById("result_"+k);			
				
				Element temp_div2=temp_div.getElementsByTag("div").get(index);			
				String bookname=temp_div2.getElementsByTag("a").get(0).text();
				
				price=temp_div2.parent().getElementsByTag("span").first().text().substring(1);					
				
				String detail=temp_div2.getElementsByTag("a").get(0).attr("href");
				Elements temp_span=temp_div2.getElementsByTag("span");			
					System.out.println(bookname+" Price:"+price);				
					
					
					String banner=getDetail(detail,attr,product,productinfo,flag);
				
				product.setProductInfo(productService.getProductInfo(banner));	
				product.setPrice(Double.parseDouble(price));
				product.setVersion(1);
				product.setQuantity(100);
			}
			
			if(i<10)
			 {product.setMarket(marketService.find(Market.class, 1));}
			 if(i>=10&& i<15)
			 {product.setMarket(marketService.find(Market.class, 2));}
			 if(i>=15)
			 {product.setMarket(marketService.find(Market.class, 3));}
			 
			productService.createProductInfo(productinfo);
			productService.create(product);
		}
		
		return product;
	}

	public static String getDetail(String url,Attribute attr,Product product,ProductInfo productinfo,String flag) throws IOException
	{
		Document detailHtml=Jsoup.connect(url).get();
		//System.out.println(detailHtml);
		Elements details=detailHtml.getElementsByTag("h2");
		String banner=null;
		//Element detail=details.get(17);
		//System.out.println(detail.text());
		Element temp_element;
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
							productinfo.setBanner(temp_array[1]);banner=temp_array[1];}
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
}


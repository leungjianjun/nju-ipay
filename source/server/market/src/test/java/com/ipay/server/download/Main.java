package com.ipay.server.download;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ipay.server.entity.Attribute;
import com.ipay.server.entity.Product;
import com.ipay.server.entity.ProductInfo;

import com.ipay.server.download.Main;
import com.ipay.server.entity.*;
import com.ipay.server.service.*;

public class Main {
	public Main()
	{		
	}
	
	
	public Product Download(String baseurl,int i,int index,Product product,ProductInfo productinfo,String flag) throws IOException
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
				attr.setKey("作者");attr.setValue(author);
				productinfo.getAtttributes().add(attr);
				attr.setKey("出版社");attr.setValue(printer);
				productinfo.getAtttributes().add(attr);
				
				getDetail(detail,attr,productinfo,flag);
			
			productinfo.getAtttributes().add(attr);
			product.setProductInfo(productinfo);
			product.setPrice(Double.parseDouble(bookprice));
			
			
		  }
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
					//String author=temp_span.text().split(" ")[0];
					//String printer=temp_span.text().split(" ")[1];
					//System.out.println(price);
					System.out.println("详细信息:");
					getDetail(detail,attr,productinfo,flag);
				System.out.println("\n");
				
				productinfo.getAtttributes().add(attr);
				//product.setPrice(Double.parseDouble(price));
				product.setProductInfo(productinfo);
				
				
			}
		}
		
		return product;
	}

	public static void getDetail(String url,Attribute attr,ProductInfo productinfo,String flag) throws IOException
	{
		Document detailHtml=Jsoup.connect(url).get();
		//System.out.println(detailHtml);
		Elements details=detailHtml.getElementsByTag("h2");
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
							{attr.setKey("ISBN");attr.setValue(temp_array[1]);
							productinfo.getAtttributes().add(attr);}
						if(temp_array[0].equals("条形码:"))
							productinfo.setBarcode(temp_array[1]);
						if(temp_array[0].equals("平装:"))
							{attr.setKey("规格");attr.setValue(temp_array[0]);
							productinfo.getAtttributes().add(attr);
							 attr.setKey("页数");attr.setValue(temp_array[1]);
							 productinfo.getAtttributes().add(attr);}					
					}
				}
				if(flag=="food")
				{
					String []temp_array=new String[2];
					
					for(int k=0;k<lis.size();k++)
					{
						temp_array=lis.get(k).text().split(" ",2);
						if(temp_array[0].equals("产品尺寸及重量:"))
							{attr.setKey("产品尺寸及重量:");attr.setValue(temp_array[1]);
							 productinfo.getAtttributes().add(attr);
							 System.out.println(temp_array[0]+" "+temp_array[1]);}
						if(temp_array[0].equals("ASIN:"))
							{attr.setKey("ASIN");attr.setValue(temp_array[1]);
							 productinfo.getAtttributes().add(attr);
							 System.out.println(temp_array[0]+" "+temp_array[1]);}
						if(temp_array[0].equals("产地:"))
							{attr.setKey("产地");attr.setValue(temp_array[1]);
							 productinfo.getAtttributes().add(attr);
							 System.out.println(temp_array[0]+" "+temp_array[1]);}
					}
				}
			}
		}
		
	}
}


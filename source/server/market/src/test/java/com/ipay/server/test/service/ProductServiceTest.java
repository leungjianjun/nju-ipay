package com.ipay.server.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ipay.server.entity.Attribute;
import com.ipay.server.entity.Market;
import com.ipay.server.entity.Product;
import com.ipay.server.entity.ProductInfo;
import com.ipay.server.service.IMarketService;
import com.ipay.server.service.IProductService;
import com.ipay.server.util.SpringJUnit45ClassRunner;

@RunWith(SpringJUnit45ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/root-context.xml","classpath:spring/appServlet/servlet-context.xml"})
public class ProductServiceTest {
	
	private IProductService<Product> productService;
	
	private IMarketService<Market> marketService;

	public IMarketService<Market> getMarketService() {
		return marketService;
	}

	@Autowired
	public void setMarketService(IMarketService<Market> marketService) {
		this.marketService = marketService;
	}

	public IProductService<Product> getProductService() {
		return productService;
	}

	@Autowired
	public void setProductService(IProductService<Product> productService) {
		this.productService = productService;
	}
	
	@Test
	public void testCreate(){
		ProductInfo productInfo = new ProductInfo();
		productInfo.setBanner("广华集团");
		productInfo.setBarcode("738374833");
		productInfo.setImgUrl("product/image/123.gif");//url格式另外定义
		productInfo.setName("广华牌摇摆杯");
		productInfo.getAtttributes().add(new Attribute("容量","600ml"));
		productService.createProductInfo(productInfo);
		
		Product product = new Product();
		product.setMarket(marketService.find(Market.class, 1));
		product.setPrice(7.5);
		product.setQuantity(10);
		product.setProductInfo(productService.getProductInfo("738374833"));
		productService.create(product);
	}
	

}

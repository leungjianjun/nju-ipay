package com.ipay.server.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ipay.server.entity.Attribute;
import com.ipay.server.entity.Market;
import com.ipay.server.entity.Product;
import com.ipay.server.entity.ProductInfo;
import com.ipay.server.entity.SpecialProduct;
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
	public void testCreateProductInfo(){
		ProductInfo productInfo = new ProductInfo();
		productInfo.setBanner("广华集团");
		productInfo.setBarcode("738374833");
		productInfo.setImgUrl("product/image/123.gif");//url格式另外定义
		productInfo.setName("广华牌摇摆杯");
		productInfo.getAtttributes().add(new Attribute("容量","600ml"));
		productService.createProductInfo(productInfo);
		
		ProductInfo productInfo2 = new ProductInfo();
		productInfo2.setBanner("上海绅门服饰有限公司");
		productInfo2.setBarcode("838374833");
		productInfo2.setImgUrl("product/image/124.gif");//url格式另外定义
		productInfo2.setName("绅门男士柔棉弹力背心");
		productInfo2.getAtttributes().add(new Attribute("颜色","黑色、白色、灰色"));
		productService.createProductInfo(productInfo2);
	}
	
	@Test
	public void testGetProductInfoByCode(){
		ProductInfo productInfo = productService.getProductInfoByCode("738374833");
		Assert.assertEquals("广华集团", productInfo.getBanner());
		Assert.assertEquals("广华牌摇摆杯", productInfo.getName());
		
		ProductInfo productInfo2 = productService.getProductInfoByCode("838374833");
		Assert.assertEquals("上海绅门服饰有限公司", productInfo2.getBanner());
		Assert.assertEquals("绅门男士柔棉弹力背心", productInfo2.getName());
	}
	
	@Test
	public void testCreateProduct(){
		Product product = new Product();
		product.setMarket(marketService.find(Market.class, 2));//find方法是按marketid找market的，所以一定要预先知道market_id,market_id不一定从1开始
		product.setPrice(7.5);
		product.setQuantity(10);
		product.setProductInfo(productService.getProductInfoByCode("738374833"));
		productService.create(product);
	}
	
	@Test
	public void testAddSpecialProduct(){
		Product product2 = new Product();
		product2.setMarket(marketService.find(Market.class, 2));//find方法是按marketid找market的，所以一定要预先知道market_id,market_id不一定从1开始
		product2.setPrice(12.9);
		product2.setQuantity(100);
		product2.setProductInfo(productService.getProductInfoByCode("838374833"));
		productService.create(product2);
		SpecialProduct specialProduct = new SpecialProduct();
		specialProduct.setIntroduction("仅售12.9元！原价39.9元的绅门男士柔棉弹力背心（黑色、白色、灰色，3种颜色随机发送）一件！");
		specialProduct.setOldprice(39.9);
		specialProduct.setProduct(product2);
		marketService.addSpecialProduct(specialProduct);
	}
	
	
}

package com.ipay.server.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ipay.server.entity.Attribute;
import com.ipay.server.entity.ProductInfo;
import com.ipay.server.service.IProductInfoService;
import com.ipay.server.util.SpringJUnit45ClassRunner;

@RunWith(SpringJUnit45ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/root-context.xml","classpath:spring/appServlet/servlet-context.xml"})
public class ProductInfoServiceTest {
	
	private IProductInfoService<ProductInfo> productInfoService;

	public IProductInfoService<ProductInfo> getProductInfoService() {
		return productInfoService;
	}

	@Autowired
	public void setProductInfoService(IProductInfoService<ProductInfo> productInfoService) {
		this.productInfoService = productInfoService;
	}
	
	@Test
	public void testCreateProductInfo(){
		ProductInfo productInfo = new ProductInfo();
		productInfo.setBanner("广华集团");
		productInfo.setBarcode("738374833");
		productInfo.setMinImgUrl("product/image/123.gif");//url格式另外定义
		productInfo.setName("广华牌摇摆杯");
		productInfo.getAtttributes().add(new Attribute("容量","600ml"));
		productInfoService.create(productInfo);
		
		ProductInfo productInfo2 = new ProductInfo();
		productInfo2.setBanner("上海绅门服饰有限公司");
		productInfo2.setBarcode("838374833");
		productInfo2.setMinImgUrl("product/image/124.gif");//url格式另外定义
		productInfo2.setMidImgUrl("product/image/124.gif");//url格式另外定义
		productInfo2.setName("绅门男士柔棉弹力背心");
		productInfo2.getAtttributes().add(new Attribute("颜色","黑色、白色、灰色"));
		productInfoService.create(productInfo2);
	}
	
	@Test
	public void testUpdateProductInfo(){
		ProductInfo productInfo = productInfoService.find(ProductInfo.class, 1);
		productInfo.setMinImgUrl("/images/1_738374833_min.gif");
		productInfo.setMidImgUrl("/images/1_738374833_mid.gif");//不要漏了斜杠
		productInfoService.update(productInfo);
	}

}

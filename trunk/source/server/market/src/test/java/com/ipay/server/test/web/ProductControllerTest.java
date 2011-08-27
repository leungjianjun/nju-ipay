package com.ipay.server.test.web;

import org.junit.Test;

import com.ipay.server.util.ControllerTest;
import com.ipay.server.web.ProductController;

public class ProductControllerTest extends ControllerTest<ProductController> {

	@Override
	protected String getBeanName() {
		return "productController";
	}
	
	@Test
	public void testGetProductInfoByCode(){
		
	}

}

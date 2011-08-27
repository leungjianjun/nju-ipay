package com.ipay.server.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ipay.server.entity.Attribute;
import com.ipay.server.entity.Product;
import com.ipay.server.entity.ProductInfo;
import com.ipay.server.service.IProductService;
import com.ipay.server.service.ServiceException;

@Controller
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	private IProductService<Product> productService;

	public IProductService<Product> getProductService() {
		return productService;
	}

	@Autowired
	public void setProductService(IProductService<Product> productService) {
		this.productService = productService;
	}
	
	/**
	 * 获取热门商品
	 * 
	 * hotProducts:[{id:123,name:"",price:12.5,minImgUrl:""}
	 * @param mid
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/client/MarketHotProducts",method=RequestMethod.GET)
	public @ResponseBody Object getMarketHotProducts(@RequestParam int mid,@RequestParam int page){
		logger.info("get market hotProducts");
		return page;
		
	}
	
	/**
	 * 根据条形码获取商品信息
	 * 
	 * 
	 * @param mid
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/client/ProductInfoByCode",method=RequestMethod.GET)
	public @ResponseBody Object getProductInfoByCode(@RequestParam int mid,@RequestParam String code){
		logger.info("get productinfo by code");
		Product product = productService.getProductByCode(mid, code);
		Map<String,Object> result = Maps.newHashMap();
		result.put("id", product.getId());
		result.put("price", product.getPrice());
		result.put("quantity", product.getQuantity());
		
		ProductInfo productInfo = product.getProductInfo();
		result.put("name", productInfo.getName());
		result.put("banner", productInfo.getBanner());
		result.put("barcode", productInfo.getBarcode());
		result.put("minImgUrl", productInfo.getMinImgUrl());
		result.put("midImgUrl", productInfo.getMidImgUrl());
		Set<Attribute> attributes = Sets.newHashSet();
		for(Attribute attribute : productInfo.getAtttributes()){
			attributes.add(attribute);
		}
		result.put("attributes", attributes);
		return result;
	}
	
	@RequestMapping(value="/client/ProductIdByCode",method=RequestMethod.GET)
	public @ResponseBody Object getProductIdByCode(@RequestParam int mid,@RequestParam String code){
		Product product = productService.getProductByCode(mid, code);
		return Collections.singletonMap("id", product.getId());
	}
	
	@RequestMapping(value="/client/ProductInfoById",method=RequestMethod.GET)
	public @ResponseBody Object getProductInfoById(@RequestParam int pid){
		Product product = productService.find(Product.class, pid);
		Map<String,Object> result = Maps.newHashMap();
		result.put("id", product.getId());
		result.put("price", product.getPrice());
		result.put("quantity", product.getQuantity());
		
		ProductInfo productInfo = product.getProductInfo();
		result.put("name", productInfo.getName());
		result.put("banner", productInfo.getBanner());
		result.put("barcode", productInfo.getBarcode());
		result.put("minImgUrl", productInfo.getMinImgUrl());
		result.put("midImgUrl", productInfo.getMidImgUrl());
		Set<Attribute> attributes = Sets.newHashSet();
		for(Attribute attribute : productInfo.getAtttributes()){
			attributes.add(attribute);
		}
		result.put("attributes", attributes);
		return result;
	}
	
	/**
	 * 搜索商品
	 * 
	 * products:[{id:123,name:"",price:12.5,minImgUrl:""},
	 * @param mid
	 * @param name
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/client/SearchProduct",method=RequestMethod.GET)
	public @ResponseBody Object searchProductByName(@RequestParam int mid, @RequestParam String name, @RequestParam int page){
		List<Product> products = productService.searchProduct(mid, name, page);
		Set<Map<String,Object>> contents = Sets.newHashSet();
		for(Product product :products){
			Map<String,Object> temp = Maps.newHashMap();
			temp.put("id", product.getId());
			temp.put("name", product.getProductInfo().getName());
			temp.put("price", product.getPrice());
			temp.put("minImgUrl", product.getProductInfo().getMinImgUrl());
			contents.add(temp);
		}
		return Collections.singletonMap("products", contents);
	}
	
	/**
	 * 统一异常处理方法，把所有的service exception放在这里处理，返回json风格
	 * @param exception
	 * 			ServiceException异常
	 * @param response
	 * 			http响应
	 * @return
	 * 			错误的json风格消息
	 */
	@ExceptionHandler(ServiceException.class)
	public @ResponseBody Map<String, String> handleServiceException(ServiceException exception,HttpServletResponse response){
		response.setStatus(exception.getHttpStatusCode());
		Map<String, String> failureMessages = new HashMap<String, String>();
		failureMessages.put("status", "false");
		failureMessages.put("error", exception.getMessage());
		return failureMessages;
	}

}

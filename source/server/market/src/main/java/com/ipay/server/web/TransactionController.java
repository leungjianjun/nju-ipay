package com.ipay.server.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipay.server.entity.Order;
import com.ipay.server.entity.Product;
import com.ipay.server.entity.Record;
import com.ipay.server.security.ExceptionMessage;
import com.ipay.server.service.IOrderService;
import com.ipay.server.service.IProductService;
import com.ipay.server.service.IRecordService;
import com.ipay.server.service.ServiceException;

@Controller
public class TransactionController {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
	
	private IRecordService<Record> recordService;
	
	private IOrderService<Order> orderService;
	
	private IProductService<Product> productService;

	public IProductService<Product> getProductService() {
		return productService;
	}

	@Autowired
	public void setProductService(IProductService<Product> productService) {
		this.productService = productService;
	}

	public IRecordService<Record> getRecordService() {
		return recordService;
	}

	@Autowired
	public void setRecordService(IRecordService<Record> recordService) {
		this.recordService = recordService;
	}

	public IOrderService<Order> getOrderService() {
		return orderService;
	}

	@Autowired
	public void setOrderService(IOrderService<Order> orderService) {
		this.orderService = orderService;
	}
	
	@RequestMapping(value = "/client/SendOrder", method = RequestMethod.POST)
	public @ResponseBody Object sendOrder(@RequestBody Map<String, Object> param,Principal principal,HttpServletResponse response) {
		int mid = (Integer) param.get("mid");
		List<Map<String,Integer>> orders = (List<Map<String, Integer>>) param.get("orders");
		double total = 0;
		for(Map<String,Integer> order : orders){
			Product product = productService.find(Product.class,order.get("pid"));
			if(product.getMarket().getId()!= mid){//检查每个商品是否在同一商场购买
				response.setStatus(400);
				Map<String, String> failureMessages = new HashMap<String, String>();
				failureMessages.put("status", "false");
				failureMessages.put("error", ExceptionMessage.PRODUCT_NOT_IN_THE_SAME_MARKET);
				return failureMessages;
			}
			total+=product.getPrice()*order.get("quantity");
		}
		
		
		return principal;
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

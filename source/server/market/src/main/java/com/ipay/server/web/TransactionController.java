package com.ipay.server.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipay.server.entity.Order;
import com.ipay.server.entity.Record;
import com.ipay.server.service.IOrderService;
import com.ipay.server.service.IRecordService;
import com.ipay.server.service.ServiceException;

@Controller
public class TransactionController {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
	
	private IRecordService<Record> recordService;
	
	private IOrderService<Order> orderService;

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

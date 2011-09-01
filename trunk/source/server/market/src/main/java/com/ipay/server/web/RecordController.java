package com.ipay.server.web;

import java.security.Principal;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ipay.server.entity.Client;
import com.ipay.server.entity.Order;
import com.ipay.server.entity.Product;
import com.ipay.server.entity.Record;
import com.ipay.server.service.IClientService;
import com.ipay.server.service.IRecordService;
import com.ipay.server.service.ServiceException;

@Controller
public class RecordController {
	
	private static final Logger logger = LoggerFactory.getLogger(RecordController.class);
	
	private IRecordService<Record> recordService;
	
	private IClientService<Client> clientService;

	public IClientService<Client> getClientService() {
		return clientService;
	}

	@Autowired
	public void setClientService(IClientService<Client> clientService) {
		this.clientService = clientService;
	}

	public IRecordService<Record> getRecordService() {
		return recordService;
	}

	@Autowired
	public void setRecordService(IRecordService<Record> recordService) {
		this.recordService = recordService;
	}
	
	/**
	 * records:[{id:134,createDate:"",marketName:"",total:343.3}]
	 * 
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/client/getRecords", method = RequestMethod.GET)
	public @ResponseBody Map<String, List<Map<String, Object>>> getClientInfo(@RequestParam int page,Principal principal) {
		Client client = clientService.getClientByAccount(principal.getName());
		List<Map<String,Object>> result = Lists.newArrayList();
		for(Record record :recordService.getRecords(client.getId(), page)){
			Map<String,Object> temp = Maps.newHashMap();
			temp.put("id", record.getId());
			temp.put("creteDate", record.getCreateDate());
			temp.put("mid", record.getMarket().getId());
			temp.put("marketName", record.getMarket().getMarketInfo().getName());
			temp.put("total", record.getTotal());
			result.add(temp);
		}
		return Collections.singletonMap("records",result);
	}
	
	@RequestMapping(value = "/client/getRecordDetail", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getRecordDetail(@RequestParam int mid,Principal principal) {
		Map<String,Object> result = Maps.newHashMap();
		Record record = recordService.find(Record.class, mid);
		result.put("createDate", record.getCreateDate());
		result.put("marketId", record.getMarket().getId());
		result.put("marketName", record.getMarket().getMarketInfo().getName());
		result.put("total", record.getTotal());
		Set<Map<String,Object>> orders = Sets.newHashSet();
		for(Order order : record.getOrders()){
			Map<String,Object> temp = Maps.newHashMap();
			Product product = order.getProduct();
			temp.put("productId", product.getId());
			temp.put("productName", product.getProductInfo().getName());
			temp.put("quantity", order.getQuantity());
			temp.put("cost", order.getCost());
			orders.add(temp);
		}
		result.put("orders", orders);
		return result;
	}
	
	@ExceptionHandler(ControllerException.class)
	public @ResponseBody Map<String, String> handleControllerException(ControllerException exception,HttpServletResponse response){
		response.setStatus(exception.getHttpStatusCode());
		Map<String, String> failureMessages = new HashMap<String, String>();
		failureMessages.put("status", "false");
		failureMessages.put("error", exception.getMessage());
		return failureMessages;
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

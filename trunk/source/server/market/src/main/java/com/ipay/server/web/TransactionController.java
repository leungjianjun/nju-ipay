package com.ipay.server.web;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ipay.server.bankproxy.BankProxyServerException;
import com.ipay.server.bankproxy.BankServerProxy;
import com.ipay.server.bankproxy.PayRequestSign;
import com.ipay.server.bankproxy.PayResponse;
import com.ipay.server.entity.Client;
import com.ipay.server.entity.Market;
import com.ipay.server.entity.Order;
import com.ipay.server.entity.Product;
import com.ipay.server.entity.Record;
import com.ipay.server.security.ExceptionMessage;
import com.ipay.server.security.KeyManager;
import com.ipay.server.security.PrivateKeyEncryptor;
import com.ipay.server.service.IClientService;
import com.ipay.server.service.IMarketService;
import com.ipay.server.service.IOrderService;
import com.ipay.server.service.IProductService;
import com.ipay.server.service.IRecordService;
import com.ipay.server.service.ServiceException;

@Controller
public class TransactionController {
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
	
	private IRecordService<Record> recordService;
	
	private IOrderService<Order> orderService;
	
	private IProductService<Product> productService;
	
	private IClientService<Client> clientService;
	
	private IMarketService<Market> marketService;

	public IMarketService<Market> getMarketService() {
		return marketService;
	}

	@Autowired
	public void setMarketService(IMarketService<Market> marketService) {
		this.marketService = marketService;
	}

	public IClientService<Client> getClientService() {
		return clientService;
	}

	@Autowired
	public void setClientService(IClientService<Client> clientService) {
		this.clientService = clientService;
	}

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
	
	/**
	 * 处理结算请求
	 * 
	 * @param param
	 * @param principal
	 * @param response
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/client/SendOrder", method = RequestMethod.POST)
	public @ResponseBody Object sendOrder(@RequestBody Map<String, Object> param,Principal principal,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		int mid = (Integer) param.get("mid");
		List<Map<String,Integer>> orders = (List<Map<String, Integer>>) param.get("orders");
		double total = 0;
		Record record = new Record();
		record.setClient(clientService.getClientByAccount(principal.getName()));
		record.setCreateDate(new Date());
		record.setEffective(false);
		record.setMarket(marketService.find(Market.class, mid));
		for(Map<String,Integer> order : orders){
			Product product = productService.find(Product.class,order.get("pid"));
			if(product.getMarket().getId()!= mid){//检查每个商品是否在同一商场购买
				throw new ControllerException(ExceptionMessage.PRODUCT_NOT_IN_THE_SAME_MARKET,400);
			}
			System.out.println();
			//创建订单
			Order o = new Order();
			double cost = product.getPrice()*order.get("quantity");
			o.setCost(cost);
			o.setProduct(product);
			o.setQuantity(order.get("quantity"));
			o.setRecord(record);
			
			record.addOrder(o);
			total+=cost;
		}
		try {
			PayResponse payresponse = BankServerProxy.getPayResponse(marketService.find(Market.class, mid).getEncryptPrivateKey(),total, marketService.find(Market.class, mid).getCardnum());
			if(!KeyManager.verify(KeyManager.getBankPublickey(), payresponse.getSource(), payresponse.getSign())){
				throw new ControllerException(ExceptionMessage.PAYRESPONSE_SIGN_ERROR,500);
			}
			Map<String,Object> source = Maps.newHashMap();
			source = mapper.readValue(payresponse.getSource(), source.getClass());
			record.setTotal(total);
			record.setTransId((Integer) source.get("tranId"));
			recordService.create(record);
			return payresponse;
		} catch (BankProxyServerException e) {
			e.printStackTrace();
			throw new ControllerException(ExceptionMessage.PAYREQUEST_BANK_ERROR,400);
		}
	}
	
	@RequestMapping(value = "/client/PayRequest", method = RequestMethod.POST)
	public @ResponseBody Object payRequest(@RequestBody PayRequestSign payRequestSign,Principal principal,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		//检查请求是否合法
		byte[] marketPrivateKey = PrivateKeyEncryptor.decrypt(
					marketService.find(Market.class, payRequestSign.getMid()).getEncryptPrivateKey());
		String message = KeyManager.decryptByRSAInString(marketPrivateKey, payRequestSign.getEncryptOI());
		Map<String,Object> source = Maps.newHashMap();
		source = mapper.readValue(message, source.getClass());
		int tranId = (Integer) source.get("tranId");
		if(!recordService.checkTransactionEffective(tranId,clientService.getClientByAccount(principal.getName()).getId())){
			throw new ControllerException(ExceptionMessage.TRANSACTION_ID_NOT_CORRECT,400);
		}
		
		//把请求交给银行
		
		return response;
		
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
	
	@ExceptionHandler(ControllerException.class)
	public @ResponseBody Map<String, String> handleControllerException(ControllerException exception,HttpServletResponse response){
		response.setStatus(exception.getHttpStatusCode());
		Map<String, String> failureMessages = new HashMap<String, String>();
		failureMessages.put("status", "false");
		failureMessages.put("error", exception.getMessage());
		return failureMessages;
	}

}

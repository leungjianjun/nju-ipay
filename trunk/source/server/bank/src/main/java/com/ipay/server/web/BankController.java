package com.ipay.server.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.ipay.server.entity.CreditCard;
import com.ipay.server.entity.Transaction;
import com.ipay.server.security.ExceptionMessage;
import com.ipay.server.security.KeyManager;
import com.ipay.server.security.TransactionException;
import com.ipay.server.security.model.PayRequest;
import com.ipay.server.security.model.PayRequestSign;
import com.ipay.server.security.model.PayResponse;
import com.ipay.server.service.ICreditCardService;
import com.ipay.server.service.ITransactionService;
import com.ipay.server.service.ServiceException;

@Controller
public class BankController {
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private static final Logger logger = LoggerFactory.getLogger(BankController.class);
	
	private ICreditCardService<CreditCard> creditCardService;
	
	private ITransactionService<Transaction> transactionService;
	
	public ITransactionService<Transaction> getTransactionService() {
		return transactionService;
	}

	@Autowired
	public void setTransactionService(
			ITransactionService<Transaction> transactionService) {
		this.transactionService = transactionService;
	}

	public ICreditCardService<CreditCard> getCreditCardService() {
		return creditCardService;
	}

	@Autowired
	public void setCreditCardService(ICreditCardService<CreditCard> creditCardService) {
		this.creditCardService = creditCardService;
	}


	@RequestMapping(value = "/bank/getPayRequest", method = RequestMethod.POST)
	public @ResponseBody PayResponse getPayRequest(@RequestBody PayRequest payRequest,HttpServletResponse response) throws TransactionException{
		byte[] data = KeyManager.decryptByRSA(KeyManager.getBankPrivatekey(), payRequest.getEncryptData());
		try {
			Map<String,Object> contents = Maps.newHashMap(); 
			contents = mapper.readValue(data,0,data.length, contents.getClass());
			String cardnum = (String) contents.get("cardnum");
			double total = (Double) contents.get("total");
			logger.info("pay request "+total +" "+cardnum);
			CreditCard creditCard = creditCardService.getCreditCardByNum(cardnum);
			if(!KeyManager.verify(creditCard.getPublicKey(), payRequest.getEncryptData(), payRequest.getSignData())){
				throw new TransactionException(ExceptionMessage.MESSAGE_VERIFY_ERROR,400);
			}else{
				Transaction transaction = new Transaction();
				transaction.setAmount(total);
				transaction.setCreateDate(new Date());
				transaction.setEffective(false);
				transaction.setFee(0);
				transaction.setPayeeCard(creditCardService.getCreditCardByNum(cardnum));
				transactionService.create(transaction);
				PayResponse payResponse = new PayResponse();
				String source = "{\"tranId\":"+transaction.getId()+",\"amount\":"+total+"}";
				payResponse.setSource(source);
				payResponse.setSign(KeyManager.sign(KeyManager.getBankPrivatekey(), source));
				return payResponse;
			}
		} catch (UnsupportedEncodingException e) {
			throw new TransactionException(ExceptionMessage.UNSUPORT_ENCODING_TYPE,400);
		} catch (JsonParseException e) {
			throw new TransactionException(ExceptionMessage.DATA_FORMAT_ERROR,400);
		} catch (JsonMappingException e) {
			throw new TransactionException(ExceptionMessage.DATA_FORMAT_ERROR,400);
		} catch (IOException e) {
			throw new TransactionException(ExceptionMessage.SERVER_INTERNAL_ERROR,500);
		}
	}
	
	@RequestMapping(value = "/bank/getPayRequestSign", method = RequestMethod.POST)
	public @ResponseBody PayResponse getPayRequestSign(@RequestBody PayRequestSign payRequestSign,HttpServletResponse response) throws TransactionException{
		PayResponse payResponse = new PayResponse();
		String message = KeyManager.decryptByRSAInString(KeyManager.getBankPrivatekey(), payRequestSign.getEncryptPI());
		Map<String,Object> contents = Maps.newHashMap();
		try {
			contents = mapper.readValue(message, contents.getClass());
			int tranId = (Integer) contents.get("tranId");
			//验证请求是否合法
			if(!transactionService.checkTransactionEffectivable(tranId)){
				throw new TransactionException(ExceptionMessage.DATA_FORMAT_ERROR,400);
			}
			//检查余额
			Transaction transaction = transactionService.find(Transaction.class, tranId);
			CreditCard payer = creditCardService.getCreditCardByNum((String) contents.get("cardnum"));
			if(payer.getBalance()<transaction.getAmount()+transaction.getFee()){
				String source = "{\"tranId\":"+transaction.getId()+",\"statusCode\":"+ExceptionMessage.BANLANCE_NOT_ENOUGH_CODE+"}";
				payResponse.setSource(source);
				byte[] sign = KeyManager.sign(KeyManager.getBankPrivatekey(), source);
				payResponse.setSign(sign);
				return payResponse;
			}else{
				transaction.setPayerCard(payer);
				
				String source = "{\"tranId\":"+transaction.getId()+",\"statusCode\":0}";
				payResponse.setSource(source);
				byte[] sign = KeyManager.sign(KeyManager.getBankPrivatekey(), source);
				payResponse.setSign(sign);
				return payResponse;
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
			throw new TransactionException(ExceptionMessage.DATA_FORMAT_ERROR,400);
		} catch (JsonMappingException e) {
			e.printStackTrace();
			throw new TransactionException(ExceptionMessage.DATA_FORMAT_ERROR,400);
		} catch (IOException e) {
			e.printStackTrace();
			throw new TransactionException(ExceptionMessage.DATA_FORMAT_ERROR,400);
		}
	}
	
	public static HttpHeaders httpHeaderPrivateKeyAttachment(final String fileName,final int fileSize) {
	    String encodedFileName = fileName.replace('"', ' ').replace(' ', '_');
	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    responseHeaders.setContentLength(fileSize);
	    responseHeaders.set("Content-Disposition", "attachment");
	    responseHeaders.add("Content-Disposition", "filename=\"" + encodedFileName + '\"');
	    return responseHeaders;
	}
	
	@ExceptionHandler(TransactionException.class)
	public @ResponseBody Map<String, String> handleServiceException(TransactionException exception,HttpServletResponse response){
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

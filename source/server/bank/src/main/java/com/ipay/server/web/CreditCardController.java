package com.ipay.server.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipay.server.entity.CreditCard;
import com.ipay.server.service.ICreditCardService;
import com.ipay.server.service.ServiceException;

@Controller
public class CreditCardController {
	
	private static final Logger logger = LoggerFactory.getLogger(CreditCardController.class);
	
	private ICreditCardService<CreditCard> creditCardService;
	
	@RequestMapping(value = "/bank/getEncryptPrivateKey", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getEncryptPrivateKey(@RequestParam String cardnum){
		CreditCard creditCard = creditCardService.getCreditCardByNum(cardnum);
		HttpHeaders httpHeaders = httpHeaderPrivateKeyAttachment("private.key",656);
		return new ResponseEntity<byte[]>(creditCard.getPrivateKey(),httpHeaders,HttpStatus.OK);
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

	public ICreditCardService<CreditCard> getCreditCardService() {
		return creditCardService;
	}

	@Autowired
	public void setCreditCardService(ICreditCardService<CreditCard> creditCardService) {
		this.creditCardService = creditCardService;
	}

}

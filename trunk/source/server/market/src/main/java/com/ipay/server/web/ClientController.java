package com.ipay.server.web;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.ipay.server.bankproxy.BankServerProxy;
import com.ipay.server.entity.Client;
import com.ipay.server.service.IClientService;
import com.ipay.server.service.ServiceException;

@Controller
public class ClientController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	private IClientService<Client> clientService;

	public IClientService<Client> getClientService() {
		return clientService;
	}

	@Autowired
	public void setClientService(IClientService<Client> clientService) {
		this.clientService = clientService;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("client login "+ locale.toString());
		return "login_test";
	}
	
	@RequestMapping(value = "client/test", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> test(Locale locale,Principal principal) {
		logger.info("client login "+ locale.toString());
		return Collections.singletonMap("name", principal.getName());
	}
	
	@RequestMapping(value = "/client/GetInfo", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> getClientInfo(Principal principal) {
		logger.info("client get personal info "+principal.getName());
		Map<String,String> result = Maps.newHashMap();
		Client client = clientService.getClientByAccount(principal.getName());
		result.put("account", client.getAccount());
		result.put("realname", client.getClientInfo().getRealname());
		result.put("phonenum", client.getClientInfo().getPhonenum());
		return result;
	}
	
	@RequestMapping(value = "/client/SetInfo", method = RequestMethod.POST)
	public @ResponseBody Object setClientInfo(@RequestBody Map<String, String> param,Principal principal) {
		Client client = clientService.getClientByAccount(principal.getName());
		if(param.containsKey("account")){
			client.setAccount(param.get("account"));
		}
		if(param.containsKey("realname")){
			client.getClientInfo().setRealname("realname");
		}
		if(param.containsKey("phonenum")){
			client.getClientInfo().setPhonenum("phonenum");
		}
		//欠缺数据检验，数据校验应该在客户端，防御式编程
		
		clientService.update(client);
		return Collections.singletonMap("status", true);
	}
	
	@RequestMapping(value = "/client/getEncryptPrivateKey", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getEncryptPrivateKey(Principal principal){
		String cardnum = clientService.getClientByAccount(principal.getName()).getCardnum();
		byte[] encryptPrivateKey = BankServerProxy.getEncryptPrivakeKey(cardnum);
		HttpHeaders httpHeaders = httpHeaderPrivateKeyAttachment("private.key",656);
		return new ResponseEntity<byte[]>(encryptPrivateKey,httpHeaders,HttpStatus.OK);
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
}

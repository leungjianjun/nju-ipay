package com.ipay.server.web;

import java.security.Principal;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipay.server.entity.Client;
import com.ipay.server.service.IClientService;

@Controller
public class ClientController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
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
	
	@RequestMapping(value = "/client/getEncryptPrivateKey", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getEncryptPrivateKey(){
		byte[] encryptPrivateKey = new byte[656];
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

}

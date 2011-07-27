package com.ipay.server.web;

import java.security.Principal;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> test(Locale locale,Principal principal) {
		logger.info("client login "+ locale.toString());
		return Collections.singletonMap("name", principal.getName());
	}

}

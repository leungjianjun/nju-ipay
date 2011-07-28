package com.ipay.server.web;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipay.server.entity.Market;
import com.ipay.server.service.IMarketService;

@Controller
public class MarketController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private IMarketService<Market> marketService;

	public IMarketService<Market> getMarketService() {
		return marketService;
	}

	@Autowired
	public void setMarketService(IMarketService<Market> marketService) {
		this.marketService = marketService;
	}
	
	@RequestMapping(value="/client/search",method=RequestMethod.GET)
	public @ResponseBody Object clientLogout(HttpSession session){
		session.invalidate();
		return Collections.singletonMap("status", true);
	}

}

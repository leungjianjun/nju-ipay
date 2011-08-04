package com.ipay.server.web;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipay.server.entity.Market;
import com.ipay.server.service.IMarketService;

@Controller
public class MarketController {
	
	private static final Logger logger = LoggerFactory.getLogger(MarketController.class);
	
	private IMarketService<Market> marketService;

	public IMarketService<Market> getMarketService() {
		return marketService;
	}

	@Autowired
	public void setMarketService(IMarketService<Market> marketService) {
		this.marketService = marketService;
	}
	
	/**
	 * 搜索商场，每页显示10条
	 * 
	 * @param name
	 * 		商场名的部分文字
	 * @param page
	 * 		页数，默认从1开始
	 * @return
	 */
	@RequestMapping(value="/client/searchMarket",method=RequestMethod.GET)
	public @ResponseBody Object searchMarket(@RequestParam String name,@RequestParam String page){
		
		return Collections.singletonMap("status", true);
	}

}

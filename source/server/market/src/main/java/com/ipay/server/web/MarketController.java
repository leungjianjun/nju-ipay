package com.ipay.server.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
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

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ipay.server.entity.Market;
import com.ipay.server.entity.SpecialProduct;
import com.ipay.server.service.IMarketService;
import com.ipay.server.service.ServiceException;

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
	 * markets:[{id:123,name:"苏果超市",location:"南京仙林大道。。。"},
	 * @param name
	 * 		商场名的部分文字
	 * @param page
	 * 		页数，默认从1开始
	 * @return
	 */
	@RequestMapping(value="/client/searchMarket",method=RequestMethod.GET)
	public @ResponseBody Object searchMarket(@RequestParam String name,@RequestParam int page){
		List<Market> markets = marketService.findMarketsByName(name, page);
		Set<Map<String,Object>> contents = Sets.newHashSet();
		for(Market market:markets){
			Map<String,Object> temp = Maps.newHashMap();
			temp.put("id", market.getId());
			temp.put("name", market.getMarketInfo().getName());
			temp.put("location", market.getMarketInfo().getLocation());
			contents.add(temp);
		}
		return Collections.singletonMap("markets", contents);
	}
	
	@RequestMapping(value="/client/findMarketId",method=RequestMethod.GET)
	public @ResponseBody Object findMarketByIp(HttpServletRequest request){
		Market market = marketService.finMarketByIp(request.getRemoteAddr());
		Map<String,Object> result = Maps.newHashMap();
		result.put("ip", market.getIp());
		result.put("id", market.getId());
		return result;
	}
	
	/**
	 * 获取商场的详细信息
	 * 
	 * @param mid
	 * @return
	 */
	@RequestMapping(value="/client/MarketInfo",method=RequestMethod.GET)
	public @ResponseBody Object getMarketInfo(@RequestParam int mid){
		Market market = marketService.find(Market.class, mid);
		Map<String,Object> result = Maps.newHashMap();
		result.put("name", market.getMarketInfo().getName());
		result.put("location", market.getMarketInfo().getLocation());
		result.put("introduction", market.getMarketInfo().getIntroduction());
		result.put("servicePhone", market.getMarketInfo().getServicePhone());
		result.put("complainPhone", market.getMarketInfo().getComplainPhone());
		result.put("createDate", market.getMarketInfo().getCreateDate());
		return result;
	}
	
	/**
	 * 获取特价商品
	 * 
	 * specialsProducts:[{name:"",oldPrice:12.5,newPrice:8.5,adWords:"",pid:123,minImgUrl:""},
	 * @param mid
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/client/MarketSpecialProducs",method=RequestMethod.GET)
	public @ResponseBody Object getMarketSpecialProducs(@RequestParam int mid,@RequestParam int page){
		List<SpecialProduct> sps = marketService.getSpecialProduct(mid, page);
		Set<Map<String,Object>> contents = Sets.newHashSet();
		for(SpecialProduct sp:sps){
			Map<String,Object> temp = Maps.newHashMap();
			temp.put("name", sp.getProduct().getProductInfo().getName());
			temp.put("oldPrice", sp.getOldprice());
			temp.put("newPrice", sp.getProduct().getPrice());
			temp.put("adWords", sp.getIntroduction());
			temp.put("pid", sp.getProduct().getId());
			temp.put("minImgUrl", sp.getProduct().getProductInfo().getMinImgUrl());
			contents.add(temp);
		}
		return Collections.singletonMap("specialsProducts", contents);
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

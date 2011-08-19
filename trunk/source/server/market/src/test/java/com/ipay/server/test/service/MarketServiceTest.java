package com.ipay.server.test.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ipay.server.entity.Market;
import com.ipay.server.entity.MarketInfo;
import com.ipay.server.entity.User;
import com.ipay.server.security.PasswordEncoder;
import com.ipay.server.service.IMarketService;
import com.ipay.server.service.IUserService;
import com.ipay.server.util.SpringJUnit45ClassRunner;

@RunWith(SpringJUnit45ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/root-context.xml","classpath:spring/appServlet/servlet-context.xml"})
public class MarketServiceTest {
	
	private IMarketService<Market> marketService;
	
	private IUserService<User> userService;

	public IUserService<User> getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(IUserService<User> userService) {
		this.userService = userService;
	}

	public IMarketService<Market> getMarketService() {
		return marketService;
	}

	@Autowired
	public void setMarketService(IMarketService<Market> marketService) {
		this.marketService = marketService;
	}
	
	@Test
	public void testCreate(){
		Market market = new Market();
		market.setAccount("junge");
		market.getAuthorityList().add(userService.getAuthority("market"));
		market.setCardnum("7387748847372");
		market.setIp("202.119.48.38");
		market.setPassword(PasswordEncoder.encode("market", market.getAccount()));
		MarketInfo marketInfo = new MarketInfo();
		marketInfo.setComplainPhone("85409161");
		marketInfo.setIntroduction("欢迎来到教育超市，教育超市是高校中最大的连锁超市");//这里写多一点，真是一点，找网上的
		marketInfo.setLocation("仙林教学楼");
		marketInfo.setName("南京大学仙林校区教育超市");
		marketInfo.setServicePhone("854091232");
		marketInfo.setCreateDate(new Date());
		market.setMarketInfo(marketInfo);
		marketService.create(market);

		Market market2 = new Market();
		market2.setAccount("skywalker");
		market2.getAuthorityList().add(userService.getAuthority("market"));
		market2.setCardnum("7387748847372");
		market2.setIp("202.119.48.38");
		market2.setPassword(PasswordEncoder.encode("market", market.getAccount()));
		MarketInfo marketInfo2 = new MarketInfo();
		marketInfo2.setComplainPhone("85409161");
		marketInfo2.setIntroduction("欢迎来到教育超市，教育超市是高校中最大的连锁超市");//这里写多一点，真是一点，找网上的
		marketInfo2.setLocation("仙林教学楼");
		marketInfo2.setName("南京大学鼓楼校区教育超市");
		marketInfo2.setServicePhone("854091232");
		marketInfo2.setCreateDate(new Date());
		market2.setMarketInfo(marketInfo2);
		marketService.create(market2);
		
		Market market3 = new Market();
		market3.setAccount("xiong");
		market3.getAuthorityList().add(userService.getAuthority("market"));
		market3.setCardnum("7387748847372");
		market3.setIp("202.119.48.38");
		market3.setPassword(PasswordEncoder.encode("market", market.getAccount()));
		MarketInfo marketInfo3 = new MarketInfo();
		marketInfo3.setComplainPhone("85409161");
		marketInfo3.setIntroduction("欢迎来到教育超市，教育超市是高校中最大的连锁超市");//这里写多一点，真是一点，找网上的
		marketInfo3.setLocation("仙林教学楼");
		marketInfo3.setName("东南大学教育超市");
		marketInfo3.setServicePhone("854091232");
		marketInfo3.setCreateDate(new Date());
		market3.setMarketInfo(marketInfo3);
		marketService.create(market3);

	}
	
	@Test
	public void testFindMarketsByName(){
		List<Market> markets = marketService.findMarketsByName("超", 1);
		for(Market market:markets){
			System.out.println(market.getMarketInfo().getName());
		}
	}
	

}

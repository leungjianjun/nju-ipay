package com.ipay.server.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import com.ipay.server.entity.Market;
import com.ipay.server.entity.MarketInfo;
import com.ipay.server.entity.User;
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
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		Market market = new Market();
		market.setAccount("jycs");
		market.getAuthorityList().add(userService.getAuthority("market"));
		market.setCardnum("7387748847372");
		market.setIp("202.119.48.38");
		market.setPassword(md5.encodePassword("market", market.getAccount()));
		MarketInfo marketInfo = new MarketInfo();
		marketInfo.setComplainPhone("85409161");
		marketInfo.setIntroduction("欢迎来到教育超市，教育超市是高校中最大的连锁超市");//这里写多一点，真是一点，找网上的
		marketInfo.setLocation("仙林教学楼");
		marketInfo.setName("南京大学仙林校区教育超市");
		marketInfo.setServicePhone("854091232");
		market.setMarketInfo(marketInfo);
		marketService.create(market);
	}
	

}

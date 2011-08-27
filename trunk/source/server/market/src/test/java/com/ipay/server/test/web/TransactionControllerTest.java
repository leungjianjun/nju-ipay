package com.ipay.server.test.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ipay.server.util.ControllerTest;
import com.ipay.server.util.PrincipalImpl;
import com.ipay.server.web.TransactionController;

public class TransactionControllerTest extends ControllerTest<TransactionController>{

	@Override
	protected String getBeanName() {
		return "transactionController";
	}
	
	@Test
	public void testSendOrder(){
		Map<String, Object> param = Maps.newHashMap();
		param.put("mid", 2);
		List<Map<String,Integer>> orders = Lists.newArrayList();
		
		Map<String,Integer> order1 = Maps.newHashMap();
		order1.put("pid", 1);
		order1.put("quantity", 2);
		orders.add(order1);
		param.put("orders", orders);
		Principal principal = new PrincipalImpl("ljj");
		controller.sendOrder(param,principal,response);
		
	}
	
	

}

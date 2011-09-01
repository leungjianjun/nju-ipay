package com.ipay.server.test.web;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ipay.server.bankproxy.PayRequestSign;
import com.ipay.server.bankproxy.PayResponse;
import com.ipay.server.bankproxy.PayResponseAdapter;
import com.ipay.server.security.KeyManager;
import com.ipay.server.util.ControllerTest;
import com.ipay.server.util.PrincipalImpl;
import com.ipay.server.web.TransactionController;

public class TransactionControllerTest extends ControllerTest<TransactionController>{

	@Override
	protected String getBeanName() {
		return "transactionController";
	}
	
	@Test
	public void testSendOrder() throws JsonParseException, JsonMappingException, IOException{
		Map<String, Object> param = Maps.newHashMap();
		param.put("mid", 2);
		List<Map<String,Integer>> orders = Lists.newArrayList();
		
		Map<String,Integer> order1 = Maps.newHashMap();
		order1.put("pid", 1);
		order1.put("quantity", 2);
		orders.add(order1);
		param.put("orders", orders);
		Principal principal = new PrincipalImpl("ljj");
		PayResponseAdapter payResponse = (PayResponseAdapter) controller.sendOrder(param,principal,response);
		if(!KeyManager.verify(KeyManager.getBankPublickey(), payResponse.getSource(), payResponse.getSign())){
			//验证错误
			System.out.println("验证错误");
		}//1.验证数据是否来源银行
		String source = payResponse.getSource();
		Map<String,Object> contents = Maps.newHashMap();//2.把数据转换成json
		ObjectMapper mapper = new ObjectMapper();
		contents = mapper.readValue(source,contents.getClass());
		System.out.println(contents.get("tranId")+" "+contents.get("amount"));
		
		String OI = "{\"tranId\":"+contents.get("tranId")+"}";//商场看的
		String PI = "{\"tranId\":"+contents.get("tranId")+"}";//银行看的
		byte[] marketPublickey = null;
		byte[] encryptOI = KeyManager.encryptByRSA(marketPublickey, OI);
		byte[] encryptPI = KeyManager.encryptByRSA(KeyManager.getBankPublickey(), PI);
		
		byte[] encryptPrivateKey = null;
		String rawPass = "";
		String salt = "";
		byte[] OIMD = KeyManager.sign(KeyManager.decryptPrivatekey(encryptPrivateKey, rawPass, salt), encryptOI);
		byte[] PIMD = KeyManager.sign(KeyManager.decryptPrivatekey(encryptPrivateKey, rawPass, salt), encryptPI);
		PayRequestSign payRequestSign = new PayRequestSign();
		//payRequestSign.setEncryptOI(encryptOI);
		//payRequestSign.setEncryptPI(encryptPI);
		//payRequestSign.setOIMD(OIMD);
		//payRequestSign.setPIMD(PIMD);
		
	}
	
	

}

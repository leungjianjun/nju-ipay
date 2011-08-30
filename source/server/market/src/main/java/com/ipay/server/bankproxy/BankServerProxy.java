package com.ipay.server.bankproxy;

import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Maps;
import com.ipay.server.security.KeyManager;
import com.ipay.server.security.PrivateKeyEncryptor;

public class BankServerProxy {
	
	public static byte[] getEncryptPrivakeKey(String cardnum){
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("cardnum", cardnum);
		try {
			return HttpConnection.doGet(Configure.GetPrivateKey(), paramMap );
		} catch (IOException e) {
			return null;
		}
	}
	
	public static byte[] getPublcKey(String cardnum){
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("cardnum", cardnum);
		try {
			return HttpConnection.doGet(Configure.GetPublicKey(), paramMap );
		} catch (IOException e) {
			return null;
		}
	}
	
	public static PayResponse getPayResponse(byte[] encryptPrivateKeyBytes, double total,String cardnum) throws BankProxyServerException{
		final String message = "{\"total\":"+total+",\"cardnum\":\""+cardnum+"\"}";
		PayRequest payRequest = new PayRequest();
		payRequest.setEncryptData(KeyManager.encryptByRSA(KeyManager.getBankPublickey(), message.getBytes()));
		payRequest.setSignData(KeyManager.sign(PrivateKeyEncryptor.decrypt(encryptPrivateKeyBytes), payRequest.getEncryptData()));
		return HttpConnection.doJsonPost(Configure.PayRequest(), payRequest);
	}

}

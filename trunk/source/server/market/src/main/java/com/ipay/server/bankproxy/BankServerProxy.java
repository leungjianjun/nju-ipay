package com.ipay.server.bankproxy;

import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Maps;

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

}

package com.ipay.server.bankproxy;

public class Configure {
	
	public static final int PORT = 8090;
	
	public static final String DOMAIN = "localhost";
	
	public static final String GETENCRYPTKE = "/bank/getEncryptPrivateKey";
	
	public static String BasicWebsite(){
		return DOMAIN+":"+PORT;
	}
	
	public static String GetPrivateKey(){
		return "http://"+BasicWebsite()+GETENCRYPTKE;
	}

}

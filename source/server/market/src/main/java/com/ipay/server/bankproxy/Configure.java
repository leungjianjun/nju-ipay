package com.ipay.server.bankproxy;

public class Configure {
	
	public static final int PORT = 8090;
	
	public static final String DOMAIN = "localhost";
	
	public static final String GETENCRYPTKEY = "/bank/getEncryptPrivateKey";
	
	public static final String 	GETPUBLICKEY = "/bank/getPublicKey";

	public static final String PAYREQUEST = "/bank/getPayRequest";
	
	public static final String GETBANKPUBLICKEY = "/bank/getBankPublicKey";
	
	public static String BasicWebsite(){
		return DOMAIN+":"+PORT;
	}
	
	public static String GetPrivateKey(){
		return "http://"+BasicWebsite()+GETENCRYPTKEY;
	}
	
	public static String GetPublicKey(){
		return "http://"+BasicWebsite()+GETPUBLICKEY;
	}
	
	public static String PayRequest(){
		return "http://"+BasicWebsite()+PAYREQUEST;
	}
	
	public static String BankPublicKey(){
		return "http://"+BasicWebsite()+GETBANKPUBLICKEY;
	}

}

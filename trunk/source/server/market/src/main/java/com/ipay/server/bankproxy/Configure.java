package com.ipay.server.bankproxy;

public class Configure {
	
	public static final int PORT = 8090;
	
	public static final String DOMAIN = "localhost";
	
	public static String getBasicwebsite(){
		return DOMAIN+":"+PORT;
	}

}

package com.ipay.server.security;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public final class PasswordEncoder {
	
	private static ShaPasswordEncoder sha = new ShaPasswordEncoder();
	
	private PasswordEncoder(){}
	
	public static String encode(String rawPass,String salt){
		return sha.encodePassword(rawPass, salt);
	}

}

package com.ipay.server.security;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public final class PasswordEncoder {
	
	private static ShaPasswordEncoder sha = new ShaPasswordEncoder();
	
	private PasswordEncoder(){}
	
	/**
	 * 加密密码
	 * 
	 * @param rawPass
	 * 			原始密码
	 * @param salt
	 * 			盐值
	 * @return
	 */
	public static String encode(String rawPass,String salt){
		return sha.encodePassword(rawPass, salt);
	}

}

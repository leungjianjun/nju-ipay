package com.ipay.server.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class EncodePassword {
	
	public static void main(String args[]){
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		System.out.println(md5.encodePassword("admin", "admin"));
	}
	
	

}

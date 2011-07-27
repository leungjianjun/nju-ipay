package com.ipay.server.util;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class EncodePassword {
	
	public static void main(String args[]){
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		System.out.println(sha.encodePassword("admin", "admin"));
	}
	
	

}

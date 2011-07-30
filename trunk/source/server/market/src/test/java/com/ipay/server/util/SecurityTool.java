package com.ipay.server.util;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class SecurityTool {
	
	public static void main(String args[]){
		
	}
	
	public static void encodePassword(){
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		System.out.println(sha.encodePassword("admin", "admin"));
	}
	
	public static void RSAexample() throws NoSuchAlgorithmException{
		KeyPairGenerator.getInstance("RSA");
	}
	
}

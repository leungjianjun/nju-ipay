package com.ipay.server.util;

import java.security.Principal;

public class PrincipalImpl implements Principal {
	
	private String name;

	public PrincipalImpl(String name){
		this.name = name;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}

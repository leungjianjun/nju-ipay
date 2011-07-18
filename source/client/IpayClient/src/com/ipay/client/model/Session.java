package com.ipay.client.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Session {
	private String username;
	private String passwordMD5;
	private HashMap<Product, Integer> shoppingCart;
	
	//if the product is in the shopping cart already,
	//then just change the amount
	public void put(Product product, int amount){
		
	}
	
	public void remove(Product product){
		
	}
}

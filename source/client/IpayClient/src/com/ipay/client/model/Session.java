package com.ipay.client.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Session {
	private String username;
	private String passwordMD5;
	private HashMap<Product, Integer> shoppingCart;
	public HashMap<Product, Integer> getShoppingCart() {
		return shoppingCart;
	}
	public Session(){
		username = null;
		passwordMD5 = null;
		shoppingCart = new HashMap<Product, Integer>();
	}
	public Session(String username, String passwordMD5) {
		this.username = username;
		this.passwordMD5 = passwordMD5;
		shoppingCart = new HashMap<Product, Integer>();
	}

	//if the product is in the shopping cart already,
	//then just change the amount
	public void put(Product product, int amount){
		shoppingCart.put(product, amount);
	}
	
	public void remove(Product product){
		shoppingCart.remove(product);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordMD5() {
		return passwordMD5;
	}

	public void setPasswordMD5(String passwordMD5) {
		this.passwordMD5 = passwordMD5;
	}
	
}

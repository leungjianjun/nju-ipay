package com.ipay.client.model;

import java.util.HashMap;

/**
 * 维护用户名，密码以及购物车心信息
 * @author tianming
 *
 */
public class Session {
	private String username;
	private String password;
	private HashMap<Product, Integer> shoppingCart;
	public HashMap<Product, Integer> getShoppingCart() {
		return shoppingCart;
	}
	public Session(){
		username = null;
		password = null;
		shoppingCart = new HashMap<Product, Integer>();
	}
	public Session(String username, String password) {
		this.username = username;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String passwordMD5) {
		this.password = passwordMD5;
	}
	
}

package com.ipay.client.model;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Order {
	private String dateStr;
	private HashMap<Product, Integer> products;
	public Order(HashMap<Product, Integer> products){
		this.products = products;
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		dateStr = date.toLocaleString();
	}
	public String getDateStr() {
		return dateStr;
	}
	public HashMap<Product, Integer> getProducts() {
		return products;
	}
	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
}

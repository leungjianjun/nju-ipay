package com.ipay.client.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Order {
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getProductId() {
		return productId;
	}
	private int productId;
	private String productName;
	private int quantity;
	private double cost;
	public static Order parseJSONObject(JSONObject object){
	Order order = new Order();
	try {
		order.setProductId(object.getInt("prodectId"));
		order.setProductName(object.getString("productName"));
		order.setQuantity(object.getInt("quantity"));
		order.setCost(object.getDouble("cost"));
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return order;
}
}



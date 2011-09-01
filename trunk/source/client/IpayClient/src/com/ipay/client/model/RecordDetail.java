package com.ipay.client.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecordDetail {
	private String createDate;
	private int marketId;
	private String marketName;
	private double total;
	private ArrayList<Order> orders;
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public int getMarketId() {
		return marketId;
	}
	public void setMarketId(int marketId) {
		this.marketId = marketId;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public ArrayList<Order> getOrders() {
		return orders;
	}
	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	public static RecordDetail parseJSONObect(JSONObject object){
		RecordDetail detail = new RecordDetail();
		try{
			detail.setCreateDate(object.getString("createDate"));
		detail.setMarketId(object.getInt("marketId"));
		detail.setMarketName(object.getString("marketName"));
		detail.setTotal(object.getDouble("total"));
		ArrayList<Order> orders = new ArrayList<Order>();
		JSONArray jArray = object.getJSONArray("orders");
		for(int i = 0; i < jArray.length(); i++){
			Order order = Order.parseJSONObject(jArray.getJSONObject(i));
			orders.add(order);
		}
		}catch (JSONException e) {
			e.printStackTrace();
		}
		
		return detail;
	}
}

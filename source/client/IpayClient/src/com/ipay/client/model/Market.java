package com.ipay.client.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Market {
	private int id;
	private String name;
	private String location;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public static Market parseJSONObect(JSONObject object) throws JSONException{
		Market market = new Market();
		market.setId(object.getInt("id"));
		market.setName(object.getString("name"));
		market.setLocation(object.getString("location"));
		return market;
	}
}

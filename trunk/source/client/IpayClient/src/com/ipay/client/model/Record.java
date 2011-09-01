package com.ipay.client.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 所有消费清单的本地记录
 * 
 * @author tianming
 * 
 */
public class Record {
	public static final String ID = "id";
	public static final String CREATE_DATE = "createDate";
	public static final String MARKET_NAME = "marketName";
	public static final String TOTAL = "total";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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

	private int id;
	private String createDate;
	private String marketName;
	private double total;

	public static Record parseJSONObect(JSONObject object) {
		Record record = new Record();
		try {
			record.setId(object.getInt("id"));
			record.setCreateDate(object.getString("createData"));
			record.setMarketName(object.getString("marketName"));
			record.setTotal(object.getDouble("total"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return record;
	}
}

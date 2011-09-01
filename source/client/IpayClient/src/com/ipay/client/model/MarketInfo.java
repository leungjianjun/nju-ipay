package com.ipay.client.model;

import org.json.JSONException;
import org.json.JSONObject;

public class MarketInfo {
	public static final String NAME = "name";
	public static final String LOCATION = "location";
	public static final String INTRODUCTION = "introduction";
	public static final String SERVICE_PHONE = "servicePhone";
	public static final String COMPLAIN_PHONE = "complainPhone";
	public static final String CREATE_DATE = "createDate";
	private String name;
	private String location;
	private String introduction;
	private String servicePhone;
	private String complainPhone;
	private String createDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String laction) {
		this.location = laction;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public String getComplainPhone() {
		return complainPhone;
	}

	public void setComplainPhone(String complainPhone) {
		this.complainPhone = complainPhone;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public static MarketInfo parseJSONObject(JSONObject object) {
		MarketInfo marketInfo = new MarketInfo();
		try {
			marketInfo.setName(object.getString("name"));
			marketInfo.setLocation(object.getString("location"));
			marketInfo.setIntroduction(object
					.getString("introduction"));
			marketInfo.setServicePhone(object
					.getString("servicePhone"));
			marketInfo.setComplainPhone(object
					.getString("complainPhone"));
			marketInfo.setCreateDate(object.getString("createDate"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return marketInfo;
	}
}

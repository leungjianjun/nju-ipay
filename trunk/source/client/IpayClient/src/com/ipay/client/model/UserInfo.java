package com.ipay.client.model;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfo {
	public static final String ACCOUNT="account";
	public static final String REAL_NAME="realname";
	public static final String PHONE_NUM="phonenum";
	private String account;
	private String realname;
	private String phone;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public static UserInfo parseJSONObject(JSONObject object) throws JSONException {
		UserInfo info = new UserInfo();
		info.setAccount(object.getString("account"));
		info.setRealname(object.getString("realname"));
		info.setPhone(object.getString("phonenum"));
		return info;
	}
}

package com.ipay.client.model;

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
	
}

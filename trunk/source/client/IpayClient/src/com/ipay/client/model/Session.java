package com.ipay.client.model;

/**
 * 维护用户名，密码以及购物车心信息
 * @author tianming
 *
 */
public class Session {
	private String username;
	private String password;
	public Session(){
		username = null;
		password = null;
	}
	public Session(String username, String password) {
		this.username = username;
		this.password = password;
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

package com.ipay.server.security.model;

public class PayResponse {
	
	private String source;
	
	private byte[] sign;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public byte[] getSign() {
		return sign;
	}

	public void setSign(byte[] sign) {
		this.sign = sign;
	}

}

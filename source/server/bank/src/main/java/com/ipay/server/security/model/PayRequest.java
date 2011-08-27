package com.ipay.server.security.model;

public class PayRequest {
	private byte[] encryptData;
	 
	 private byte[] signData;

	public byte[] getSignData() {
		return signData;
	}

	public void setSignData(byte[] signData) {
		this.signData = signData;
	}

	public byte[] getEncryptData() {
		return encryptData;
	}

	public void setEncryptData(byte[] encryptData) {
		this.encryptData = encryptData;
	}
}

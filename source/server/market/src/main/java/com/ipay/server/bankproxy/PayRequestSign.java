package com.ipay.server.bankproxy;

public class PayRequestSign {
	private int mid;
	private byte[] encryptOI;
	private byte[] encryptPI;
	private byte[] OIMD;
	private byte[] PIMD;
	public byte[] getEncryptOI() {
		return encryptOI;
	}
	public void setEncryptOI(byte[] encryptOI) {
		this.encryptOI = encryptOI;
	}
	public byte[] getEncryptPI() {
		return encryptPI;
	}
	public void setEncryptPI(byte[] encryptPI) {
		this.encryptPI = encryptPI;
	}
	public byte[] getOIMD() {
		return OIMD;
	}
	public void setOIMD(byte[] oIMD) {
		OIMD = oIMD;
	}
	public byte[] getPIMD() {
		return PIMD;
	}
	public void setPIMD(byte[] pIMD) {
		PIMD = pIMD;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}

}

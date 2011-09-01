package com.ipay.server.security.model;

import java.util.Map;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class PayRequestSign {
	private int mid;
	private String encryptOI;
	private String encryptPI;
	private String OIMD;
	private String PIMD;
	public PayRequestSign() {}
	public PayRequestSign(Map<String, Object> param) {
		this.mid = (Integer) param.get("mid");
		this.encryptOI = (String) param.get("encryptOI");
		this.encryptPI = (String) param.get("encryptPI");
		this.OIMD = (String) param.get("OIMD");
		this.PIMD = (String) param.get("PIMD");
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getEncryptOI() {
		return encryptOI;
	}
	public void setEncryptOI(String encryptOI) {
		this.encryptOI = encryptOI;
	}
	public String getEncryptPI() {
		return encryptPI;
	}
	public void setEncryptPI(String encryptPI) {
		this.encryptPI = encryptPI;
	}
	public String getOIMD() {
		return OIMD;
	}
	public void setOIMD(String oIMD) {
		OIMD = oIMD;
	}
	public String getPIMD() {
		return PIMD;
	}
	public void setPIMD(String pIMD) {
		PIMD = pIMD;
	}
	
	public byte[] getEncryptOIBytes(){
		try {
			return Base64.decode(this.encryptOI);
		} catch (Base64DecodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public byte[] getEncryptPIBytes(){
		try {
			return Base64.decode(this.encryptPI);
		} catch (Base64DecodingException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public byte[] getPIMDBytes(){
		try {
			return Base64.decode(this.PIMD);
		} catch (Base64DecodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public byte[] getOIMDBytes(){
		try {
			return Base64.decode(this.OIMD);
		} catch (Base64DecodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}

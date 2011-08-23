package com.ipay.server.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class CreditCard extends BaseEntity {
	
	/**
	 * 信用卡号
	 */
	private String cardnum;
	
	/**
	 * 支付密码,使用sha加密
	 */
	private String paypass;
	
	/**
	 * 信用卡余额,可以为负值
	 */
	private double balance;
	
	/**
	 * 信用卡是否可用
	 */
	private boolean enable;
	
	/**
	 * 使用aes加密的rsa私钥
	 */
	private byte[] privateKey;
	
	/**
	 * rsa公钥
	 */
	private byte[] publicKey;
	
	/**
	 * 信用卡的持有者
	 */
	@OneToOne
	private User user;

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public String getPaypass() {
		return paypass;
	}

	public void setPaypass(String paypass) {
		this.paypass = paypass;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public byte[] getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(byte[] privateKey) {
		this.privateKey = privateKey;
	}

	public byte[] getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(byte[] publicKey) {
		this.publicKey = publicKey;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}

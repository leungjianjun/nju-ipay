package com.ipay.server.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Sets;

/**
 * 该实体包含商店的信息
 * @author ljj
 *
 */
@Entity
public class Market extends User {
	
	/**
	 * 银行卡号
	 */
	@NotNull
	private String cardnum;
	
	/**
	 * 商场ip,备用
	 */
	private String ip;
	
	/**
	 * 商场的详细信息
	 */
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn
	private MarketInfo marketInfo;
	
	@Column(length=700)
	private byte[] encryptPrivateKey;
	
	/**
	 * 商场的商品
	 */
	@OneToMany(mappedBy="market",fetch = FetchType.LAZY)
	private Set<Product> products = Sets.newHashSet();

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public MarketInfo getMarketInfo() {
		return marketInfo;
	}

	public void setMarketInfo(MarketInfo marketInfo) {
		this.marketInfo = marketInfo;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public byte[] getEncryptPrivateKey() {
		return encryptPrivateKey;
	}

	public void setEncryptPrivateKey(byte[] encryptPrivateKey) {
		this.encryptPrivateKey = encryptPrivateKey;
	}

}

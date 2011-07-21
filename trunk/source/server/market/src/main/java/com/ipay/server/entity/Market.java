package com.ipay.server.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.google.common.collect.Sets;

@Entity
public class Market extends User {
	
	private String cardnum;
	
	private String ip;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn
	private MarketInfo marketInfo;
	
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

}

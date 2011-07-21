package com.ipay.server.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Product extends BaseEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Market market;
	
	private double price;
	
	private int quantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private ProductInfo productInfo;

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

}

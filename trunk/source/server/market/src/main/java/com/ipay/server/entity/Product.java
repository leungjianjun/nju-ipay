package com.ipay.server.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 该实体包含与商店有关的商品信息,包括价格,数量等
 * @author ljj
 *
 */
@Entity
public class Product extends BaseEntity {
	
	/**
	 * 所属商店
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Market market;
	
	/**
	 * 价格
	 */
	private double price;
	
	/**
	 * 数量
	 */
	private int quantity;
	
	/**
	 * 所指向的的商品信息
	 */
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

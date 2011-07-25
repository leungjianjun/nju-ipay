package com.ipay.server.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * 该实体包含特价商品的所有信息
 * @author ljj
 *
 */
@Entity
public class SpecialProduct extends BaseEntity{
	
	/**
	 * 特价商品所指向的商品
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Product product;
	
	/**
	 * 介绍特价商品，如：某某公司多少周年纪念，推出怎样的特价商品之类的
	 */
	private String introduction;
	
	/**
	 * 原价格
	 * 注意现在的价格就在product.price里面,不必重复
	 */
	private double oldprice;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public double getOldprice() {
		return oldprice;
	}

	public void setOldprice(double oldprice) {
		this.oldprice = oldprice;
	}

}

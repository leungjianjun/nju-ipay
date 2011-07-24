package com.ipay.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 该实体包含订单的所有信息,注意一次购买记录包括多个订单,这里的订单特指购买清单的上某个商品的记录
 * @author ljj
 *
 */
@Entity(name="client_order")
public class Order extends BaseEntity {
	
	/**
	 * 所属购买记录
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Record record;
	
	/**
	 * 该订单的费用
	 * =商品价格*数量-优惠
	 */
	@Column(name="cost_double")
	private double cost;
	
	/**
	 * 购买该商品的数量
	 */
	@Column(name="quantity_int")
	private int quantity;
	
	/**
	 * 所购买的商品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Product product;

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	

}

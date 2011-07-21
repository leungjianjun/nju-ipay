package com.ipay.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="client_order")
public class Order extends BaseEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Record record;
	
	@Column(name="cost_double")
	private double cost;
	
	@Column(name="quantity_int")
	private int quantity;
	
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

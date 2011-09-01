package com.ipay.server.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.collect.Sets;

/**
 * 该实体包含一次购买记录的信息
 * @author ljj
 *
 */
@Entity
public class Record extends BaseEntity {
	
	private boolean effective;
	
	private int transId;
	
	/**
	 * 客户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Client client;
	
	/**
	 * 购买时间
	 */
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createDate;
	
	/**
	 * 商场
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Market market;
	
	/**
	 * 购买的订单
	 */
	@OneToMany(mappedBy="record",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Order> orders = Sets.newHashSet();
	
	/**
	 * 总费用
	 * =order.cost的总和-优惠
	 */
	private double total;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public boolean isEffective() {
		return effective;
	}

	public void setEffective(boolean effective) {
		this.effective = effective;
	}
	
	public void addOrder(Order order){
		orders.add(order);
	}

	public int getTransId() {
		return transId;
	}

	public void setTransId(int transId) {
		this.transId = transId;
	}

}

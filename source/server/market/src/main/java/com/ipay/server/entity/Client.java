package com.ipay.server.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.google.common.collect.Sets;

/**
 * 该实体包含客户的信息
 * @author ljj
 *
 */
@Entity
public class Client extends User {
	
	/**
	 * 银行卡号
	 */
	private String cardnum;
	
	/**
	 * 支付密码
	 */
	private String paypass;
	
	/**
	 * 客户的详细信息
	 */
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn
	private ClientInfo clientInfo = new ClientInfo();
	
	/**
	 * 顾客的购买记录
	 */
	@OneToMany(mappedBy="client",fetch = FetchType.LAZY)
	private Set<Record> records = Sets.newHashSet();

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	public Set<Record> getRecords() {
		return records;
	}

	public void setRecords(Set<Record> records) {
		this.records = records;
	}

	public String getPaypass() {
		return paypass;
	}

	public void setPaypass(String paypass) {
		this.paypass = paypass;
	}

}

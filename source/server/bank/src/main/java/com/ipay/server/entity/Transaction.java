package com.ipay.server.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Transaction extends BaseEntity {
	
	/**
	 * 支出卡
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private CreditCard payerCard;
	
	/**
	 * 收款卡
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private CreditCard payeeCard;
	
	/**
	 * 转账金额
	 */
	private double amount;
	
	/**
	 * 转账收取的手续费
	 */
	private double fee;
	
	/**
	 * 交易是否有效,如果交易无效,则金额是不会发生转移的
	 */
	private boolean effective;

	public CreditCard getPayerCard() {
		return payerCard;
	}

	public void setPayerCard(CreditCard payerCard) {
		this.payerCard = payerCard;
	}

	public CreditCard getPayeeCard() {
		return payeeCard;
	}

	public void setPayeeCard(CreditCard payeeCard) {
		this.payeeCard = payeeCard;
	}

	public boolean isEffective() {
		return effective;
	}

	public void setEffective(boolean effective) {
		this.effective = effective;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

}

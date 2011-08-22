package com.ipay.server.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Transaction extends BaseEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private CreditCard payerCard;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private CreditCard payeeCard;
	
	private float amount;
	
	private float fee;
	
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

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public boolean isEffective() {
		return effective;
	}

	public void setEffective(boolean effective) {
		this.effective = effective;
	}

}

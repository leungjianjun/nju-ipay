package com.ipay.server.service;

import com.ipay.server.entity.CreditCard;

public interface ICreditCardService<T extends CreditCard> extends IService<T> {
	
	public T getCreditCardByNum(String cardnum);

}

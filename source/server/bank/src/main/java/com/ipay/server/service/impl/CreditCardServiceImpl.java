package com.ipay.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.entity.CreditCard;
import com.ipay.server.service.ICreditCardService;

@Service("creditCardService")
@Transactional
public class CreditCardServiceImpl<T extends CreditCard> extends ServiceImpl<T> implements ICreditCardService<T> {

	@Override
	public void create(T baseBean) {
		dao.persist(baseBean);
		
	}

	@Override
	public void delete(T baseBean) {
		dao.delete(baseBean);
		
	}

	public T getCreditCardByNum(String cardnum) {
		return dao.findUniqueBy("from CreditCard as cc where cc.cardnum =?", cardnum);
	}

}

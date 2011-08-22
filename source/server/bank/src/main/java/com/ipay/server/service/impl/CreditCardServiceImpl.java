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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(T baseBean) {
		// TODO Auto-generated method stub
		
	}

}

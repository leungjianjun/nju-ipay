package com.ipay.server.service;

import com.ipay.server.entity.Transaction;

public interface ITransactionService<T extends Transaction> extends IService<T> {
	
	public boolean checkTransactionEffectivable(int tranId);

}

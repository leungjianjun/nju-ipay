package com.ipay.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.entity.Transaction;
import com.ipay.server.service.ITransactionService;

@Service("transactionService")
@Transactional
public class TransactionServiceImpl<T extends Transaction> extends ServiceImpl<T> implements ITransactionService<T> {

	@Override
	public void create(T baseBean) {
		dao.persist(baseBean);

	}

	@Override
	public void delete(T baseBean) {
		// TODO Auto-generated method stub

	}

	public boolean checkTransactionEffectivable(int tranId) {
		dao.findUniqueBy("from Transaction as tran where tran.id =?", tranId);
		return false;
	}

}

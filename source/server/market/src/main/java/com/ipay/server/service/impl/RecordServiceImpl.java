package com.ipay.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.entity.Record;
import com.ipay.server.service.IRecordService;

@Service("recordService")
@Transactional
public class RecordServiceImpl<T extends Record> extends ServiceImpl<T> implements IRecordService<T> {

	@Override
	public void create(T baseBean) {
		dao.persist(baseBean);

	}

	@Override
	public void delete(T baseBean) {
		// TODO Auto-generated method stub

	}

	public boolean checkTransactionEffective(int tranId,int cid) {
		T record = dao.findUniqueBy("from Record as record where record.transId =? and record.client.id =?", tranId,cid);
		if(record== null){
			return false;
		}else{
			return !record.isEffective();
		}
	}

	public boolean checktransactionDone(int tranId) {
		T record = dao.findUniqueBy("from Record as record where record.transId =?", tranId);
		if(record==null){
			return false;
		}else {
			return record.isEffective();
		}
	}

}

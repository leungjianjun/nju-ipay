package com.ipay.server.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.entity.Record;
import com.ipay.server.security.ExceptionMessage;
import com.ipay.server.service.IRecordService;
import com.ipay.server.service.ServiceException;

@Service("recordService")
@Transactional
public class RecordServiceImpl<T extends Record> extends ServiceImpl<T> implements IRecordService<T> {

	public final int QUANTITY_PER_PAGE = 10;
	
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

	public T getRecordByTranId(int tranId) {
		T record = dao.findUniqueBy("from Record as record where record.transId =?", tranId);
		if(record == null ){
			throw new ServiceException(ExceptionMessage.RESOURCE_NOT_FOUND,HttpServletResponse.SC_BAD_REQUEST);
		}else{
			return record;
		}
	}

	public List<T> getRecords(int client, int page) {
		int firstResult = (page-1)*QUANTITY_PER_PAGE;
		return dao.list("from Record as record where record.client.id =? ordre by record.createDate desc", firstResult, firstResult+QUANTITY_PER_PAGE , client);
	}

}

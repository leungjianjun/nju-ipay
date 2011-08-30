package com.ipay.server.service;

import com.ipay.server.entity.Record;

/**
 * <p>提供购买记录服务接口</p>
 */
public interface IRecordService<T extends Record> extends IService<T> {
	
	public boolean checkTransactionEffective(int tranId,int cid);
	
	public boolean checktransactionDone(int tranId);

}

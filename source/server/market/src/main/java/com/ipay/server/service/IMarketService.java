package com.ipay.server.service;

import java.util.List;
import com.ipay.server.entity.Market;
import com.ipay.server.entity.SpecialProduct;

public interface IMarketService<T extends Market> extends IService<T> {
	
	public void addSpecialProduct(SpecialProduct specialProduct);
	
	public List<T> findMarketsByName(String name,int pageNum);
	
	public T finMarketByIp(String ip);
	
	public List<SpecialProduct> getSpecialProduct(int mid,int page);
	
	public byte[] prepareEncryptPrivatekey(String cardnum,String paypass);
}

package com.ipay.server.service;

import com.ipay.server.entity.Market;
import com.ipay.server.entity.SpecialProduct;

public interface IMarketService<T extends Market> extends IService<T> {
	
	public void addSpecialProduct(SpecialProduct specialProduct);

}

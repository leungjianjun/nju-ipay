package com.ipay.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.dao.IDao;
import com.ipay.server.entity.Market;
import com.ipay.server.entity.SpecialProduct;
import com.ipay.server.service.IMarketService;

@Service("marketService")
@Transactional
public class MarketServiceImpl<T extends Market> extends ServiceImpl<T> implements IMarketService<T> {

	@Autowired
	private IDao<SpecialProduct> specialProductDao; 
	
	@Override
	public void create(T baseBean) {
		dao.persist(baseBean);

	}

	@Override
	public void delete(T baseBean) {
		// TODO Auto-generated method stub

	}

	public void addSpecialProduct(SpecialProduct specialProduct) {
		specialProductDao.persist(specialProduct);
	}

	public IDao<SpecialProduct> getSpecialProductDao() {
		return specialProductDao;
	}

	public void setSpecialProductDao(IDao<SpecialProduct> specialProductDao) {
		this.specialProductDao = specialProductDao;
	}

}

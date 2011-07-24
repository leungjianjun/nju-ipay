package com.ipay.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.entity.Market;
import com.ipay.server.service.IMarketService;

@Service("marketService")
@Transactional
public class MarketServiceImpl<T extends Market> extends ServiceImpl<T> implements IMarketService<T> {

	@Override
	public void create(T baseBean) {
		dao.persist(baseBean);

	}

	@Override
	public void delete(T baseBean) {
		// TODO Auto-generated method stub

	}

}

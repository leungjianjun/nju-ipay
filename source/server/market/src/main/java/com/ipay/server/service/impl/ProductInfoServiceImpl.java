package com.ipay.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.entity.ProductInfo;
import com.ipay.server.service.IProductInfoService;

@Service("productInfoService")
@Transactional
public class ProductInfoServiceImpl<T extends ProductInfo> extends ServiceImpl<T> implements IProductInfoService<T>  {

	@Override
	public void create(T baseBean) {
		dao.persist(baseBean);
	}

	@Override
	public void delete(T baseBean) {
		// TODO Auto-generated method stub
		
	}

}

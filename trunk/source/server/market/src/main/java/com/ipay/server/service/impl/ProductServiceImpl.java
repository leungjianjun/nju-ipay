package com.ipay.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.entity.Product;
import com.ipay.server.service.IProductService;

@Service("productService")
@Transactional
public class ProductServiceImpl<T extends Product> extends ServiceImpl<T> implements IProductService<T> {

	@Override
	public void create(T baseBean) {
		dao.persist(baseBean);

	}

	@Override
	public void delete(T baseBean) {
		// TODO Auto-generated method stub

	}

}

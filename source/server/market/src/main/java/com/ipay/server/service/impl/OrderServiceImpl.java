package com.ipay.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.entity.Order;
import com.ipay.server.service.IOrderService;

@Service("orderService")
@Transactional
public class OrderServiceImpl<T extends Order> extends ServiceImpl<T> implements IOrderService<T> {

	@Override
	public void create(T baseBean) {
		dao.persist(baseBean);

	}

	@Override
	public void delete(T baseBean) {
		// TODO Auto-generated method stub

	}

}

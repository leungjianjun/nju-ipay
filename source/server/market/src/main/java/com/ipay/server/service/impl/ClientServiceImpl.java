package com.ipay.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.entity.Client;
import com.ipay.server.service.IClientService;

@Service("clientService")
@Transactional
public class ClientServiceImpl<T extends Client> extends ServiceImpl<T> implements IClientService<T>{

	@Override
	public void create(T baseBean) {
		dao.persist(baseBean);
		
	}

	@Override
	public void delete(T baseBean) {
		// TODO Auto-generated method stub
		
	}

}

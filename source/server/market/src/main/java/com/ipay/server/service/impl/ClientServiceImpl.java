package com.ipay.server.service.impl;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.entity.Client;
import com.ipay.server.security.ExceptionMessage;
import com.ipay.server.service.IClientService;
import com.ipay.server.service.ServiceException;

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

	public T getClientByAccount(String account) {
		T client = dao.findUniqueBy("from Client as client where client.account =?", account);
		if(client==null){
			throw new ServiceException(ExceptionMessage.CLIENT_NOT_FOUND,HttpServletResponse.SC_BAD_REQUEST);
		}else{
			return client;
		}
	}

}

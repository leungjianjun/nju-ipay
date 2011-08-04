package com.ipay.server.service;

import com.ipay.server.entity.Client;

public interface IClientService<T extends Client> extends IService<T> {
	
	public T getClientByAccount(String account);

}

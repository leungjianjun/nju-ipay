package com.ipay.server.service;

import com.ipay.server.entity.Authority;
import com.ipay.server.entity.User;

public interface IUserService<T extends User> extends IService<T> {
	
	public void createAuthority(Authority authority);
	
	public Authority getAuthority(String name);

}

package com.ipay.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.entity.User;
import com.ipay.server.service.IUserService;

@Service("userService")
@Transactional
public class UserServiceImpl<T extends User> extends ServiceImpl<T> implements IUserService<T> {

	@Override
	public void create(T baseBean) {
		dao.persist(baseBean);
	}

	@Override
	public void delete(T baseBean) {
		
	}

}

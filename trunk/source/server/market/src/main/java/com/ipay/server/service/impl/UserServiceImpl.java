package com.ipay.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.dao.IDao;
import com.ipay.server.entity.Authority;
import com.ipay.server.entity.User;
import com.ipay.server.service.IUserService;

@Service("userService")
@Transactional
public class UserServiceImpl<T extends User> extends ServiceImpl<T> implements IUserService<T> {

	@Autowired
	private IDao<Authority> authorityDao;
	
	@Override
	public void create(T baseBean) {
		dao.persist(baseBean);

	}

	@Override
	public void delete(T baseBean) {
		// TODO Auto-generated method stub

	}

	public IDao<Authority> getAuthorityDao() {
		return authorityDao;
	}

	public void setAuthorityDao(IDao<Authority> authorityDao) {
		this.authorityDao = authorityDao;
	}
	
	

}

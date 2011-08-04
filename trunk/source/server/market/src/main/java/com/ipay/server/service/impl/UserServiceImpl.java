package com.ipay.server.service.impl;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.dao.IDao;
import com.ipay.server.entity.Authority;
import com.ipay.server.entity.User;
import com.ipay.server.security.ExceptionMessage;
import com.ipay.server.service.IUserService;
import com.ipay.server.service.ServiceException;

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

	public void createAuthority(Authority authority) {
		authorityDao.persist(authority);
	}

	@Transactional(readOnly = true)
	public Authority getAuthority(String name) {
		return authorityDao.findUniqueBy("from Authority as authority where authority.name =?", name);
	}

	public T getUser(String account, String password) {
		T user = dao.findUniqueBy("from User as user where user.account =? and user.password =?", account,password);
		if(user==null){
			throw new ServiceException(ExceptionMessage.ACCOUNT_OR_PASSWORD_ERROR,HttpServletResponse.SC_BAD_REQUEST);
		}else{
			return user;
		}
		
	}
	
	

}

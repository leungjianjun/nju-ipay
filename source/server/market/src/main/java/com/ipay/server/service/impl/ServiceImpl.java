package com.ipay.server.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.ipay.server.dao.IDao;
import com.ipay.server.entity.BaseEntity;
import com.ipay.server.security.ExceptionMessage;
import com.ipay.server.service.IService;
import com.ipay.server.service.ServiceException;

public abstract class ServiceImpl<T extends BaseEntity> implements IService<T> {

	@Autowired
	protected IDao<T> dao;
	
	public IDao<T> getDao(){
		return dao;
	}
	
	public void setDao(IDao<T> dao){
		this.dao = dao;
	}
	
	public T find(Class<T> clazz, int id){
		T object = dao.get(clazz, id);
		if(object==null){
			throw new ServiceException(ExceptionMessage.RESOURCE_NOT_FOUND,HttpServletResponse.SC_BAD_REQUEST);
		}else{
			return object;
		}
		
	}
	
	public abstract void create(T baseBean);
	
	public abstract void delete(T baseBean);
	
	public int getTotalCount(String hql,Object ... params){
		return dao.getTotalCount(hql, params);
	}
	
	public void save(T baseBean){
		dao.save(baseBean);
	}
	
	public List<T> list(String hql){
		return dao.list(hql);
	}
	
	public List<T> list(String hql, int firstResult, int maxSize,Object ...params){
		return dao.list(hql, firstResult, maxSize, params);
	}
	
	public void update(T baseBean){
		dao.update(baseBean);
	}

}

package com.ipay.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ipay.server.dao.IDao;
import com.ipay.server.entity.BaseEntity;
import com.ipay.server.service.IService;

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
		return dao.get(clazz, id);
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

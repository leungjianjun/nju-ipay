package com.ipay.server.dao;

import java.util.List;

import org.hibernate.Query;

public interface IDao<T> {
	public T get(Class<T> clazz, int id );
	public void persist(T baseBean);
	public void save(T baseBean);
	public void delete(T baseBean);
	public List<T> list(String hql);
	public int getTotalCount(String hql,Object ... params);
	public List<T> list(String hql, int firstResult, int maxSize,Object ...params);
	public Query createQuery(String hql);
	public T findUniqueBy(String hql, Object... values);
	public void update(T baseBean);

}

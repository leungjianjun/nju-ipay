package com.ipay.server.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.ipay.server.dao.IDao;

@Repository("dao")
public class DaoImpl<T> extends HibernateDaoSupport implements IDao<T> {
	
	@Autowired
	public void setSuperSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	public void persist(T baseBean) {
		getHibernateTemplate().persist(baseBean);
	}

	public Query createQuery(String hql) {
		return getSession().createQuery(hql);
	}

	public void delete(T baseBean) {
		getHibernateTemplate().delete(baseBean);
	}

	public T get(Class<T> clazz, int id) {
		return (T)getHibernateTemplate().get(clazz, id);
	}

	public int getTotalCount(String hql, Object... params) {
		Query query = createQuery(hql);
		for(int i = 0; params != null && i < params.length; i++){
			query.setParameter(i+1, params[i]);
		}
		Object obj = createQuery(hql).uniqueResult();
		return ((Long) obj).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<T> list(String hql) {
		return getHibernateTemplate().find(hql);
	}

	@SuppressWarnings("unchecked")
	public List<T> list(String hql, int firstResult, int maxSize,Object... params) {
		Query query = createQuery(hql);
		for(int i = 0; params != null && i < params.length; i++){
			query.setParameter(i, params[i]);
		}
		return query.setFirstResult(firstResult).setMaxResults(maxSize).list();
	}

	public void save(T baseBean) {
		getHibernateTemplate().save(baseBean);
	}

	@SuppressWarnings("unchecked")
	public T findUniqueBy(String hql, Object... values) {
		Query query = createQuery(hql);
		for(int i=0;values != null && i<values.length;i++){
			query.setParameter(i, values[i]);
		}
		return (T) query.uniqueResult();
	}

	public void update(T baseBean) {
		getHibernateTemplate().update(baseBean);
	}
	

}

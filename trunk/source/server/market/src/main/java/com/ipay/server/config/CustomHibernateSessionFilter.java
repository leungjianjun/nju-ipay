package com.ipay.server.config;

import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;
import org.hibernate.*;
import org.springframework.dao.*;

public class CustomHibernateSessionFilter extends OpenSessionInViewFilter {
	protected Session getSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException {
		Session session = super.getSession(sessionFactory);
		session.setFlushMode(FlushMode.COMMIT);
		return session;
	}

	protected void closeSession(Session session, SessionFactory factory) {
		session.flush();
		super.closeSession(session, factory);
	}
}

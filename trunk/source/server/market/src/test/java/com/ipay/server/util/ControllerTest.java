package com.ipay.server.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import junit.framework.TestCase;

public abstract class ControllerTest<T> extends TestCase{
	
	protected HttpServletRequest request = new MockHttpServletRequest();
	
	protected HttpServletResponse response = new MockHttpServletResponse();
    
	protected XmlWebApplicationContext context;
    
	protected MockServletContext msc;
    
    protected T controller;
    
    protected void setUp() throws Exception {  
        String[] contexts = new String[] {"classpath:spring/root-context.xml","classpath:spring/appServlet/servlet-context.xml","classpath:spring/spring-security.xml"};  
        context = new XmlWebApplicationContext();  
        context.setConfigLocations(contexts);  
        msc = new MockServletContext();  
        context.setServletContext(msc);  
        context.refresh();  
        msc.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);  
        SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        session.setFlushMode(FlushMode.COMMIT);
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
        controller = (T) context.getBean(getBeanName());
    } 
    
    protected abstract String getBeanName();

}

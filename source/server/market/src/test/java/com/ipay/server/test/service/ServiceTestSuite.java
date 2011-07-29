package com.ipay.server.test.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	UserServiceTest.class,
	ClientServiceTest.class,
	MarketServiceTest.class,
	ProductServiceTest.class,
	ProductInfoServiceTest.class,
	RecordServiceTest.class
	
})
public class ServiceTestSuite {

}

package com.ipay.server.test.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	UserServiceTest.class,
	ClientServiceTest.class,
	MarketServiceTest.class,
	ProductInfoServiceTest.class,
	ProductServiceTest.class,
	RecordServiceTest.class
	
})
public class ServiceTestSuite {

}

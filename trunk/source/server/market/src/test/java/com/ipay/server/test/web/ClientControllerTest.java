package com.ipay.server.test.web;

import org.junit.Test;

import com.ipay.server.util.ControllerTest;

public class ClientControllerTest extends ControllerTest<ClientControllerTest> {

	@Override
	protected String getBeanName() {
		return "clientController";
	}
	
	@Test
	public void testNothing(){
		System.out.println("test nothing!");
	}

}

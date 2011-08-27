package com.ipay.server.test.web;

import org.junit.Test;

import com.ipay.server.util.ControllerTest;
import com.ipay.server.web.ClientController;

public class ClientControllerTest extends ControllerTest<ClientController> {

	@Override
	protected String getBeanName() {
		return "clientController";
	}
	
	@Test
	public void testNothing(){
		System.out.println("test nothing!");
	}

}

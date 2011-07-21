package com.ipay.server.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ipay.server.entity.Client;
import com.ipay.server.service.IClientService;

@Controller
public class ClientController {
	
	private IClientService<Client> clientService;

	public IClientService<Client> getClientService() {
		return clientService;
	}

	@Autowired
	public void setClientService(IClientService<Client> clientService) {
		this.clientService = clientService;
	}

}

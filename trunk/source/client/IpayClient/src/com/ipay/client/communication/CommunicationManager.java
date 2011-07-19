package com.ipay.client.communication;

import com.ipay.client.model.Session;

/**
 * @author tianming
 *	all communication with the server is done by this class
 *	
 */

public class CommunicationManager {
	private CommunicationManager(){
		
	}
	private static CommunicationManager manager;
	public static CommunicationManager instance(){
		if(manager == null){
			manager = new CommunicationManager();
		}
		return manager;
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return boolean
	 * for security reason, find out the MD5 of password first
	 * then send the username and md5 value to the server for authentication
	 */
	public boolean login(Session session,String username, String password){
		session.setUsername(username);
		
		return false;
	}
	
	//parameters are not defined yet
	public boolean pay(){
		
		return false;
	}
	
	//parameters and return value are not defined yet
	public void findInfo(){
		
	}
	
}

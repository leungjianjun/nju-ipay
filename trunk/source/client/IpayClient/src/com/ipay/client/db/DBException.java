package com.ipay.client.db;

import android.database.SQLException;

public class DBException extends SQLException{
	
	private String msg;
	
	public DBException(String msg){
		this.msg = msg;
	}
	
	@Override
	public String toString(){
		return msg;
	}
}

package com.ipay.client.db;

import junit.framework.Assert;

import com.ipay.client.model.Session;

import android.test.AndroidTestCase;

public class UserDBHelperTest extends AndroidTestCase{
	
	UserDBHelper userDBHelper;
	
	
	public void test1Clear(){
		userDBHelper = new UserDBHelper(getContext());
		userDBHelper.Clear();
		userDBHelper.Close();
	}
	
	public void test2SaveUser(){
		userDBHelper = new UserDBHelper(getContext());
		
		Session session = new Session("vincent","vincent11080428");
		userDBHelper.SaveUser(session);
		session = new Session("zhima","123456");
		userDBHelper.SaveUser(session);
		userDBHelper.Close();
	}
	
	public void test3GetUserList(){
		userDBHelper = new UserDBHelper(getContext());
		
		Assert.assertEquals(2, userDBHelper.GetUserList().size());
		userDBHelper.Close();
	}
	
	public void test4DelUser(){
		userDBHelper = new UserDBHelper(getContext());
		
		userDBHelper.DelUser("vincent");
		Assert.assertEquals(1, userDBHelper.GetUserList().size());
		userDBHelper.Close();
	}
	
	public void test5Upgrade(){
		userDBHelper = new UserDBHelper(getContext());
		SqliteHelper.getInstance(getContext()).onUpgrade(userDBHelper.getDb(), 0, 0);
		userDBHelper.Close();
	}
	
}

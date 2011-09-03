package com.ipay.client;

import android.os.Bundle;

import com.ipay.client.ui.base.BaseActivity;
import com.ipay.client.ui.component.NaviBar;
import com.ipay.client.ui.component.NaviBarBack;

public class BalanceActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
	}
	
	protected void initViews(){
		setContentView(R.layout.balance);
		naviBar=new NaviBarBack(this);
		naviBar.setTitle(R.string.balance_navi_title);
	}
	
	protected void update(){
		
		
	}

	
	protected void doRetrive(){
		
	}
	
}

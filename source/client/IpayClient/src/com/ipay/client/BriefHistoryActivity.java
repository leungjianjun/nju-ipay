/**
 * 
 */
package com.ipay.client;

import android.os.Bundle;

import com.ipay.client.ui.base.BaseListActivity;
import com.ipay.client.ui.component.NaviBarBack;

/**
 * @author tangym
 *
 */
public class BriefHistoryActivity extends BaseListActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
	}
	
	
	protected void initViews(){
		setContentView(R.layout.brief_record);
		naviBar=new NaviBarBack(this);
		naviBar.setTitle(R.string.brief_history_title);
		
	}

	
	@Override
	protected void update() {
		

	}

	
	@Override
	protected void doRetrive() {
		

	}


	@Override
	protected void bindItemOnClickListener() {
		

	}

}

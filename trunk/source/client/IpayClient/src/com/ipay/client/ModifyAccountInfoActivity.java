/**
 * 
 */
package com.ipay.client;

import android.os.Bundle;

import com.ipay.client.ui.base.BaseActivity;
import com.ipay.client.ui.component.NaviBarBack;

/**
 * @author tangym
 *
 */
public class ModifyAccountInfoActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_account);
		naviBar=new NaviBarBack(this);
		naviBar.setTitle(R.string.modify_account_info_title);
	}

}

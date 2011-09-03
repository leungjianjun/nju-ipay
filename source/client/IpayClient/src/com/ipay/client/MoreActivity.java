/**
 * 
 */
package com.ipay.client;

import com.ipay.client.ui.base.BaseActivity;
import com.ipay.client.ui.component.NaviBar;
import com.ipay.client.ui.component.NaviBarBack;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author tangym
 * 
 */
public class MoreActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);

		naviBar = new NaviBar(this);
		// naviBar.setTitle(R.string.brief_history_title);
	}

}

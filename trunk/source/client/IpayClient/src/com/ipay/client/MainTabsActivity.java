/**
 * 
 */
package com.ipay.client;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

/**
 * @author tangym
 * 
 */
public class MainTabsActivity extends TabActivity {
	private static final String TAG="MainTabsActivity";

	private TabHost tabHost;
	private RadioGroup tabGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		
		setContentView(R.layout.main_tabs);
		tabHost = getTabHost();
		//商场首页
		Intent home = new Intent(this, HomeActivity.class);
		tabHost.addTab(tabHost.newTabSpec("HOME").setIndicator("HOME")
				.setContent(home));
		
		Intent tab2 = new Intent(this, TestActivity2.class);
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("TAB2")
				.setContent(tab2));
		//购物车
		Intent cart = new Intent(this, ShoppingCartActivity.class);
		tabHost.addTab(tabHost.newTabSpec("CART").setIndicator("CART")
				.setContent(cart));
		
		Intent account = new Intent(this, AccountActivity.class);
		tabHost.addTab(tabHost.newTabSpec("ACCOUNT").setIndicator("ACCOUNT")
				.setContent(account));
		
		Intent more = new Intent(this, MoreActivity.class);
		tabHost.addTab(tabHost.newTabSpec("MORE").setIndicator("MORE")
				.setContent(more));

		tabGroup = (RadioGroup) this
				.findViewById(R.id.tab_group);
		
		//绑定tabs和Radio Buttons
		tabGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int rid) {
				// TODO Auto-generated method stub
				Log.d("", "you selected=" + rid);
				switch (rid) {
				case R.id.radio_button0:
					tabHost.setCurrentTabByTag("HOME");
					
					break;
				case R.id.radio_button1:
					tabHost.setCurrentTabByTag("tab2");
					
					break;
				case R.id.radio_button2:
					tabHost.setCurrentTabByTag("CART");
					break;
				case R.id.radio_button3:
					tabHost.setCurrentTabByTag("ACCOUNT");
					break;
				case R.id.radio_button4:
					tabHost.setCurrentTabByTag("MORE");
					break;

				}
			}

		});
	}

}

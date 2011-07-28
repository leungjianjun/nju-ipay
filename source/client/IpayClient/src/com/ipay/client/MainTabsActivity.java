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

	private TabHost tabhost;
	private RadioGroup tabGroup;
	private int selected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		
		setContentView(R.layout.main_tabs);
		tabhost = getTabHost();
		Intent home = new Intent(this, HomeActivity.class);
		tabhost.addTab(tabhost.newTabSpec("HOME").setIndicator("HOME")
				.setContent(home));
		Intent tab2 = new Intent(this, TestActivity2.class);
		tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator("TAB2")
				.setContent(tab2));
		Intent tab3 = new Intent(this, TestActivity3.class);
		tabhost.addTab(tabhost.newTabSpec("tab3").setIndicator("TAB3")
				.setContent(tab3));
		
		Intent account = new Intent(this, TestActivity1.class);
		tabhost.addTab(tabhost.newTabSpec("ACCOUNT").setIndicator("ACCOUNT")
				.setContent(account));

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
					tabhost.setCurrentTabByTag("HOME");
					selected=0;
					
					break;
				case R.id.radio_button1:
					tabhost.setCurrentTabByTag("tab2");
					selected=1;
					
					break;
				case R.id.radio_button2:
					tabhost.setCurrentTabByTag("tab3");
					selected=2;
					break;
				case R.id.radio_button3:
					tabhost.setCurrentTabByTag("ACCOUNT");
					selected=3;
					break;
				case R.id.radio_button4:
					Log.d(TAG,"more checked");
					
					Intent i=new Intent(MainTabsActivity.this, TestActivity2.class);
					startActivity(i);
					group.check(selected);
					break;

				}
			}

		});
	}

}

/**
 * 
 */
package com.ipay.client;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

/**
 * @author tangym
 *
 */
public class MainTabsActivity extends TabActivity {
	
	private TabHost tabhost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_tabs);
		tabhost=getTabHost();
		Intent tab1 = new Intent(this, TestActivity1.class);
		tabhost.addTab(tabhost.newTabSpec("tab1").setIndicator("TAB1").setContent(tab1));
		Intent tab2 = new Intent(this, TestActivity2.class);
		tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator("TAB2").setContent(tab2));
		Intent tab3 = new Intent(this, TestActivity3.class);
		tabhost.addTab(tabhost.newTabSpec("tab3").setIndicator("TAB3").setContent(tab3));
		
		
		RadioGroup mainGroup=(RadioGroup)this.findViewById(R.id.test_menu_bar);
		mainGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
 	       @Override
			public void onCheckedChanged(RadioGroup arg0, int rid) {
				// TODO Auto-generated method stub
			 Log.d("radiou group", "you selected="+rid);
			 switch(rid)
			 {case R.id.radio_button0://Ê×Ò³
				 tabhost.setCurrentTabByTag("tab1");
				// msgTitle.setVisibility(View.GONE);
				 break;
			 case R.id.radio_button1://ÐÅÏ¢
				 tabhost.setCurrentTabByTag("tab2");
				// msgTitle.setVisibility(View.VISIBLE);
				 break;
			 case R.id.radio_button2://×ÊÁÏ
				 tabhost.setCurrentTabByTag("tab3");
				 //msgTitle.setVisibility(View.GONE);
				 break;
			 case R.id.radio_button3://ËÑË÷
				 tabhost.setCurrentTabByTag("TAB_SEARCH");
				 //msgTitle.setVisibility(View.GONE);
				 break;
			 case R.id.radio_button4://žü¶à	
				 //msgTitle.setVisibility(View.GONE);
				 tabhost.setCurrentTabByTag("TAB_MORE");
				 
			 }
			}
			
		}
		);
	}

}

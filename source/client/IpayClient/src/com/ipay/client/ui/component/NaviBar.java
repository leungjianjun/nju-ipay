/**
 * 
 */
package com.ipay.client.ui.component;

import com.ipay.client.AccountActivity;
import com.ipay.client.R;
import com.ipay.client.barcode.CaptureActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author tangym
 *
 */
public class NaviBar {
	protected Context context;
	protected TextView titleTxt;
	protected Activity activity;
	protected ImageButton scannerBtn;
	
	
	public NaviBar(Context context){
		this.context=context;
		this.activity=(Activity)context;
		init();
	}
	
	
	/**设置导航栏标题
	 * @param title 标题
	 */
	public void setTitle(String title){
		this.titleTxt.setText(title);
	}
	
	public void setTitle(int rsid){
		this.titleTxt.setText(rsid);
	}
	
	
	/**
	 * 初始化
	 */
	protected  void init(){
		titleTxt=(TextView) activity.findViewById(R.id.navi_bar_title);
		scannerBtn=(ImageButton)activity.findViewById(R.id.navi_bar_scanner_btn);
		scannerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(context,CaptureActivity.class);
				context.startActivity(intent);
			}
		});
	}
}

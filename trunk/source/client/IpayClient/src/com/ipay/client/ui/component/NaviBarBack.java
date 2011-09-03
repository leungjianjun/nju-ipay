/**
 * 
 */
package com.ipay.client.ui.component;

import com.ipay.client.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author tangym
 *
 */
public class NaviBarBack extends NaviBar {
	
	private Button backBtn;
	
	public NaviBarBack(Context context) {
		super(context);
	}
	
	@Override
	protected void init() {
		super.init();
		backBtn=(Button)activity.findViewById(R.id.navi_bar_back_btn);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				activity.finish();
			}
		});
	}

}

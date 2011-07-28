/**
 * 
 */
package com.ipay.client;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author tangym
 *
 */
public class TestActivity1 extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		TextView text=(TextView)findViewById(R.id.test);
		text.setText("this is tab1");
		
	}

}

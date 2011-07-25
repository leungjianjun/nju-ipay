/**
 * 
 */
package com.ipay.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * @author tangym
 *
 */
public class ShoppingCartActivity  extends Activity{
	private static final String TAG = "ShoppingCartActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.main);
	}

}

/**
 * 
 */
package com.ipay.client.ui.base;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * @author tangym
 * 
 */
public class BaseActivity extends Activity {
	private static final String TAG = "BaseActivity";
	protected SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		_onCreate(savedInstanceState);
	}

	/**
	 * 真实的onCreate方法
	 * 
	 * @param savedInstanceState
	 * @return
	 */
	protected boolean _onCreate(Bundle savedInstanceState) {
		return true;

	}

}

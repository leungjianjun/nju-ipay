/**
 * 
 */
package com.ipay.client;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author tangym
 * 
 */
public class LoginActivity extends Activity {

	private static final String TAG = "LoginActivity";
	private static final String SIS_RUNNING_KEY = "running";

	private String username;
	private String password;
	// private User user;

	// views
	private EditText usernameEdit;
	private EditText passwordEdit;
	private Button signinBtn;
	private ProgressDialog progressDialog;

	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);

		// No Title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		requestWindowFeature(Window.FEATURE_PROGRESS);

		// 获取偏好设置
		preferences = PreferenceManager.getDefaultSharedPreferences(this);

		// 初始化views
		setContentView(R.layout.login);
		usernameEdit = (EditText) findViewById(R.id.login_username_edit);
		passwordEdit = (EditText) findViewById(R.id.login_password_edit);
		signinBtn = (Button) findViewById(R.id.login_signin_button);

		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(SIS_RUNNING_KEY)) {
				if (savedInstanceState.getBoolean(SIS_RUNNING_KEY)) {
					Log.d(TAG, "Was previously logging in. Restart action.");
					doLogin();
				}
			}
		}

		signinBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				doLogin();
			}
		});

	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestory");
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "onStop");
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	private void doLogin() {
		Intent intent=new Intent(LoginActivity.this, MainTabsActivity.class);
		startActivity(intent);
		finish();

	}

	private void onLoginBegan() {

	}

	private void onLoginSucceeded() {

	}

	private void onLoginFailed() {

	}

}

/**
 * 
 */
package com.ipay.client;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author tangym
 *
 */
public class LoginActivity extends Activity {
	
	private static final String TAG="LoginActivity";
	private static final String RUNNING_STATUS="running";
	
	private String username;
	private String password;
//	private User user;
	
	//views
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
        
        //获取偏好设置
        preferences=PreferenceManager.getDefaultSharedPreferences(this);
        
        setContentView(R.layout.login);
        
		
	}
	
	
	

}

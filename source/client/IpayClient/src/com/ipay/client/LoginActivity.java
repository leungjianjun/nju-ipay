/*    Copyright 2011 Popcorn

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.ipay.client;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.ipay.client.communication.CommunicationManager;
import com.ipay.client.task.TaskResult;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author tangym
 * 
 */
public class LoginActivity extends Activity {

	private static final String TAG = "LoginActivity";

	private String username;
	private String password;

	private EditText usernameEdit;
	private EditText passwordEdit;
	private TextView progressText;
	private Button loginBtn;
	private ProgressDialog progressDialog;
	private LoginTask loginTask;

	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		// 获取preference
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		// 初始化
		setContentView(R.layout.login);
		usernameEdit = (EditText) findViewById(R.id.login_username_edit);
		passwordEdit = (EditText) findViewById(R.id.login_password_edit);
		progressText = (TextView) findViewById(R.id.login_progress_text);
		loginBtn = (Button) findViewById(R.id.login_signin_button);

		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doLogin();
			}
		});
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestory");
		// if(loginTask!=null&&LoginTask.)
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "onStop");
		super.onStop();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	/**
	 * 登录
	 */
	private void doLogin() {

		username = usernameEdit.getText().toString();
		password = passwordEdit.getText().toString();

		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			progressText.setText(R.string.login_text_null_username_or_password);
		} else {
//			loginTask = new LoginTask();
		//	loginTask.execute(username, password);
			Intent intent = new Intent(LoginActivity.this, MainTabsActivity.class);
			startActivity(intent);
			finish();
		}
	}

	private void onLoginStart() {
		progressDialog = ProgressDialog.show(this, "",
				getString(R.string.login_text_status_logining), true, true);
	}

	private void onLoginSucceeded() {
		progressDialog.dismiss();
		Intent intent = new Intent(LoginActivity.this, MainTabsActivity.class);
		startActivity(intent);
		finish();
	}

	private void onLoginFailed() {

		progressDialog.dismiss();
		progressText.setText("用户名或密码错误");

	}

	/**
	 * 实际上用于登录的异步任务类
	 * 
	 * @author tangym
	 * 
	 */
	private class LoginTask extends AsyncTask<String, Integer, TaskResult> {

		private static final String TASK_TAG = "LoginTask";

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d(TASK_TAG, "onPreExecute");
			onLoginStart();
		}

		@Override
		protected TaskResult doInBackground(String... params) {
			Log.d(TASK_TAG, "doInBackground");
			// 获取输入的用户名和密码
			String username = params[0];
			String password = params[1];
			CommunicationManager cm = CommunicationManager.instance();
			try {
				if (cm.login(username, password) == 200) {
					Log.d(TASK_TAG, "Login Succeed");
					Editor editor = preferences.edit();
					editor.putString("username", username);
					editor.putString("password", password);
					// add 存储当前用户的id
					// editor.putString(Preferences.CURRENT_USER_ID,
					// user.getId());
					editor.commit();

					return TaskResult.OK;
				} else {
					Log.d(TASK_TAG, "Login Fail");
					return TaskResult.FAILED;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return TaskResult.FAILED;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			Log.d(TASK_TAG, "onCancelled");
		}

		@Override
		protected void onPostExecute(TaskResult result) {
			super.onPostExecute(result);
			if (result==TaskResult.OK)
				onLoginSucceeded();
			else
				onLoginFailed();
		}

	}

}

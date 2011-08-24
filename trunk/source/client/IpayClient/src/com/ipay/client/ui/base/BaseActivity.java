/**
 * 
 */
package com.ipay.client.ui.base;

import com.ipay.client.LoginActivity;
import com.ipay.client.R;
import com.ipay.client.task.GenericTask;
import com.ipay.client.task.TaskListener;
import com.ipay.client.ui.component.Feedback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * @author tangym
 * 
 */
public abstract class BaseActivity extends Activity {

	private static final String TAG = "BaseActivity";

	protected SharedPreferences preferences;

	protected GenericTask task;

	protected TaskListener taskListener;

	protected Feedback feedback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!isLogedIn()){
			showLogin();
			finish();
		}
			
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.omenu_about:
			return true;
		case R.id.omenu_logout:
			logout();
			return true;
		case R.id.omenu_setting:
			return true;
		case R.id.omenu_quit:
			quit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * 注销确认
	 */
	protected void logout() {
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示");
		builder.setMessage("确定要退出吗？");
		builder.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				doLogout();

			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.cancel();
			}
		});
		Dialog dialog = builder.create();
		dialog.show();
	}

	/**
	 * 真正做注销处理的方法
	 */
	protected void doLogout() {
		//清除配置文件
//		Editor editor = preferences.edit();
//		editor.clear();
//		editor.commit();
		//TODO: 清除图片缓存
		
		//TODO：
		
		showLogin();
		finish();
	}
	
	/**
	 * TODO:退出程序
	 */
	protected void quit(){
		Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
	}
	
	/**
	 * 跳转到登录界面
	 */
	protected void showLogin(){
		Intent intent=new Intent(this, LoginActivity.class);
		//intent.putExtra(Intent.Ex, value)
		startActivity(intent);
		
	}
	
	/**
	 * @return true 如果已经登录，反之 false
	 */
	protected boolean isLogedIn(){
		return true;
	}

}

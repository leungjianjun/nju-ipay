/**
 * 
 */
package com.ipay.client;

import java.io.IOException;

import org.apache.http.client.HttpResponseException;

import com.ipay.client.communication.CommunicationManager;
import com.ipay.client.model.UserInfo;
import com.ipay.client.task.GenericTask;
import com.ipay.client.task.TaskListener;
import com.ipay.client.task.TaskParams;
import com.ipay.client.task.TaskResult;
import com.ipay.client.ui.base.BaseActivity;
import com.ipay.client.ui.component.Feedback;
import com.ipay.client.ui.component.FeedbackFactory;
import com.ipay.client.ui.component.FeedbackFactory.FeedbackType;

import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author tangym
 * 
 */
public class AccountActivity extends BaseActivity {

	private TextView nameTxt;
	private TextView accountTxt;
	private TextView phoneNumTxt;
	private Button historyBtn;
	private Button balanceBtn;
	private Button modifyBtn;

	private GenericTask task;
	private Feedback feedback;
	private UserInfo userInfo;

	private static final int REQUEST_MODIFY = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		initViews();

		feedback = new FeedbackFactory().create(FeedbackType.PROGRESSBAR, this);

		doRetrive();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (task != null && task.getStatus() == Status.RUNNING) {
			task.cancel(true);
		}
	}

	/**
	 * 初始化界面
	 */
	protected void initViews() {

		setContentView(R.layout.account);
		nameTxt = (TextView) findViewById(R.id.account_name);
		accountTxt = (TextView) findViewById(R.id.account_account);
		phoneNumTxt = (TextView) findViewById(R.id.account_phone_num);
		historyBtn = (Button) findViewById(R.id.account_history);
		balanceBtn = (Button) findViewById(R.id.account_balance);
		modifyBtn = (Button) findViewById(R.id.account_modify);

		historyBtn.setOnClickListener(new HistoryListener());
		balanceBtn.setOnClickListener(new BalanceListener());
		modifyBtn.setOnClickListener(new ModifyListener());

	}

	/**
	 * 刷新界面
	 */
	protected void update() {

		nameTxt.setText(userInfo.getRealname());
		accountTxt.setText(userInfo.getAccount());
		phoneNumTxt.setText(userInfo.getPhone());

	}

	/**
	 * 获取UserInfo
	 */
	protected void doRetrive() {
		
		task=new GetUserInfoTask();
		TaskListener taskListener=new GetUserInfoListener();
		task.setTaskListener(taskListener);
		task.execute();

	}

	private class GetUserInfoListener implements TaskListener {

		@Override
		public void onPreExecute() {
			feedback.start("");
		}

		@Override
		public void onProgressUpdate(Integer... values) {
			feedback.update(values[0]);
		}

		@Override
		public void onPostExecute(TaskResult result) {
			// 若成功，则更新界面
			if (result == TaskResult.OK) {
				feedback.succeed("");
				update();
			} else {
				feedback.fail(getString(R.string.account_get_fail));
			}
		}

		@Override
		public void onCancelled() {
			feedback.cancel();
		}

	}

	private class GetUserInfoTask extends GenericTask {

		@Override
		protected TaskResult doInBackground(TaskParams... params) {
			CommunicationManager cm = CommunicationManager.instance();
			try {
				userInfo = cm.getUserInfo();
			} catch (HttpResponseException e) {
				e.printStackTrace();
				return TaskResult.FAILED;
			} catch (IOException e) {
				e.printStackTrace();
				return TaskResult.FAILED;
			}
			return userInfo != null ? TaskResult.OK : TaskResult.FAILED;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_MODIFY && resultCode == RESULT_OK) {
			// TODO: 刷新页面
			// userInfo=
		}
	}

	private class HistoryListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(AccountActivity.this,
					BriefHistoryActivity.class);
			startActivity(intent);
		}
	}

	private class ModifyListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(AccountActivity.this,
					ModifyAccountInfoActivity.class);
			startActivityForResult(intent, REQUEST_MODIFY);
		}
	}

	private class BalanceListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(AccountActivity.this,
					BalanceActivity.class);
			startActivity(intent);
		}
	}

}

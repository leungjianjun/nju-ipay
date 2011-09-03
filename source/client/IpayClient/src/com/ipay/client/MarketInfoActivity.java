/**
 * 
 */
package com.ipay.client;

import java.io.IOException;

import org.apache.http.client.HttpResponseException;

import com.ipay.client.app.IpayApplication;
import com.ipay.client.model.MarketInfo;
import com.ipay.client.task.GenericTask;
import com.ipay.client.task.TaskListener;
import com.ipay.client.task.TaskParams;
import com.ipay.client.task.TaskResult;
import com.ipay.client.ui.base.BaseActivity;
import com.ipay.client.ui.component.Feedback;
import com.ipay.client.ui.component.FeedbackFactory;
import com.ipay.client.ui.component.NaviBarBack;
import com.ipay.client.ui.component.FeedbackFactory.FeedbackType;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * @author tangym
 * 
 */
public class MarketInfoActivity extends BaseActivity {
	private static final String TAG = "MarketInfoActivity";

	private TextView marketNameTxt;
	private TextView marketLocationTxt;
	private TextView marketIntruTxt;
	private TextView marketServPhoneTxt;
	private TextView marketCompPhoneTxt;
	private TextView marketCreateDateTxt;

	private GenericTask task;

	private TaskListener taskListener;

	private Feedback feedback;

	private MarketInfo marketInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.market_info);
		initViews();
		taskListener = new GetMarketInfoListener();
		feedback = new FeedbackFactory().create(FeedbackType.PROGRESSBAR, this);
		getMarketInfo();
	}

	private void initViews() {

		marketNameTxt = (TextView) findViewById(R.id.market_info_name);
		marketLocationTxt = (TextView) findViewById(R.id.market_info_location);
		marketIntruTxt=(TextView)findViewById(R.id.market_info_intro);
		marketServPhoneTxt = (TextView) findViewById(R.id.market_info_service_phone);
		marketCompPhoneTxt = (TextView) findViewById(R.id.market_info_complain_phone);
		marketCreateDateTxt = (TextView) findViewById(R.id.market_info_create_date);
		naviBar=new NaviBarBack(this);
		naviBar.setTitle(R.string.market_info_title);

	}

	private void update() {
		marketNameTxt.setText(marketInfo.getName());
		marketLocationTxt.setText(marketInfo.getLocation());
		marketIntruTxt.setText(marketInfo.getIntroduction());
		marketServPhoneTxt.setText(marketInfo.getServicePhone());
		marketCompPhoneTxt.setText(marketInfo.getComplainPhone());
		marketCreateDateTxt.setText(marketInfo.getCreateDate());

	}

	private void getMarketInfo() {

		task = new GetMarketInfoTask();
		task.setTaskListener(taskListener);
		task.execute();

	}

	private class GetMarketInfoTask extends GenericTask {

		@Override
		protected TaskResult doInBackground(TaskParams... params) {
			Log.d(TAG,"开始获取商场信息");
			try {
				marketInfo = IpayApplication.communicationManager.getMarketInfo();
			} catch (HttpResponseException e) {
				e.printStackTrace();
				return TaskResult.FAILED;
			} catch (IOException e) {
				e.printStackTrace();
				return TaskResult.FAILED;
			}
			publishProgress(60);

			if (marketInfo != null) {
				return TaskResult.OK;
			} else {
				return TaskResult.FAILED;
			}
		}
	}

	private class GetMarketInfoListener implements TaskListener {

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
			if(result==TaskResult.OK){
				feedback.succeed("");
				update();
			}else{
				feedback.fail("失败");
			}
		}

		@Override
		public void onCancelled() {
			feedback.cancel();
		}

	}

}

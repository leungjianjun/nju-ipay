/**
 * 
 */
package com.ipay.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.HttpResponseException;

import com.ipay.client.app.IpayApplication;
import com.ipay.client.communication.CommunicationManager;
import com.ipay.client.model.Product;
import com.ipay.client.model.ShoppingCart;
import com.ipay.client.task.GenericTask;
import com.ipay.client.task.ParamsNotFoundException;
import com.ipay.client.task.TaskListener;
import com.ipay.client.task.TaskParams;
import com.ipay.client.task.TaskResult;
import com.ipay.client.ui.base.BaseActivity;
import com.ipay.client.ui.component.DialogFeedback;
import com.ipay.client.ui.component.Feedback;
import com.ipay.client.ui.component.FeedbackFactory;
import com.ipay.client.ui.component.FeedbackFactory.FeedbackType;
import com.ipay.client.ui.component.ShoppingCartArrayAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 购物车 继承自BaseActivity，带有Listview,目前不分页 TODO： 添加刷新选项，清空购物车选项
 * 
 * @author tangym
 * 
 */
public class ShoppingCartActivity extends BaseActivity {

	private static final String TAG = "ShoppingCartActivity";

	private ListView listView;
	private Button payBtn;
	private TextView quantityTxt;
	private TextView totalTxt;
	private ShoppingCartArrayAdapter adapter;
	private ShoppingCart cart = ShoppingCart.getInstance();
	private Feedback feedback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		initViews();
		update();
	}

	private void initViews() {
		setContentView(R.layout.shopping_cart);
		listView = (ListView) findViewById(R.id.cart_goods_list);
		payBtn = (Button) findViewById(R.id.cart_pay_btn);
		quantityTxt = (TextView) findViewById(R.id.cart_quantity);
		totalTxt = (TextView) findViewById(R.id.cart_total);
		payBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				pay();
			}
		});
		adapter = new ShoppingCartArrayAdapter(this);
		listView.setAdapter(adapter);

		feedback = new FeedbackFactory().create(FeedbackType.DIALOG, this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		update();
	}

	public void update() {

		totalTxt.setText("" + cart.getTotalPrice());
		quantityTxt.setText("" + cart.getQuantity());
	}

	protected void bindItemOnClickListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ShoppingCartActivity.this,
						GoodsInfoActivity.class);
				intent.putExtra("pid", cart.get(position).getId());
				startActivity(intent);
			}
		});
	}

	private void pay() {
		final LayoutInflater inflater = getLayoutInflater();
		final View view = inflater.inflate(R.layout.pay, null);
		TextView moneyTxt = (TextView) view.findViewById(R.id.pay_money);
		final EditText pinEdit = (EditText) view.findViewById(R.id.pay_pin);
		moneyTxt.setText(totalTxt.getText().toString());

		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.shopping_cart_pay_title);
		builder.setView(view);
		builder.setPositiveButton(R.string.shopping_cart_pay_sure,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO: 支付
						String pin = pinEdit.getText().toString();
						if (!TextUtils.isEmpty(pin))
							doPay(pin);
						else
							Toast.makeText(ShoppingCartActivity.this,
									R.string.shopping_cart_pay_pin_null,
									Toast.LENGTH_SHORT).show();

					}
				});

		builder.setNegativeButton(R.string.shopping_cart_pay_cancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO:取消
						dialog.cancel();
					}
				});

		Dialog dialog = builder.create();
		dialog.show();
	}

	private void doPay(String pin) {
		Log.d(TAG, "支付" + pin);
		GenericTask task = new PayTask();
		TaskListener taskListener = new PayTaskListener();
		task.setTaskListener(taskListener);
		TaskParams params = new TaskParams();
		params.put("pin", pin);
		task.execute(params);

	}

	private class PayTask extends GenericTask {

		@Override
		protected TaskResult doInBackground(TaskParams... params) {
			String pin = null;
			try {
				pin = params[0].getString("pin");
			} catch (ParamsNotFoundException e) {
				e.printStackTrace();
				return TaskResult.FAILED;
			}

			CommunicationManager cm = CommunicationManager.instance();
			int statusCode = 0;
			try {
				statusCode = cm.pay(pin, ShoppingCartActivity.this);
				publishProgress(60);
			} catch (HttpResponseException e) {
				e.printStackTrace();
				return TaskResult.FAILED;
			} catch (IOException e) {
				e.printStackTrace();
				return TaskResult.FAILED;
			}

			if (statusCode == 200)
				return TaskResult.OK;
			else
				return TaskResult.FAILED;
		}
	}

	private class PayTaskListener implements TaskListener {

		@Override
		public void onPreExecute() {
			feedback.start("正在连接服务器");
		}

		@Override
		public void onProgressUpdate(Integer... values) {
			feedback.update("等待处理");
		}

		@Override
		public void onPostExecute(TaskResult result) {
			if (result == TaskResult.OK)
				feedback.succeed("");
			else
				feedback.fail("失败");
		}

		@Override
		public void onCancelled() {
			feedback.cancel();
		}

	}

}

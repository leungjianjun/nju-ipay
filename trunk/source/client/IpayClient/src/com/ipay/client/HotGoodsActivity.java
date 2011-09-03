/**
 * 
 */
package com.ipay.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.Pack200.Packer;

import org.apache.http.client.ClientProtocolException;

import com.ipay.client.communication.CommunicationManager;
import com.ipay.client.model.Product;
import com.ipay.client.task.GenericTask;
import com.ipay.client.task.TaskListener;
import com.ipay.client.task.TaskParams;
import com.ipay.client.task.TaskResult;
import com.ipay.client.ui.base.BaseListActivity;
import com.ipay.client.ui.base.Pageable;
import com.ipay.client.ui.component.Feedback;
import com.ipay.client.ui.component.FeedbackFactory;
import com.ipay.client.ui.component.NaviBarBack;
import com.ipay.client.ui.component.FeedbackFactory.FeedbackType;
import com.ipay.client.ui.component.GoodsArrayAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author tangym
 * 
 */
public class HotGoodsActivity extends BaseListActivity implements Pageable {
	private static final String TAG = "HotGoodsActivity";
	// 页码
	private int page = 1;

	private View listHeader;
	private View listFooter;
	private ArrayAdapter<Product> listItemAdapter;
	private ArrayList<Product> products=new ArrayList<Product>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hot_goods);
		//各种初始化
		listView = (ListView) findViewById(R.id.hot_goods_list);
		setHeaderAndFooter();
		bindItemOnClickListener();
		
		
		naviBar=new NaviBarBack(this);
		naviBar.setTitle(R.string.hot_goods_title);
		
		taskListener = new GetHotGoodsTaskListener();
		feedback = new FeedbackFactory().create(FeedbackType.PROGRESSBAR,
				HotGoodsActivity.this);
		listItemAdapter = new GoodsArrayAdapter(this, new ArrayList<Product>());
		listView.setAdapter(listItemAdapter);
		doRetrive();

	}

	/**
	 * 给listview添加头部和尾部
	 */
	private void setHeaderAndFooter() {

		listHeader = View.inflate(this, R.layout.listview_header, null);
		listFooter = View.inflate(this, R.layout.listview_footer, null);
		listView.addHeaderView(listHeader, null, true);
		listView.addFooterView(listFooter, null, true);
	}

	@Override
	protected void bindItemOnClickListener() {

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (position == 0) {
					Log.d("HotgoodsActivity", "已选择刷新");
				} else if (position == listView.getCount() - 1) {
					Log.d("HotgoodsActivity", "已选择更多");
					nextPage();
				} else {
					Intent i = new Intent(HotGoodsActivity.this,
							GoodsInfoActivity.class);
					startActivity(i);
				}
			}
		});

	}

	public void show() {
		Log.d(TAG, "已经按下移除按钮");
		listItemAdapter.add(new Product(111, "介绍: ", "1111", "名称: ", "111111"));

	}

	@Override
	protected void update() {

	}

	@Override
	protected void doRetrive() {
		task = new GetHotGoodsTask();
		task.setTaskListener(taskListener);
		task.execute();
	}

	@Override
	public void nextPage() {

		page++;
		doRetrive();
	
	}

	@Override
	public int getCurrentPage() {
		return page;
	}

	private class GetHotGoodsTaskListener implements TaskListener {

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
			if (result == TaskResult.OK) {
				for (int i=(page-1)*10;i<page*10;i++) {
					listItemAdapter.add(products.get(i));
				}

				feedback.succeed("");
			} else {
				feedback.fail("失败");
			}

		}

		@Override
		public void onCancelled() {
			// TODO Auto-generated method stub

		}

	}

	private class GetHotGoodsTask extends GenericTask {

		@Override
		protected TaskResult doInBackground(TaskParams... params) {
			// CommunicationManager cm = CommunicationManager.instance();

			ArrayList<Product> list;
			int process=0;

			list = new ArrayList<Product>();
			for (int i = (page-1)*10; i < page*10; i++) {
				list.add(new Product(111, "介绍: " + i, "1111", "名称: " + i,
						"111111"));
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				publishProgress(process);
				process+=10;
			}

			for (Product product : list) {
				products.add(product);
			}

			return TaskResult.OK;

		}
	}

}

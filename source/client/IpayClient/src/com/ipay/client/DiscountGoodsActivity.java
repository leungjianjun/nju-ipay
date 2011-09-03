/**
 * 
 */
package com.ipay.client;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ipay.client.communication.CommunicationManager;
import com.ipay.client.model.Product;
import com.ipay.client.model.SpecialProduct;
import com.ipay.client.task.GenericTask;
import com.ipay.client.task.TaskListener;
import com.ipay.client.task.TaskParams;
import com.ipay.client.task.TaskResult;
import com.ipay.client.ui.base.BaseListActivity;
import com.ipay.client.ui.base.Pageable;
import com.ipay.client.ui.component.FeedbackFactory;
import com.ipay.client.ui.component.NaviBarBack;
import com.ipay.client.ui.component.FeedbackFactory.FeedbackType;
import com.ipay.client.ui.component.SpecialGoodsArrayAdapter;

/**
 * @author tangym
 * 
 */
public class DiscountGoodsActivity extends BaseListActivity implements Pageable {

	private static final String TAG = "DiscountGoodsActivity";
	// 页码
	private int page = 1;
	private View listHeader;
	private View listFooter;
	private ArrayAdapter<SpecialProduct> listItemAdapter;
	private ArrayList<SpecialProduct> products = new ArrayList<SpecialProduct>();
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hot_goods);
		// 各种初始化
		listView = (ListView) findViewById(R.id.hot_goods_list);
		setHeaderAndFooter();
		
		bindItemOnClickListener();
		naviBar=new NaviBarBack(this);
		naviBar.setTitle(R.string.discount_goods_title);
		
		taskListener = new GetDiscountGoodsTaskListener();
		feedback = new FeedbackFactory().create(FeedbackType.PROGRESSBAR, this);
		listItemAdapter = new SpecialGoodsArrayAdapter(this,
				new ArrayList<SpecialProduct>());
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
		progressBar = (ProgressBar) listFooter
				.findViewById(R.id.list_footer_progress_bar);
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
					Intent intent = new Intent(DiscountGoodsActivity.this,
							GoodsInfoActivity.class);
					Log.d(TAG, "position: " + position);
					intent.putExtra("pid", getItem(position).getId());
					startActivity(intent);
				}
			}
		});

	}

	private Product getItem(int position) {
		return listItemAdapter.getItem(position - 1);
	}

	public void show() {
		Log.d(TAG, "已经按下移除按钮");

	}

	@Override
	protected void update() {

	}

	@Override
	protected void doRetrive() {
		task = new GetDiscountGoodsTask();
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

	private class GetDiscountGoodsTaskListener implements TaskListener {

		@Override
		public void onPreExecute() {

			feedback.start("");

			progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		public void onProgressUpdate(Integer... values) {
			feedback.update(values[0]);
		}

		@Override
		public void onPostExecute(TaskResult result) {
			
			if (result == TaskResult.OK) {
				int count = 0;
				
				//将打折商品实体装入adapter，如果个数为0（即没有更多了），则隐藏更多按钮
				for (int i = (page - 1) * 10; i < products.size(); i++) {
					listItemAdapter.add(products.get(i));
					count++;
				}
				
				if (count == 0) {
					page--;
					listFooter.setVisibility(View.GONE);
				}

				feedback.succeed("");
			} else {
				page--;
				feedback.fail("失败");
			}
			progressBar.setVisibility(View.GONE);

		}

		@Override
		public void onCancelled() {

			progressBar.setVisibility(View.VISIBLE);
		}

	}

	private class GetDiscountGoodsTask extends GenericTask {

		@Override
		protected TaskResult doInBackground(TaskParams... params) {
			// CommunicationManager cm = CommunicationManager.instance();
			Log.d(TAG, "获取打折商品，page:" + page);
			ArrayList<SpecialProduct> list;
			int process = 0;
			CommunicationManager cm = CommunicationManager.instance();
			try {
				publishProgress(40);
				list = cm.getSpecialProducts(page);

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return TaskResult.FAILED;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return TaskResult.FAILED;
			}

			publishProgress(80);

			for (SpecialProduct product : list) {
				products.add(product);
			}

			return TaskResult.OK;

		}
	}

}

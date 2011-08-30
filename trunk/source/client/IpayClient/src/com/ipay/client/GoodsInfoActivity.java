/*  Copyright 2011 Popcorn

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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.client.DefaultHttpClient;

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
import com.ipay.client.ui.component.Feedback;
import com.ipay.client.ui.component.FeedbackFactory;
import com.ipay.client.ui.component.ImageCache;
import com.ipay.client.ui.component.FeedbackFactory.FeedbackType;
import com.ipay.client.ui.component.LazyImageLoader.ImageLoaderCallback;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author tangym
 * 
 */
public class GoodsInfoActivity extends BaseActivity {

	private static final String TAG = "GoodsInfoActivitys";
	private static final String PRODUCT_ID = "pid";
	private static final String PRODUCT_BARCODE = "barcode";

	// views
	private ImageView productImageView;
	private TextView productTitleTxt;
	private TextView productNameTxt;
	private TextView productPriceTxt;
	private TextView productBrandTxt;
	private TextView productAttrsTxt;
	private Button addToCartBtn;

	private GenericTask task;
	private TaskListener taskListener;
	private Feedback feedback;
	private Product product;
	private Bitmap imageBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.goods_info);
		initViews();
		imageBitmap = ImageCache.defaultBitmap;
		feedback = new FeedbackFactory().create(FeedbackType.PROGRESSBAR, this);
		taskListener = new GetProductTaskListener();

		getProduct();

	}

	/**
	 * 初始化
	 */
	private void initViews() {

		productTitleTxt = (TextView) findViewById(R.id.goods_info_title);
		productNameTxt = (TextView) findViewById(R.id.goods_info_name);
		productPriceTxt = (TextView) findViewById(R.id.goods_info_price);
		productBrandTxt = (TextView) findViewById(R.id.goods_info_brand);
		productAttrsTxt = (TextView) findViewById(R.id.goods_info_attrs);
		productImageView = (ImageView) findViewById(R.id.goods_info_image);
		addToCartBtn = (Button) findViewById(R.id.goods_info_add_to_cart);
		addToCartBtn.setOnClickListener(new AddToCartListener());
	}

	/**
	 * 从bundle获取id 若不存在，则尝试获取barcode 根据情况从服务器查找
	 */
	private void getProduct() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		int pid = bundle.getInt(PRODUCT_ID, -1);
		Log.d(TAG, "get *****pid*****: " + pid);
		String barcode = null;
		if (pid == -1) {
			barcode = bundle.getString(PRODUCT_BARCODE);
			Log.d(TAG, "get *****barcode*****: " + pid);
			if (barcode != null) {
				addToCartBtn.setVisibility(View.VISIBLE);
				getProductByBarcode(barcode);
			}

		} else {

			// getProductByBarcode("838374833");
			getProductById(pid);
		}

	}

	/**
	 * 根据商品ID获取
	 * 
	 * @param pid
	 *            商品id
	 */
	private void getProductById(int pid) {
		task = new GetProductByIdTask();
		task.setTaskListener(taskListener);
		TaskParams params = new TaskParams();
		params.put(PRODUCT_ID, pid);
		task.execute(params);

	}

	/**
	 * 根据商品条形码获取
	 * 
	 * @param barcode
	 *            条形码
	 */
	private void getProductByBarcode(String barcode) {

		task = new GetProductByBarcodeTask();
		task.setTaskListener(taskListener);
		TaskParams params = new TaskParams();
		params.put(PRODUCT_BARCODE, barcode);
		task.execute(params);
	}

	/**
	 * 添加到购物车
	 */
	private boolean addToCart() {

		ShoppingCart cart = IpayApplication.shoppingCart;
		if (cart.add(product)) {
			Toast.makeText(this, R.string.goods_info_add_success,
					Toast.LENGTH_SHORT).show();
			return true;
		}

		else {
			Toast.makeText(this, R.string.goods_info_add_fail,
					Toast.LENGTH_SHORT).show();
			return false;
		}

	}

	/**
	 * 刷新界面
	 */
	private void update() {

		productImageView.setImageBitmap(imageBitmap);
		productTitleTxt.setText(product.getName());
		productNameTxt.setText("品名：" + product.getName());
		productPriceTxt.setText("价格：" + product.getPrice());
		productBrandTxt.setText("厂商：" + product.getBanner());
		productAttrsTxt.setText("简介：" + '\n');
		HashMap<String, String> attrs = product.getAttributes();
		Set<String> keySet = attrs.keySet();
		for (String key : keySet) {
			productAttrsTxt.append(key + ": " + attrs.get(key));
		}

	}

	private class GetProductTaskListener implements TaskListener {

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

				feedback.succeed("");
				update();
			} else {
				feedback.fail("失败");
			}
		}

		@Override
		public void onCancelled() {
			feedback.cancel();
		}

	}

	/**
	 * TODO：通过Id获取
	 * 
	 * @author tangym
	 * 
	 */
	private class GetProductByIdTask extends GenericTask {

		@Override
		protected TaskResult doInBackground(TaskParams... params) {
			int pid = 0;

			// 获取pid
			try {
				pid = params[0].getInt(PRODUCT_ID);
				publishProgress(40);
			} catch (ParamsNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d(TAG, "pid not found");
				return TaskResult.FAILED;
			}
			if (pid > 0) {
				try {
					product = IpayApplication.communicationManager
							.getProductInfo(pid);
				} catch (HttpResponseException e1) {
					e1.printStackTrace();
					return TaskResult.FAILED;
				} catch (IOException e1) {

					e1.printStackTrace();
					return TaskResult.FAILED;
				}

				if (product != null) {
					String imageURL = CommunicationManager.BASE_URL
							+ product.getMidImgUrl();
					Log.d(TAG, "mid image url: " + imageURL);
					try {
						URL url = new URL(imageURL);
						url.getContent();
						InputStream is = (InputStream) url.getContent();
						imageBitmap = BitmapFactory
								.decodeStream(new BufferedInputStream(is));
					} catch (IOException e) {

						e.printStackTrace();
						Log.d(TAG, "获取图片失败");
					}

				}

				publishProgress(80);
			} else {
				return TaskResult.FAILED;
			}
			return TaskResult.OK;
		}

	}

	private class GetProductByBarcodeTask extends GenericTask {

		@Override
		protected TaskResult doInBackground(TaskParams... params) {
			String barcode = null;

			// 获取pid
			try {
				barcode = params[0].getString(PRODUCT_BARCODE);
				Log.d(TAG, "条形码为: " + barcode);
				publishProgress(40);
			} catch (ParamsNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d(TAG, "pid not found");
				return TaskResult.FAILED;
			}

			if (barcode != null) {

				try {
					product = IpayApplication.communicationManager
							.getProductInfo(barcode);
				} catch (HttpResponseException e) {
					e.printStackTrace();
					return TaskResult.FAILED;
				} catch (IOException e) {
					e.printStackTrace();
					return TaskResult.FAILED;
				}

				Log.d(TAG, "商品名称: " + product.getName());
				publishProgress(80);
			}

			if (product != null) {
				String imageURL = CommunicationManager.BASE_URL
						+ product.getMidImgUrl();
				Log.d(TAG, "mid image url: " + imageURL);
				try {
					URL url = new URL(imageURL);
					url.getContent();
					InputStream is = (InputStream) url.getContent();
					imageBitmap = BitmapFactory
							.decodeStream(new BufferedInputStream(is));
				} catch (IOException e) {

					e.printStackTrace();
					Log.d(TAG, "获取图片失败");
				}

			}

			if (product != null)
				return TaskResult.OK;
			else
				return TaskResult.FAILED;
		}

	}

	private class AddToCartListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (product != null) {
				if (addToCart())
					finish();
			}
		}

	}

}

/**
 * 
 */
package com.ipay.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.ipay.client.app.IpayApplication;
import com.ipay.client.model.Product;
import com.ipay.client.model.ShoppingCart;
import com.ipay.client.ui.base.BaseActivity;
import com.ipay.client.ui.component.ShoppingCartArrayAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

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
		adapter=new ShoppingCartArrayAdapter(this);
		listView.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		update();
	}

	public void update() {
		
		ShoppingCart cart=ShoppingCart.getInstance();
		totalTxt.setText(""+cart.getTotalPrice());
		quantityTxt.setText(""+cart.getQuantity());
	}

	protected void bindItemOnClickListener() {
		

	}

	private void pay() {
		Product p=new Product(1,"描述 "+22, "101010110", "名称: "+22, "dddd");
		p.setQuantity(10);
		ShoppingCart.getInstance().add(p);

	}

}

/**
 * 
 */
package com.ipay.client;

import java.util.ArrayList;
import java.util.HashMap;
import com.ipay.client.ui.base.BaseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 购物车
 * 继承自BaseActivity，带有Listview,目前不分页
 * TODO： 添加刷新选项，清空购物车选项
 * @author tangym
 *
 */
public class ShoppingCartActivity  extends BaseActivity{
	
	private static final String TAG = "ShoppingCartActivity";
	
	private ListView listView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG,"onCreate");
		super.onCreate(savedInstanceState);
		
		
	}
	
	private void initViews(){
		setContentView(R.layout.shopping_cart);
		listView = (ListView) findViewById(R.id.cart_goods_list);
		
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		update();
	}

	
	protected void update() {
		
	}

	
	protected void doRetrive() {

		
	}

	protected void bindItemOnClickListener() {
		// TODO Auto-generated method stub
		
	}

}

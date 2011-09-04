/**
 * 
 */
package com.ipay.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.ipay.client.barcode.CaptureActivity;
import com.ipay.client.ui.base.BaseActivity;
import com.ipay.client.ui.component.NaviBar;
import com.ipay.client.ui.component.NaviBarBack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author tangym
 * 
 */
public class HomeActivity extends BaseActivity {

	private ListView list;
	ArrayList<HashMap<String, Object>> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		naviBar=new NaviBar(this);

		list = (ListView) findViewById(R.id.home_listview);

		data = new ArrayList<HashMap<String, Object>>();
		createListData();

		SimpleAdapter listItemAdapter = new SimpleAdapter(this, data,
				R.layout.home_item_view,
				
				new String[] { "ItemImage", "ItemTitle" },

				new int[] { R.id.home_item_image, R.id.home_item_text });

		// 添加并且显示
		list.setAdapter(listItemAdapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				switch (position) {
				case 0:
					Intent marketInfo = new Intent(HomeActivity.this,
							MarketInfoActivity.class);
					startActivity(marketInfo);
					break;
				case 1:
					Intent hotGoods = new Intent(HomeActivity.this,
							HotGoodsActivity.class);
					startActivity(hotGoods);
					break;
				case 2:
					Intent discntGoods = new Intent(HomeActivity.this,
							DiscountGoodsActivity.class);
					startActivity(discntGoods);
					break;
					
				case 3:
					Intent scan=new Intent(HomeActivity.this,CaptureActivity.class);
					startActivity(scan);
					break;
				}

			}
		});

	}

	private void createListData() {
		String[] menu = getResources().getStringArray(R.array.home_menu_array);
		int[] icons={R.drawable.ic_market_info,R.drawable.ic_hot_item,R.drawable.ic_buy_now,R.drawable.ic_quick_scan};
		for (int i = 0; i < 4; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", icons[i]);
			map.put("ItemTitle", menu[i]);
			data.add(map);
		}
	}

}

/**
 * 
 */
package com.ipay.client;

import java.util.ArrayList;
import java.util.HashMap;

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
public class HomeActivity extends Activity {

	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		list = (ListView) findViewById(R.id.home_listview);

		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < 4; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", R.drawable.goods_image_example);// 图像资源的ID
			map.put("ItemTitle", "选项 " + i);
			data.add(map);
		}
		// 生成适配器的Item和动态数组对应的元素
		SimpleAdapter listItemAdapter = new SimpleAdapter(this, data,// 数据源
				R.layout.home_item_view,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemTitle" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.home_item_image, R.id.home_item_text });

		// 添加并且显示
		list.setAdapter(listItemAdapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					Intent marketInfo = new Intent(HomeActivity.this,
							MarketInfoActivity.class);
					startActivity(marketInfo);
					break;
				case 1:
					Intent  hotGoods= new Intent(HomeActivity.this,
							HotGoodsActivity.class);
					startActivity(hotGoods);
					break;
				}

			}
		});

	}

}

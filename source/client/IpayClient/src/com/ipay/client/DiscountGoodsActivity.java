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
public class DiscountGoodsActivity extends Activity {

	private ListView listView;

	private View listHeader;
	private View listFooter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hot_goods);

		listView = (ListView) findViewById(R.id.hot_goods_list);

		listHeader = View.inflate(this, R.layout.listview_header, null);
		listView.addHeaderView(listHeader, null, true);
		listFooter = View.inflate(this, R.layout.listview_footer, null);
		listView.addFooterView(listFooter, null, true);

		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < 20; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", R.drawable.goods_image_example);// 图像资源的ID
			map.put("ItemTitle", "商品名称 " + i);
			map.put("ItemMeta", "描述(如价格，数量) " + i);
			data.add(map);
		}
		// 生成适配器的Item和动态数组对应的元素
		SimpleAdapter listItemAdapter = new SimpleAdapter(this, data,// 数据源
				R.layout.goods_item,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemTitle", "ItemMeta" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.goods_image, R.id.goods_item_name,
						R.id.goods_item_meta });

		// 添加并且显示
		listView.setAdapter(listItemAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Intent i = new Intent(DiscountGoodsActivity.this,
						MarketInfoActivity.class);
				startActivity(i);
			}
		});
	}

}

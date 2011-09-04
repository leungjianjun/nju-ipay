/**
 * 
 */
package com.ipay.client.ui.component;

import java.util.ArrayList;

import com.ipay.client.R;
import com.ipay.client.model.Record;
import com.ipay.client.ui.component.LazyImageLoader.ImageLoaderCallback;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author tangym
 *
 */
public class BriefHistoryArrayAdapter extends ArrayAdapter<Record> {
	
	protected LayoutInflater inflater;
	
	public BriefHistoryArrayAdapter(Context context, ArrayList<Record> list) {
		super(context, 0, list);
		inflater = LayoutInflater.from(context);
		setNotifyOnChange(true);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View view;
		if (convertView != null) {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		} else {
			view = inflater.inflate(R.layout.common_goods_item, parent, false);
			holder = new ViewHolder();
			holder.goodsName = (TextView) view
					.findViewById(R.id.goods_item_name);
			holder.goodsMeta = (TextView) view
					.findViewById(R.id.goods_item_meta);
			view.setTag(holder);
		}
		
		return view;
	}
	
	private static class ViewHolder {
		TextView goodsName;
		TextView goodsMeta;
	}

}

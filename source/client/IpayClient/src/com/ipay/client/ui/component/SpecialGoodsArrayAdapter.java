/**
 * 
 */
package com.ipay.client.ui.component;

import java.util.ArrayList;

import com.ipay.client.HotGoodsActivity;
import com.ipay.client.R;
import com.ipay.client.model.Product;
import com.ipay.client.model.SpecialProduct;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author tangym
 *
 */
public class SpecialGoodsArrayAdapter extends ArrayAdapter<SpecialProduct> {
	protected LayoutInflater inflater;

	public SpecialGoodsArrayAdapter(Context context, ArrayList<SpecialProduct> items) {
		super(context, 0, items);
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
			view = inflater.inflate(R.layout.goods_item, parent, false);
			holder = new ViewHolder();
			holder.goodsImage = (ImageView) view.findViewById(R.id.goods_image);
			holder.goodsName = (TextView) view
					.findViewById(R.id.goods_item_name);
			holder.goodsMeta = (TextView) view
					.findViewById(R.id.goods_item_meta);
			holder.delButton = (TextView) view
					.findViewById(R.id.goods_item_add_btn);
			view.setTag(holder);
		}

		SpecialProduct product = getItem(position);

		holder.goodsImage.setImageResource(R.drawable.goods_image_example);
		holder.goodsName.setText(product.getName());
		holder.goodsMeta.setText(product.getAdWords());
		holder.delButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((HotGoodsActivity) getContext()).show();
			}
		});
		return view;
	}

	private static class ViewHolder {
		ImageView goodsImage;
		TextView goodsName;
		TextView goodsMeta;
		TextView delButton;
	}
}

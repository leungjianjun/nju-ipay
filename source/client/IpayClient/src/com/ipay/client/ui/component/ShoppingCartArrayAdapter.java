/**
 * 
 */
package com.ipay.client.ui.component;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ipay.client.R;
import com.ipay.client.app.IpayApplication;
import com.ipay.client.communication.CommunicationManager;
import com.ipay.client.model.Product;
import com.ipay.client.ui.component.LazyImageLoader.ImageLoaderCallback;

/**
 * @author tangym
 *
 */
public class ShoppingCartArrayAdapter extends ArrayAdapter<Product> {

	private static final String TAG="ShoppingCartArrayAdapter";
	protected LayoutInflater inflater;

	private ImageLoaderCallback callback;

	public ShoppingCartArrayAdapter(Context context,
			ArrayList<Product> items) {
		super(context, 0, items);
		inflater = LayoutInflater.from(context);
		setNotifyOnChange(true);
		callback = new ImageLoaderCallback() {

			@Override
			public void refresh(String url, Bitmap bitmap) {
				ShoppingCartArrayAdapter.this.notifyDataSetChanged();
			}

		};
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

		final Product product = getItem(position);

		String imageUrl=product.getMinImgUrl();
		if(!TextUtils.isEmpty(imageUrl)){
			holder.goodsImage.setImageBitmap(IpayApplication.imageLoader.get(CommunicationManager.BASE_URL+imageUrl, callback));
		}

		holder.goodsName.setText(product.getName());
		holder.goodsMeta.setText(""+product.getPrice());
		holder.delButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				remove(product);
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

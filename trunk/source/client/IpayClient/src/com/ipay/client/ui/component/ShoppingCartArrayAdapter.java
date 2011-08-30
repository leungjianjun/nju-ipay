/**
 * 
 */
package com.ipay.client.ui.component;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ipay.client.R;
import com.ipay.client.ShoppingCartActivity;
import com.ipay.client.app.IpayApplication;
import com.ipay.client.communication.CommunicationManager;
import com.ipay.client.model.Product;
import com.ipay.client.model.ShoppingCart;
import com.ipay.client.ui.component.LazyImageLoader.ImageLoaderCallback;

/**
 * @author tangym
 * 
 */
public class ShoppingCartArrayAdapter extends BaseAdapter {

	private static final String TAG = "ShoppingCartArrayAdapter";
	protected LayoutInflater inflater;

	private ImageLoaderCallback callback;
	private Context context;
	private ShoppingCart shoppingCart;

	public ShoppingCartArrayAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		callback = new ImageLoaderCallback() {

			@Override
			public void refresh(String url, Bitmap bitmap) {
				ShoppingCartArrayAdapter.this.notifyDataSetChanged();
			}

		};
		shoppingCart = ShoppingCart.getInstance();
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

		final Product product = (Product) getItem(position);

		String imageUrl = product.getMinImgUrl();
		if (!TextUtils.isEmpty(imageUrl)) {
			holder.goodsImage.setImageBitmap(IpayApplication.imageLoader.get(
					CommunicationManager.BASE_URL + imageUrl, callback));
		}

		holder.goodsName.setText(product.getName());
		holder.goodsMeta.setText("" + product.getPrice());
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

	private void remove(final Product product) {
		Builder diaBuilder = new AlertDialog.Builder(context).setTitle(
				R.string.notice_title).setMessage(R.string.notice_question);
		diaBuilder.setPositiveButton(R.string.notice_sure,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						doRemove(product);
					}
				});

		diaBuilder.setNegativeButton(R.string.notice_cancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
	
						dialog.cancel();
					}
				});
		diaBuilder.create().show();
		Log.d(TAG,"dialog created");

	}

	private void doRemove(Product p) {
		shoppingCart.remove(p);
		notifyDataSetChanged();
		((ShoppingCartActivity)context).update();
	}

	@Override
	public int getCount() {
		return shoppingCart.getSize();
	}

	@Override
	public Object getItem(int position) {

		return shoppingCart.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}

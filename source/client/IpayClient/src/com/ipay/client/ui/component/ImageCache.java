package com.ipay.client.ui.component;

import com.ipay.client.R;
import com.ipay.client.app.IpayApplication;

import android.graphics.Bitmap;

public interface ImageCache {

	public static Bitmap defaultBitmap = ImageManager
			.drawableToBitmap(IpayApplication.context.getResources()
					.getDrawable(R.drawable.goods_image_example));

	public Bitmap get(String url);

	public void put(String url, Bitmap bitmap);

}

package com.ipay.client.app;

import com.ipay.client.ui.component.LazyImageLoader;

import android.app.Application;
import android.content.Context;

public class IpayApplication extends Application {
	
	public static Context context;
	
	public static LazyImageLoader imageLoader;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context=this.getApplicationContext();
		imageLoader=new LazyImageLoader();
		
	}

}

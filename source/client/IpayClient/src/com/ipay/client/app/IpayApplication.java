package com.ipay.client.app;

import com.ipay.client.communication.CommunicationManager;
import com.ipay.client.model.Product;
import com.ipay.client.model.ShoppingCart;
import com.ipay.client.ui.component.LazyImageLoader;

import android.app.Application;
import android.content.Context;

public class IpayApplication extends Application {
	
	public static Context context;
	
	public static LazyImageLoader imageLoader;
	
	public static CommunicationManager communicationManager;
	
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context=this.getApplicationContext();
		imageLoader=new LazyImageLoader();
		communicationManager=CommunicationManager.instance();
		
		ShoppingCart shoppingCart=ShoppingCart.getInstance();
		
		for(int i=0;i<20;i++){
			Product p=new Product(i,"描述 "+i, "101010110", "名称: "+i, "dddd");
			p.setQuantity(i);
			shoppingCart.add(p);
		}
		
	}

}

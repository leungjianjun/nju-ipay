package com.ipay.client.db;

import java.util.ArrayList;

import com.ipay.client.model.Product;

import android.test.AndroidTestCase;
import android.util.Log;

public class ProductDBHelperTest extends AndroidTestCase{

	ProductDBHelper pDBHelper;
	
/*	public void test1SaveProduct(){
		pDBHelper = new ProductDBHelper(getContext());
		
		Product product = new Product();
		product.setName("乐扣乐水杯");
		product.setPrice(15.6);
		product.setDescription("韩国制造");
		product.setBanner("乐扣乐");
		product.setBarcode("asdfghjkl");
		
		pDBHelper.SaveProduct(product);
		pDBHelper.Close();
	}*/
	
	public void test1SaveProductList(){
		
		pDBHelper = new ProductDBHelper(getContext());
		
		ArrayList<Product> productList = new ArrayList<Product>();
		
		Product product = new Product();
		product.setName("乐扣乐水杯");
		product.setPrice(15.6);
		product.setDescription("棒子制造");
		product.setBanner("乐扣乐");
		product.setBarcode("CCCCCCCCCCCCCCCCCC");
		productList.add(product);
		product = new Product();
		product.setName("乐扣乐水杯");
		product.setPrice(15.6);
		product.setDescription("棒子制造");
		product.setBanner("乐扣乐");
		product.setBarcode("DDDDDDDDDDDDDDDDDD");
		productList.add(product);
		
		pDBHelper.SaveProductList(productList);
		
	}
	
	public void test2GetProductList(){
		pDBHelper = new ProductDBHelper(getContext());
		
		for(Product product : pDBHelper.GetProductList()){
			Log.d("*****************", product.getName());
			Log.d("*****************", product.getBarcode());
			Log.d("*****************", product.getBanner());
			Log.d("*****************", ""+product.getPrice());
			Log.d("*****************", product.getDescription());
		}
	}
}

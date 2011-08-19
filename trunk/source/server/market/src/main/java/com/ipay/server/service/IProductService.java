package com.ipay.server.service;

import java.util.List;

import com.ipay.server.entity.Product;
import com.ipay.server.entity.ProductInfo;

public interface IProductService<T extends Product> extends IService<T> {
	
	public void createProductInfo(ProductInfo productInfo);
	
	public ProductInfo getProductInfoByCode(String barcode);
	
	public List<T> getHotProduct(int mid,int page);
	
	public T getProductByCode(int mid,String barCode);
	
	public List<T> searchProduct(int mid,String name,int page);

}

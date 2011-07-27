package com.ipay.server.service;

import com.ipay.server.entity.Product;
import com.ipay.server.entity.ProductInfo;

public interface IProductService<T extends Product> extends IService<T> {
	
	public void createProductInfo(ProductInfo productInfo);
	
	public ProductInfo getProductInfoByCode(String barcode);

}

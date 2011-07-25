package com.ipay.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.dao.IDao;
import com.ipay.server.entity.Product;
import com.ipay.server.entity.ProductInfo;
import com.ipay.server.service.IProductService;

@Service("productService")
@Transactional
public class ProductServiceImpl<T extends Product> extends ServiceImpl<T> implements IProductService<T> {

	private IDao<ProductInfo> productInfoDao;
	
	@Override
	public void create(T baseBean) {
		dao.persist(baseBean);
	}

	@Override
	public void delete(T baseBean) {
		// TODO Auto-generated method stub

	}

	public void createProductInfo(ProductInfo productInfo) {
		productInfoDao.persist(productInfo);
	}

	public IDao<ProductInfo> getProductInfoDap() {
		return productInfoDao;
	}

	@Autowired
	public void setProductInfoDap(IDao<ProductInfo> productInfoDap) {
		this.productInfoDao = productInfoDap;
	}

	public ProductInfo getProductInfo(String barcode) {
		return productInfoDao.findUniqueBy("from ProductInfo as pi where pi.barcode =?", barcode);
		
	}

}

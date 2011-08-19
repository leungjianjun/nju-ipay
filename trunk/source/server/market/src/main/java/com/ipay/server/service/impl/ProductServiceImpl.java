package com.ipay.server.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.dao.IDao;
import com.ipay.server.entity.Product;
import com.ipay.server.entity.ProductInfo;
import com.ipay.server.security.ExceptionMessage;
import com.ipay.server.service.IProductService;
import com.ipay.server.service.ServiceException;

@Service("productService")
@Transactional
public class ProductServiceImpl<T extends Product> extends ServiceImpl<T> implements IProductService<T> {

	private IDao<ProductInfo> productInfoDao;
	
	public final int QUANTITY_PER_PAGE = 10;
	
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

	public ProductInfo getProductInfoByCode(String barcode) {
		return productInfoDao.findUniqueBy("from ProductInfo as pi where pi.barcode =?", barcode);
		
	}

	public List<T> getHotProduct(int mid, int page) {
		//dao.list("from ", firstResult, maxSize, params)
		return null;
	}

	public T getProductByCode(int mid,String barCode) {
		T product = dao.findUniqueBy("from Product as product where product.market.id =? and product.productInfo.barcode =?", mid,barCode);
		if(product==null){
			throw new ServiceException(ExceptionMessage.PRODUC_NOT_FOUND,HttpServletResponse.SC_BAD_REQUEST);
		}else{
			return product;
		}
	}

	public List<T> searchProduct(int mid, String name, int page) {
		int firstResult = (page-1)*QUANTITY_PER_PAGE;
		return dao.createQuery("from Product as product where product.market.id = :mid and product.productInfo.name like :name")
			.setParameter("mid", mid).setParameter("name","%" + name+ "%")
			.setFirstResult(firstResult).setMaxResults(firstResult+QUANTITY_PER_PAGE ).list();
	}

}

package com.ipay.server.service.impl;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipay.server.bankproxy.BankProxyServerException;
import com.ipay.server.bankproxy.BankServerProxy;
import com.ipay.server.dao.IDao;
import com.ipay.server.entity.Market;
import com.ipay.server.entity.SpecialProduct;
import com.ipay.server.security.ExceptionMessage;
import com.ipay.server.security.KeyManager;
import com.ipay.server.security.PrivateKeyEncryptor;
import com.ipay.server.service.IMarketService;
import com.ipay.server.service.ServiceException;

@Service("marketService")
@Transactional
public class MarketServiceImpl<T extends Market> extends ServiceImpl<T> implements IMarketService<T> {
	
	public final int QUANTITY_PER_PAGE = 10;

	@Autowired
	private IDao<SpecialProduct> specialProductDao; 
	
	@Override
	public void create(T baseBean) {
		dao.persist(baseBean);

	}

	@Override
	public void delete(T baseBean) {
		// TODO Auto-generated method stub

	}

	public void addSpecialProduct(SpecialProduct specialProduct) {
		specialProductDao.persist(specialProduct);
	}

	public IDao<SpecialProduct> getSpecialProductDao() {
		return specialProductDao;
	}

	public void setSpecialProductDao(IDao<SpecialProduct> specialProductDao) {
		this.specialProductDao = specialProductDao;
	}

	public List<T> findMarketsByName(String name, int pageNum) {
		int firstResult = (pageNum-1)*QUANTITY_PER_PAGE;
		return dao.createQuery("from Market as market where market.marketInfo.name like :name").setParameter("name", "%"+name+"%")
				.setFirstResult(firstResult).setMaxResults(firstResult+QUANTITY_PER_PAGE ).list();
	}

	public T finMarketByIp(String ip) {
		T market = dao.findUniqueBy("from Market as market where market.ip =?", ip);
		if(market==null){
			throw new ServiceException(ExceptionMessage.MARKET_NOT_FOUND,HttpServletResponse.SC_BAD_REQUEST);
		}else{
			return market;
		}
	}

	public List<SpecialProduct> getSpecialProduct(int mid, int page) {
		int firstResult = (page-1)*QUANTITY_PER_PAGE;
		return specialProductDao.list("from SpecialProduct as sp where sp.product.market.id =? ",firstResult,firstResult+QUANTITY_PER_PAGE, mid);
	}

	public byte[] prepareEncryptPrivatekey(String cardnum, String paypass) {
		byte[] encryptPrivatekey;
		try {
			encryptPrivatekey = BankServerProxy.getEncryptPrivakeKey(cardnum);
		} catch (BankProxyServerException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessage.ENCRYPT_PRIVATEKEY_NOT_FOUND);
		}
		byte[] privatekey = KeyManager.decryptPrivatekey(encryptPrivatekey, paypass, cardnum);
		return PrivateKeyEncryptor.encrypt(privatekey);
	}

}

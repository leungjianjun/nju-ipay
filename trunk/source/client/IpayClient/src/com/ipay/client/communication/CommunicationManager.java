package com.ipay.client.communication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.ipay.client.model.Order;
import com.ipay.client.model.Product;
import com.ipay.client.model.Session;

/**
 * all communication with the server is done by this class
 * 
 * @author tianming
 * 
 */

public class CommunicationManager {
	private CommunicationManager() {

	}

	private static CommunicationManager manager;

	public static CommunicationManager instance() {
		if (manager == null) {
			manager = new CommunicationManager();
		}
		return manager;
	}

	public static final String URL = "";

	/**
	 * 
	 * @param username
	 * @param password
	 * @return boolean for security reason, find out the MD5 of password first
	 *         then send the user name and md5 value to the server for
	 *         authentication
	 */
	public boolean login(Session session, String username, String password) {
		String md5 = Md5Crypto.encrypt(password);
		JSONObject param = new JSONObject();
		HttpPost post = new HttpPost(URL);

		// 封装post实体
		try {
			param.put("username", username);
			param.put("password", password);
			StringEntity se = new StringEntity(param.toString());
			post.setEntity(se);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 提交post并等待回应
		String token;
		try {
			HttpResponse httpResponse = new DefaultHttpClient().execute(post);
			// 得到应答的字符串，这也是一个 JSON 格式保存的数据
			String retSrc = EntityUtils.toString(httpResponse.getEntity());
			JSONObject result = new JSONObject(retSrc);
			token = (String) result.get("token");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boolean logged = false;
		// 检查回应，根据回应决定是否登陆成功
		// TODO
		if (logged) {
			session.setUsername(username);
			session.setPasswordMD5(md5);
		}
		return logged;
	}
	
	
	/**
	 * return the order of shopping cart if the payment is success
	 * @param session
	 * @return
	 */
	public Order pay(Session session) {
		boolean success = false;
		//
		//TODO pay
		//
		if(success){
			Order order = new Order(session.getShoppingCart());
			return order;
		}else{
			return null;
		}
		
		
		
	}

	// parameters and return value are not defined yet
	public Product findInfo(String barcode) {
		return null;
	}

}

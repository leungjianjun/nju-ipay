package com.ipay.client.communication;

import java.io.IOException;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
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

	public static final String LOGIN_URL = "";
	public static final String PRODUCT_URL = "";
	public static final String PAY_URL = "";
	 /** OK: Success! */
    public static final int OK = 200;
    /** Not Modified: There was no new data to return. */
    public static final int NOT_MODIFIED = 304;
    /** Bad Request: The request was invalid.  An accompanying error message will explain why. This is the status code will be returned during rate limiting. */
    public static final int BAD_REQUEST = 400;
    /** Not Authorized: Authentication credentials were missing or incorrect. */
    public static final int NOT_AUTHORIZED = 401;
    /** Forbidden: The request is understood, but it has been refused.  An accompanying error message will explain why. */
    public static final int FORBIDDEN = 403;
    /** Not Found: The URI requested is invalid or the resource requested, such as a user, does not exists. */
    public static final int NOT_FOUND = 404;
    /** Not Acceptable: Returned by the Search API when an invalid format is specified in the request. */
    public static final int NOT_ACCEPTABLE = 406;
    /** Internal Server Error: Something is broken.  Please post to the group so the Weibo team can investigate. */
    public static final int INTERNAL_SERVER_ERROR = 500;
    /** Bad Gateway: Weibo is down or being upgraded. */
    public static final int BAD_GATEWAY = 502;
    /** Service Unavailable: The Weibo servers are up, but overloaded with requests. Try again later. The search and trend methods use this to indicate when you are being rate limited. */
    public static final int SERVICE_UNAVAILABLE = 503;

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
		JSONObject personalInfo = new JSONObject();

		// 封装post实体
		try {
			personalInfo.put("username", username);
			personalInfo.put("password", password);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 提交post并等待回应
		String token;
		try {
			HttpResponse httpResponse = doPost(LOGIN_URL, personalInfo);
			// 得到应答的字符串，这也是一个 JSON 格式保存的数据
			if(httpResponse.getStatusLine().getStatusCode() == OK){
				String retSrc = EntityUtils.toString(httpResponse.getEntity());
			JSONObject result = new JSONObject(retSrc);
			token = (String) result.get("token");
			}
			
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
	public Order pay(Session session, String payPassword) {
		boolean success = false;
		
		//封装订单JSON
		JSONObject param = new JSONObject();
		JSONArray products = new JSONArray();
		try {
			param.put("username", session.getUsername());
			param.put("password", payPassword);
			for(Entry<Product, Integer> entry : session.getShoppingCart().entrySet()){
				JSONObject product = new JSONObject();
				product.put("barcode", entry.getKey().getBarcode());
				product.put("amount", entry.getValue());
				products.put(product);
			}
			param.put("order", products);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//post
		try {
			HttpResponse response = doPost(PAY_URL, param);
			if(response.getStatusLine().getStatusCode() == OK){
				
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(success){
			Order order = new Order(session.getShoppingCart());
			return order;
		}else{
			return null;
		}
		
		
		
	}

	// parameters and return value are not defined yet
	public Product findInfo(String barcode) {
		HttpGet get = new HttpGet(PRODUCT_URL + barcode);
		HttpClient client = new DefaultHttpClient();
		try {
			HttpResponse response = client.execute(get);
			if(response.getStatusLine().getStatusCode() == OK){
				
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private HttpResponse doPost(String url, JSONObject jsonObject) throws ClientProtocolException, IOException{
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost request = new HttpPost(url);
	    HttpEntity entity;
	    StringEntity s = new StringEntity(jsonObject.toString());
	    s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	    entity = s;
	    request.setEntity(entity);
	    HttpResponse response;
	    response = httpclient.execute(request);
	    return response;
	}

}

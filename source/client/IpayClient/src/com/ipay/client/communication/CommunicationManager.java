package com.ipay.client.communication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ipay.client.model.Market;
import com.ipay.client.model.Order;
import com.ipay.client.model.Product;
import com.ipay.client.model.Session;

/**
 * 所有与服务器的通信由此类完成
 * 
 * @author tianming
 * 
 */

public class CommunicationManager {
	private int marketId;
	private CommunicationManager() {

	}

	private static CommunicationManager manager;

	public static CommunicationManager instance() {
		if (manager == null) {
			manager = new CommunicationManager();
		}
		return manager;
	}

	public static final String TAG = "CommunicationManager";
	
	public static final String LOGIN_URL = "https://192.168.1.100:8443/j_security_check";
	public static final String LOGOUT_URL = "http://192.168.0.1:8080/client/logout";
	public static final String MARKET_ID_URL = "http://xxx.xxx.xxx.xxx:8080/client/findMarketId";
	public static final String MARKET_INFO_URL = "http://xxx.xxx.xxx.xxx:8080/client/MarketInfo?mid=";
	public static final String PRODUCT_INFO_URL = "http://xxx.xxx.xxx.xxx:8080/client/ProductInfoByCode?";
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
	 * @param session 若为null，创建新session对象并返回，否则仅修改session的username和password属性
	 * @param username
	 * @param password
	 * @return	失败返回null
	 */
	public Session login(Session session, String username, String password) {
//		HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
//
//        DefaultHttpClient client = new DefaultHttpClient();
//
//        SchemeRegistry registry = new SchemeRegistry();
//        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
//        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
//        registry.register(new Scheme("https", socketFactory, 443));
//        SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
//        DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());
//
//        // Set verifier     
//        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
        
//		HttpClient httpClient = new DefaultHttpClient();
//		SSLSocketFactory sf = (SSLSocketFactory)httpClient.getConnectionManager()
//		    .getSchemeRegistry().getScheme("https").getSocketFactory();
//		sf.setHostnameVerifier(new AllowAllHostnameVerifier());
		
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));
		 
		HttpParams params = new BasicHttpParams();
		params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
		params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(30));
		params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		 
		ClientConnectionManager cm = new SingleClientConnManager(params, schemeRegistry);
		HttpClient httpClient = new DefaultHttpClient(cm, params);
		
		
		HttpPost post = new HttpPost(LOGIN_URL);
		try {
			StringEntity entity = new StringEntity("j_username="+username+"&j_password="+password);
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post .setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean status = false;
		JSONObject result;
		try {
			HttpResponse response = httpClient.execute(post);
			
			Log.d(TAG, "********execute post");
			
			if(response.getStatusLine().getStatusCode() == OK){
				
				Log.d(TAG, "********response.getStatusLine().getStatusCode() == OK");
				
				String retSrc = EntityUtils.toString(response.getEntity());
				result = new JSONObject(retSrc);
				status = result.getBoolean("status");
			}
		} catch (ClientProtocolException e) {
			Log.d(TAG, "********ClientProtocolException: " + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			Log.d(TAG, "********IOException: " + e.toString());
			e.printStackTrace();
		} catch (JSONException e) {
			Log.d(TAG, "********JSONException: " + e.toString());
			e.printStackTrace();
		}
		//登陆成功
		if(status == true){
			
			Log.d(TAG,"********status == true");
			
			if(session == null){
				return new Session(username, password);
			}else{
				session.setUsername(username);
				session.setPassword(password);
				return session;
			}
		}
		//登陆失败
		else{
			Log.d(TAG,"********status == false");
			return null;
		}
		
	}
	
	
	/**
	 * 
	 * @param session
	 * @param payPassword 支付密码
	 * @return 支付失败返回null
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
	/**
	 * 获得指定商场的详细信息
	 * @param MarketId
	 * @return
	 */
	public Market getMarketInfo(int MarketId){
		HttpGet get = new HttpGet(MARKET_INFO_URL+marketId);
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpResponse response = httpClient.execute(get);
			if(response.getStatusLine().getStatusCode() == OK){
				String retSrc = EntityUtils.toString(response.getEntity());
				JSONObject result = new JSONObject(retSrc);
				Market market = new Market(marketId);
				market.setName(result.getString(Market.NAME));
				market.setLocation(result.getString(Market.LOCATION));
				market.setIntroduction(result.getString(Market.INTRODUCTION));
				market.setServicePhone(result.getString(Market.SERVICE_PHONE));
				market.setServicePhone(result.getString(Market.COMPLAIN_PHONE));
				market.setServicePhone(result.getString(Market.CREATE_DATE));
				return market;
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
		return null;
	}
	
	/**
	 * 获得当前接入的商场信息
	 * @return
	 */
	public Market getMarketInfo(){
		return getMarketInfo(marketId);
	}
	
	/**
	 * @param barcode
	 * @return 查找失败返回null
	 */
	public Product getProductInfo(String barcode) {
		HttpGet get = new HttpGet(PRODUCT_INFO_URL+"mid="+marketId+"&code="+barcode);
		HttpClient client = new DefaultHttpClient();
		try {
			HttpResponse response = client.execute(get);
			if(response.getStatusLine().getStatusCode() == OK){
				String retSrc = EntityUtils.toString(response.getEntity());
				JSONObject result = new JSONObject(retSrc);
				Product product = new Product();
				product.setId(result.getInt(Product.ID));
				product.setName(result.getString(Product.NAME));
				product.setBanner(result.getString(Product.BANNER));
				product.setBarcode(barcode);
				product.setMidImgUrl(result.getString(Product.MID_IMG_URL));
				product.setMinImgUrl(result.getString(Product.MIN_IMG_URL));
				product.setPrice(result.getDouble(Product.PRICE));
				product.setQuantity(result.getInt(Product.QUANTITY));
				JSONArray attrs = result.getJSONArray(Product.ATTRIBUTES);
				for(int i = 0; i < attrs.length(); i++){
					JSONObject attr = attrs.getJSONObject(i);
					product.putAttr(attr.getString("key"), attr.getString("value"));
				}
				return product;
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
	public boolean logout(){
		HttpGet get = new HttpGet(LOGOUT_URL);
		HttpClient httpClient = new DefaultHttpClient();
		
		boolean status = false;
		try {
			HttpResponse response = httpClient.execute(get);
			String retSrc = EntityUtils.toString(response.getEntity());
			JSONObject result = new JSONObject(retSrc);
			status = result.getBoolean("status");
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
		return status;
		
	}
	
	/**
	 * 用于获得商场id
	 * 每一次客户端成功连接到商场网络时，必须调用此方法
	 * @return
	 */
	public boolean initConnection(){
		HttpGet get = new HttpGet(MARKET_ID_URL);
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpResponse response = httpClient.execute(get);
			if(response.getStatusLine().getStatusCode() == OK){
				String retSrc = EntityUtils.toString(response.getEntity());
			JSONObject result = new JSONObject(retSrc);
			marketId = result.getInt("id");
			return true;
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
		return false;
	}

}

package com.ipay.client.communication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
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

import android.content.Context;
import android.util.Log;

import com.ipay.client.model.Market;
import com.ipay.client.model.MarketInfo;
import com.ipay.client.model.Order;
import com.ipay.client.model.Product;
import com.ipay.client.model.Session;
import com.ipay.client.model.UserInfo;

/**
 * 所有与服务器的通信由此类完成
 * 
 * @author tianming
 * 
 */

public class CommunicationManager {
	private int marketId;
	private DefaultHttpClient httpClient;
	private CommunicationManager() {
		httpClient = createHttpsClient();
	}

	private static CommunicationManager manager;

	public static CommunicationManager instance() {
		if (manager == null) {
			manager = new CommunicationManager();
		}
		return manager;
	}

	public static final String TAG = "CommunicationManager";
	

	//用户相关
	public static final String LOGIN_URL ="https://192.168.1.103:8443/j_spring_security_check";
	public static final String LOGOUT_URL = "http://192.168.0.1:8080/client/logout";
	public static final String USER_INFO_URL = "https://xxx.xxx.xxx.xxx:8443/client/GetInfo";
	public static final String SET_USER_INFO_URL = "https://xxx.xxx.xxx.xxx:8443/client/SetInfo";
	public static final String SET_PASSWORD_URL = "https://xxx.xxx.xxx.xxx:8443/client/SetInfo";
	
	//进入商店
	public static final String SEARCH_MARKET_URL = "http://xxx.xxx.xxx.xxx:8080/client/searchMarket?";	
	public static final String MARKET_ID_URL = "http://xxx.xxx.xxx.xxx:8080/client/findMarketId";
	public static final String MARKET_INFO_URL = "http://xxx.xxx.xxx.xxx:8080/client/MarketInfo?";
	public static final String SPECIAL_PRODUCT_URL = "http://xxx.xxx.xxx.xxx:8080/client/MarketSpecialProducs?";
	public static final String HOT_PRODUCT_URL = "http://xxx.xxx.xxx.xxx:8080/client/MarketHotProducts?";
	
	//扫描商品
	public static final String PRODUCT_INFO_URL = "http://xxx.xxx.xxx.xxx:8080/client/ProductInfoByCode?";
	
	//搜索商品
	public static final String SEARCH_PRODUCT_URL = "http://xxx.xxx.xxx.xxx:8080/client/SearchProduct?";
	
	//支付
	public static final String GET_KEY_URL = "https://xxx.xxx.xxx.xxx:8443/client/getEncryptPrivateKey";
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
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public Session login(Session session, String username, String password) throws ClientProtocolException, IOException {
	
		JSONObject data = new JSONObject();
		try {
			data.put("account",username);
			data.put("password", password);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Log.d(TAG,"********user json="+data.toString());
		
		
		boolean status = false;
		JSONObject result;
		try {
			HttpResponse response = doPost(LOGIN_URL, data);
			
			Log.d(TAG, "********execute post");
			
			if(response.getStatusLine().getStatusCode() == OK){
				String statusLine=response.getStatusLine().toString();
				Log.d(TAG, "********response.getStatusLine()=="+statusLine);
				Log.d(TAG, "********response.getStatusLine().getStatusCode() == OK");
				
				result = getJsonResult(response);
				String s=result.toString();
				Log.d(TAG, "********Status=="+s);
				status = result.getBoolean("status");
			}
		} 
		catch (JSONException e) {
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
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public boolean logout() throws ClientProtocolException, IOException{
		HttpGet get = new HttpGet(LOGOUT_URL);
		get.setHeader(HTTP.CONTENT_TYPE,"application/json");
		boolean status = false;
		try {
			HttpResponse response = httpClient.execute(get);
			String retSrc = EntityUtils.toString(response.getEntity());
			JSONObject result = new JSONObject(retSrc);
			status = result.getBoolean("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
		
	}
	
	/**
	 * 查看个人信息
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public UserInfo getUserInfo() throws ClientProtocolException, IOException{
		HttpGet get = new HttpGet(USER_INFO_URL);
		get.setHeader(HTTP.CONTENT_TYPE,"application/json");
		try {
			HttpResponse response = httpClient.execute(get);
			if(response.getStatusLine().getStatusCode() == OK){
				String retSrc = EntityUtils.toString(response.getEntity());
				JSONObject result = new JSONObject(retSrc);
				UserInfo info = new UserInfo();
				info.setAccount(result.getString(UserInfo.ACCOUNT));
				info.setRealname(result.getString(UserInfo.REAL_NAME));
				info.setPhone(result.getString(UserInfo.PHONE_NUM));
				return info;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @param info
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public boolean setUserInfo(UserInfo info) throws ClientProtocolException, IOException{
		JSONObject jInfo = new JSONObject();
		boolean status = false;
		try {
			jInfo.put(UserInfo.ACCOUNT, info.getAccount());
			jInfo.put(UserInfo.PHONE_NUM, info.getPhone());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			HttpResponse response = doPost(SET_USER_INFO_URL, jInfo);
			if(response.getStatusLine().getStatusCode() == OK){			
				JSONObject result = getJsonResult(response);
				status = result.getBoolean("status");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	/**
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public boolean setPassword(String oldPassword,String newPassword){
		JSONObject data = new JSONObject();
		try {
			data.put("oldPassword", oldPassword);
			data.put("newPassword", newPassword);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		boolean status = false;
		try {
			HttpResponse response = doPost(SET_PASSWORD_URL, data);
			if(response.getStatusLine().getStatusCode() == OK){
				JSONObject result = getJsonResult(response);
				status = result.getBoolean("status");
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
		return status;
	}
	/**
	 * 
	 * @param name
	 * @param pageNum
	 * @return
	 */
	public ArrayList<Market> searchMarket(String name, int pageNum){
		HttpGet get = new HttpGet(SEARCH_MARKET_URL+"name="+name+"&page="+pageNum);
		get.setHeader(HTTP.CONTENT_TYPE,"application/json");
		ArrayList<Market> markets = new ArrayList<Market>();
		try {
			HttpResponse response = httpClient.execute(get);
			if(response.getStatusLine().getStatusCode() == OK){
				JSONObject result = getJsonResult(response);
				JSONArray jMarkets = result.getJSONArray("markets");
				for(int i = 0; i < jMarkets.length(); i++){
					Market market = new Market();
					JSONObject jMarket = jMarkets.getJSONObject(i);
					market.setId(jMarket.getInt("id"));
					market.setName(jMarket.getString(MarketInfo.NAME));
					market.setLocation(jMarket.getString(MarketInfo.LOCATION));
				}
				
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
		return markets;
	}
	
	/**
	 * 检查本地是否已有该帐号对应的key
	 * 若有，直接使用
	 * 若无，获取加密密钥并保存
	 * 保存位置 /data/data/ipay/files,所以需要context
	 * @param username
	 * @param context
	 * @return
	 */
	public byte[] getEncryptPrivateKey(String username, Context context){
		//查找本地key
		byte[] key = new byte[656];
		try {	
			FileInputStream inputStream = context.openFileInput(username+".key");
			inputStream.read(key);
			inputStream.close();
		} catch (FileNotFoundException e1) {
			//未找到，需要下载
			key = downloadEncryptPrivateKey(username);
			if(key != null){
				//保存key
				try {
					FileOutputStream outputStream = context.openFileOutput(username+".key", Context.MODE_PRIVATE);
					outputStream.write(key);
					outputStream.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return key;
	}
	/**
	 * 从服务器获得key
	 * @param username
	 * @return
	 */
	private byte[] downloadEncryptPrivateKey(String username){
		HttpGet get = new HttpGet(GET_KEY_URL);
		get.setHeader(HTTP.CONTENT_TYPE,"application/json");
		byte[] key = new byte[656];
		try {
			HttpResponse response = httpClient.execute(get);
			if(response.getStatusLine().getStatusCode() == OK){
				InputStream inputStream = response.getEntity().getContent();
				inputStream.read(key);
				inputStream.close();
				return key;
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
	/**
	 * 获得指定商场的详细信息
	 * @param MarketId
	 * @return
	 */
	public MarketInfo getMarketInfo(int MarketId){
		HttpGet get = new HttpGet(MARKET_INFO_URL+marketId);
		get.setHeader(HTTP.CONTENT_TYPE,"application/json");
		try {
			HttpResponse response = httpClient.execute(get);
			if(response.getStatusLine().getStatusCode() == OK){
				JSONObject result = getJsonResult(response);
				MarketInfo marketInfo = new MarketInfo(marketId);
				marketInfo.setName(result.getString(MarketInfo.NAME));
				marketInfo.setLocation(result.getString(MarketInfo.LOCATION));
				marketInfo.setIntroduction(result.getString(MarketInfo.INTRODUCTION));
				marketInfo.setServicePhone(result.getString(MarketInfo.SERVICE_PHONE));
				marketInfo.setServicePhone(result.getString(MarketInfo.COMPLAIN_PHONE));
				marketInfo.setServicePhone(result.getString(MarketInfo.CREATE_DATE));
				return marketInfo;
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
	public MarketInfo getMarketInfo(){
		return getMarketInfo(marketId);
	}
	
	/**
	 * @param barcode
	 * @return 查找失败返回null
	 */
	public Product getProductInfo(String barcode) {
		HttpGet get = new HttpGet(PRODUCT_INFO_URL+"mid="+marketId+"&code="+barcode);
		get.setHeader(HTTP.CONTENT_TYPE,"application/json");
		try {
			HttpResponse response = httpClient.execute(get);
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
	    HttpPost request = new HttpPost(url);
	    StringEntity s = new StringEntity(jsonObject.toString());
	    s.setContentType("application/json");
	    request.setEntity(s);
	    HttpResponse response;
	    response = httpClient.execute(request);
	    return response;
	}

	
	/**
	 * 用于获得商场id
	 * 每一次客户端成功连接到商场网络时，必须调用此方法
	 * @return
	 */
	public boolean initConnection(){
		HttpGet get = new HttpGet(MARKET_ID_URL);
		get.setHeader(HTTP.CONTENT_TYPE,"application/json");
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
	/**
	 * 
	 * @param session
	 * @param payPassword 支付密码
	 * @param key 证书,在调用此方法前需先调用getEncryptPrivateKey获得证书
	 * @return 支付失败返回null
	 */
	public Order pay(Session session, String payPassword, byte[] key) {
		boolean success = false;
		
		//私钥解密
		String decryptoKey;
		try {
			decryptoKey = AesCrypto.decrypt(session.getUsername()+payPassword, new String(key));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
	 * https连接时使用
	 * @return
	 */
	private DefaultHttpClient createHttpsClient(){
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));
		 
		HttpParams params = new BasicHttpParams();
		params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
		params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(30));
		params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		 
		ClientConnectionManager cm = new SingleClientConnManager(params, schemeRegistry);
		DefaultHttpClient httpClient = new DefaultHttpClient(cm, params);
		return httpClient;
	}
	private JSONObject getJsonResult(HttpResponse response){
		JSONObject result = null;
		try {
			String retSrc = EntityUtils.toString(response.getEntity());
			
			Log.d(TAG, "********Result-----"+retSrc);
			result = new JSONObject(retSrc);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}

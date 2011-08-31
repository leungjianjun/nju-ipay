package com.ipay.client.communication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.ipay.client.model.Market;
import com.ipay.client.model.MarketInfo;
import com.ipay.client.model.Product;
import com.ipay.client.model.Session;
import com.ipay.client.model.ShoppingCart;
import com.ipay.client.model.SpecialProduct;
import com.ipay.client.model.UserInfo;
import com.ipay.client.security.KeyManager;

/**
 * 所有与服务器的通信由此类完成
 * 
 * @author tianming
 * 
 */

public class CommunicationManager {
	private int marketId = 2;
	private DefaultHttpClient httpClient;
	private Session session;

	private CommunicationManager() {
		httpClient = createHttpsClient();
		session = new Session();
	}

	public Session getSession() {
		return session;
	}

	private static CommunicationManager manager;

	public static CommunicationManager instance() {
		if (manager == null) {
			manager = new CommunicationManager();
		}
		return manager;
	}

	public static final String TAG = "CommunicationManager";

	public static final String HTTP_BASE = "http://192.168.1.101:8080/client/";
	public static final String HTTPS_BASE = "https://192.168.1.101:8443/client/";

	// 用户相关
	public static final String LOGIN_URL = HTTPS_BASE+"j_spring_security_check";
	public static final String LOGOUT_URL = HTTP_BASE+"logout";
	public static final String USER_INFO_URL = HTTPS_BASE+"GetInfo";
	public static final String SET_USER_INFO_URL = HTTPS_BASE+"SetInfo";
	public static final String SET_PASSWORD_URL = HTTPS_BASE+"SetInfo";

	// 进入商店
	public static final String SEARCH_MARKET_URL = HTTP_BASE+"searchMarket?";
	public static final String MARKET_ID_URL = HTTP_BASE+"findMarketId";
	public static final String MARKET_INFO_URL = HTTP_BASE+"MarketInfo?";
	public static final String SPECIAL_PRODUCT_URL = HTTP_BASE+"MarketSpecialProducts?";
	public static final String HOT_PRODUCT_URL = HTTP_BASE+"MarketHotProducts?";

	// 扫描商品
	public static final String PRODUCT_INFO_BY_BARCODE_URL = HTTP_BASE+"ProductInfoByCode?";
	public static final String PRODUCT_INFO_BY_ID_URL = HTTP_BASE+"ProductInfoById?";
	public static final String PRODUCT_ID_URL = HTTP_BASE+"ProductIdByCode?";
	// 搜索商品
	public static final String SEARCH_PRODUCT_URL = HTTP_BASE+"SearchProduct?";

	// 支付
	public static final String SEND_ORDER_URT = HTTPS_BASE+"SendOrder";
	public static final String GET_BANK_PRIVATE_KEY_URL = HTTPS_BASE+"getEncryptPrivateKey";
	public static final String GET_BANK_PUBLIC_KEY_URL = HTTP_BASE+"getMarketPublickey";
	public static final String GET_MARKET_PUBLIC_KEY_URL = HTTP_BASE+"getBankPublickey";
	public static final String PAY_URL = HTTPS_BASE+"PayRequest";
	public static final String PUBLIC_KEY = "public";
	public static final String PRIVATE_KEY = "private";

	/**
	 * 
	 * @param session
	 *            若为null，创建新session对象并返回，否则仅修改session的username和password属性
	 * @param username
	 * @param password
	 * @return 失败返回null
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws RequestException
	 */
	public int login(String username, String password)
			throws HttpResponseException, IOException {

		JSONObject data = new JSONObject();
		try {
			data.put("account", username);
			data.put("password", password);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Log.d(TAG, "********user json=" + data.toString());

		int statusCode = HttpStatus.SC_BAD_REQUEST;
		boolean status = false;
		JSONObject result;
		HttpResponse response = null;
		try {
			response = doPost(LOGIN_URL, data);

			Log.d(TAG, "********execute post");
			statusCode = response.getStatusLine().getStatusCode();
			result = getJsonResult(response);
			if (statusCode == HttpStatus.SC_OK) {
				String statusLine = response.getStatusLine().toString();
				Log.d(TAG, "********response.getStatusLine()==" + statusLine);
				Log.d(TAG,
						"********response.getStatusLine().getStatusCode() == OK");

				String s = result.toString();
				Log.d(TAG, "********Status==" + s);

				status = result.getBoolean("status");
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(statusCode, error);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			Log.d(TAG, "********JSONException: " + e.toString());
			e.printStackTrace();
		}
		// 登陆成功
		if (status == true) {
			Log.d(TAG, "********status == true");
			if (session.getUsername() == null) {
				session.setUsername(username);
				session.setPassword(password);
			}
		}
		return statusCode;

	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws RequestException
	 */
	public boolean logout() throws HttpResponseException, IOException {
		HttpGet get = new HttpGet(LOGOUT_URL);
		get.setHeader(HTTP.CONTENT_TYPE, "application/json");
		boolean status = false;
		try {
			HttpResponse response = httpClient.execute(get);
			JSONObject result = getJsonResult(response);
			;
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				status = result.getBoolean("status");
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (status == true) {
			session = new Session();
		}
		return status;

	}

	/**
	 * 查看个人信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public UserInfo getUserInfo() throws HttpResponseException, IOException {
		HttpGet get = new HttpGet(USER_INFO_URL);
		get.setHeader(HTTP.CONTENT_TYPE, "application/json");
		try {
			HttpResponse response = httpClient.execute(get);
			JSONObject result = getJsonResult(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				UserInfo info = new UserInfo();
				info.setAccount(result.getString(UserInfo.ACCOUNT));
				info.setRealname(result.getString(UserInfo.REAL_NAME));
				info.setPhone(result.getString(UserInfo.PHONE_NUM));
				return info;
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
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
	 * @throws HttpResponseException
	 */
	public boolean setUserInfo(UserInfo info) throws HttpResponseException,
			IOException {
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
			JSONObject result = getJsonResult(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				status = result.getBoolean("status");
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
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
	 * @throws IOException
	 */
	public boolean setPassword(String oldPassword, String newPassword)
			throws HttpResponseException, IOException {
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
			JSONObject result = getJsonResult(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				status = result.getBoolean("status");
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
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
	 *            从1开始
	 * @return
	 * @throws HttpResponseException
	 *             ,IOException
	 */
	public ArrayList<Market> searchMarket(String name, int pageNum)
			throws HttpResponseException, IOException {
		HttpGet get = new HttpGet(SEARCH_MARKET_URL + "name=" + name + "&page="
				+ pageNum);
		get.setHeader(HTTP.CONTENT_TYPE, "application/json");
		ArrayList<Market> markets = new ArrayList<Market>();
		try {
			HttpResponse response = httpClient.execute(get);
			JSONObject result = getJsonResult(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				JSONArray jMarkets = result.getJSONArray("markets");
				for (int i = 0; i < jMarkets.length(); i++) {
					Market market = new Market();
					JSONObject jMarket = jMarkets.getJSONObject(i);
					market.setId(jMarket.getInt("id"));
					market.setName(jMarket.getString(MarketInfo.NAME));
					market.setLocation(jMarket.getString(MarketInfo.LOCATION));
				}
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return markets;
	}

	/**
	 * 检查本地是否已有该帐号对应的key 若有，直接使用 若无，获取加密密钥并保存 保存位置
	 * /data/data/ipay/files,所以需要context
	 * 
	 * @param type
	 *            PUBLIC_KEY or PRIVATE_KEY
	 * @param context
	 * @return
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public byte[] getBankKey(String type, Context context)
			throws HttpResponseException, IOException {
		// 查找本地key
		byte[] key = new byte[656];

		try {
			FileInputStream inputStream = context.openFileInput(session
					.getUsername() + type + ".key");
			inputStream.read(key);
			inputStream.close();
		} catch (FileNotFoundException e1) {
			// 未找到，需要下载
			if (type.equals(PRIVATE_KEY)) {
				key = downloadKey(GET_BANK_PRIVATE_KEY_URL);
			} else if (type.equals(PUBLIC_KEY)) {
				key = downloadKey(GET_BANK_PUBLIC_KEY_URL);
			} else {
				// wrong
			}
			if (key != null) {
				// 保存key
				try {
					FileOutputStream outputStream = context.openFileOutput(
							session.getUsername() + type + ".key",
							Context.MODE_PRIVATE);
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
	 * 
	 * @return
	 * @throws IOException
	 */
	private byte[] downloadKey(String url) throws HttpResponseException,
			IOException {
		HttpGet get = new HttpGet(url);
		get.setHeader(HTTP.CONTENT_TYPE, "application/json");
		byte[] key = new byte[656];
		try {
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream inputStream = response.getEntity().getContent();
				inputStream.read(key);
				inputStream.close();
				return key;
			} else {
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), "error");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得指定商场的详细信息
	 * 
	 * @param MarketId
	 * @return
	 * @throws IOException
	 */
	public MarketInfo getMarketInfo(int MarketId) throws HttpResponseException,
			IOException {
		HttpGet get = new HttpGet(MARKET_INFO_URL + "mid=" + marketId);
		get.setHeader(HTTP.CONTENT_TYPE, "application/json");
		try {
			HttpResponse response = httpClient.execute(get);
			JSONObject result = getJsonResult(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Log.d(TAG, "获取商场信息：" + result.toString());
				MarketInfo marketInfo = new MarketInfo(marketId);
				marketInfo.setName(result.getString(MarketInfo.NAME));
				marketInfo.setLocation(result.getString(MarketInfo.LOCATION));
				marketInfo.setIntroduction(result
						.getString(MarketInfo.INTRODUCTION));
				marketInfo.setServicePhone(result
						.getString(MarketInfo.SERVICE_PHONE));
				marketInfo.setComplainPhone(result
						.getString(MarketInfo.COMPLAIN_PHONE));
				marketInfo.setCreateDate(result
						.getString(MarketInfo.CREATE_DATE));
				return marketInfo;
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
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
	 * 
	 * @return
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public MarketInfo getMarketInfo() throws HttpResponseException, IOException {
		return getMarketInfo(marketId);
	}

	/**
	 * 获得商场特价商品
	 * 
	 * @param pageNum
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public ArrayList<SpecialProduct> getSpecialProducts(int pageNum)
			throws HttpResponseException, IOException {
		ArrayList<SpecialProduct> specialProducts = new ArrayList<SpecialProduct>();
		// id 要改回 marketId
		HttpGet get = new HttpGet(SPECIAL_PRODUCT_URL + "mid=" + marketId
				+ "&page=" + pageNum);
		get.setHeader(HTTP.CONTENT_TYPE, "application/json");

		HttpResponse response;
		try {
			response = httpClient.execute(get);
			JSONObject result = getJsonResult(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Log.d(TAG, "特殊商品:" + result.toString());

				JSONArray jProducts = result.getJSONArray("specialProducts");
				for (int i = 0; i < jProducts.length(); i++) {
					SpecialProduct product = new SpecialProduct();
					JSONObject jProduct = jProducts.getJSONObject(i);
					product.setId(jProduct.getInt(Product.ID));
					product.setName(jProduct.getString(Product.NAME));
					product.setPrice(jProduct
							.getDouble(SpecialProduct.OLD_PRICE));
					product.setSpecialPrice(jProduct
							.getDouble(SpecialProduct.New_PRICE));
					product.setMinImgUrl(jProduct
							.getString(Product.MIN_IMG_URL));
					product.setAdWords(jProduct
							.getString(SpecialProduct.AD_WORDS));
					specialProducts.add(product);
				}
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return specialProducts;
	}

	/**
	 * 获得商场热销商品
	 * 
	 * @param pageNum
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public ArrayList<Product> getHotProducts(int pageNum)
			throws HttpResponseException, IOException {
		ArrayList<Product> hotProducts = new ArrayList<Product>();
		HttpGet get = new HttpGet(HOT_PRODUCT_URL + "mid=" + marketId
				+ "&page=" + pageNum);
		get.setHeader(HTTP.CONTENT_TYPE, "application/json");
		try {
			HttpResponse response = httpClient.execute(get);
			JSONObject result = getJsonResult(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				JSONArray jProducts = result.getJSONArray("hotProducts");

				for (int i = 0; i < jProducts.length(); i++) {
					Product product = new Product();
					JSONObject jProduct = jProducts.getJSONObject(i);
					product.setId(jProduct.getInt(Product.ID));
					product.setName(jProduct.getString(Product.NAME));
					product.setPrice(jProduct.getDouble(Product.PRICE));
					product.setMinImgUrl(jProduct
							.getString(Product.MIN_IMG_URL));
					hotProducts.add(product);
				}

			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hotProducts;

	}

	/**
	 * 通过条形码获得商品信息
	 * 
	 * @param barcode
	 * @return 查找失败返回null
	 * @throws IOException
	 */
	public Product getProductInfo(String barcode) throws HttpResponseException,
			IOException {
		HttpGet get = new HttpGet(PRODUCT_INFO_BY_BARCODE_URL + "mid="
				+ marketId + "&code=" + barcode);
		get.setHeader(HTTP.CONTENT_TYPE, "application/json");
		try {
			HttpResponse response = httpClient.execute(get);
			JSONObject result = getJsonResult(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
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
				for (int i = 0; i < attrs.length(); i++) {
					JSONObject attr = attrs.getJSONObject(i);
					product.putAttr(attr.getString("key"),
							attr.getString("value"));
				}
				return product;
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过id获得商品信息
	 * 
	 * @param id
	 * @return
	 * @throws HttpResponseException
	 *             , IOException
	 */
	public Product getProductInfo(int id) throws HttpResponseException,
			IOException {
		HttpGet get = new HttpGet(PRODUCT_INFO_BY_ID_URL + "pid=" + id);
		get.setHeader(HTTP.CONTENT_TYPE, "application/json");
		try {
			HttpResponse response = httpClient.execute(get);
			JSONObject result = getJsonResult(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Product product = new Product();
				product.setId(result.getInt(Product.ID));
				product.setName(result.getString(Product.NAME));
				product.setBanner(result.getString(Product.BANNER));
				product.setBarcode(result.getString(Product.BARCADE));
				product.setMidImgUrl(result.getString(Product.MID_IMG_URL));
				product.setMinImgUrl(result.getString(Product.MIN_IMG_URL));
				product.setPrice(result.getDouble(Product.PRICE));
				product.setQuantity(result.getInt(Product.QUANTITY));
				JSONArray attrs = result.getJSONArray(Product.ATTRIBUTES);
				for (int i = 0; i < attrs.length(); i++) {
					JSONObject attr = attrs.getJSONObject(i);
					product.putAttr(attr.getString("key"),
							attr.getString("value"));
				}
				return product;
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private HttpResponse doPost(String url, JSONObject jsonObject)
			throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost(url);
		StringEntity s = new StringEntity(jsonObject.toString());
		s.setContentType("application/json");
		request.setEntity(s);
		HttpResponse response;
		response = httpClient.execute(request);
		return response;
	}

	/**
	 * 用于获得商场id 每一次客户端成功连接到商场网络时，必须调用此方法
	 * 
	 * @return
	 */
	public boolean initConnection() {
		HttpGet get = new HttpGet(MARKET_ID_URL);
		get.setHeader(HTTP.CONTENT_TYPE, "application/json");
		try {
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
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
	 * @param payPassword           支付密码
	 * @param context 因为需要读取和保存密钥
	 * @return int status code
	 * @throws IOException
	 */
	public int pay(String payPassword, Context context)
			throws HttpResponseException, IOException {
		int statusCode = -1;
		// 第一步：发送订单send order
		JSONObject result = sendOrder(context);
		
		//pay
		if (result != null) {
			JSONObject data = new JSONObject();
			try {
				int tranId = result.getInt("tranId");
//				int amount = result.getInt("amount");
				String cardnum = result.getString("cardnum");
				byte[] marketPublicKey = downloadKey(GET_MARKET_PUBLIC_KEY_URL+"?mid="+marketId);
				byte[] bankPublicKey = getBankKey(PUBLIC_KEY, context);
				byte[] bankPrivateKey = KeyManager.decryptPrivatekey(
						getBankKey(PRIVATE_KEY, context), payPassword, cardnum);
				
				//准备支付信息
				data.put("mid", marketId);
				// OI
				JSONObject oi = new JSONObject();
				oi.put("tranId", tranId);
				data.put("encryptOI",
						KeyManager.encryptByRSA(marketPublicKey, oi.toString()));

				// pi
				JSONObject pi = new JSONObject();
				pi.put("tranId", tranId);
				pi.put("cardnum", cardnum);
				data.put("encryptPI",
						KeyManager.encryptByRSA(bankPublicKey, pi.toString()));

				// OIMD PIMD
				data.put("OIMD", KeyManager.sign(bankPrivateKey, oi.toString()));
				data.put("PIMD", pi.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//第二步：支付
			try{
				HttpResponse response = doPost(PAY_URL, data);
				JSONObject result2 = getJsonResult(response);
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					String bankResult = result2.getString("bankResult");
					byte[] sign = result2.getString("sign").getBytes();
					boolean verify = KeyManager.verify(getBankKey(PUBLIC_KEY, context), bankResult, sign);
					if(verify == true){
						JSONObject payResult = new JSONObject(bankResult);
						statusCode = payResult.getInt("statusCode");
						
					}
				}
			}catch (ClientProtocolException e) {
				e.printStackTrace();
			}catch (JSONException e) {
				e.printStackTrace();
			}
			
			
		}
		return statusCode;
	}

	private JSONObject sendOrder(Context context) throws HttpResponseException,
			IOException {
		JSONObject data = new JSONObject();
		JSONArray products = new JSONArray();
		try {
			data.put("mid", marketId);

			ShoppingCart cart = ShoppingCart.getInstance();
			int size = cart.getSize();
			for (int i = 0; i < size; i++) {
				Product product = cart.get(i);
				JSONObject jProduct = new JSONObject();
				jProduct.put("pid", product.getId());
				jProduct.put("quantity", product.getQuantity());
				products.put(product);
			}
			data.put("orders", products);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// post
		JSONObject orderResult = null;
		try {
			HttpResponse response = doPost(SEND_ORDER_URT, data);
			JSONObject result = getJsonResult(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String source = result.getString("source");
				byte[] sign = result.getString("sign").getBytes();
				byte[] publicKey = getBankKey(PUBLIC_KEY, context);
				boolean verify = KeyManager.verify(publicKey, source, sign);
				if (verify == true) {
					orderResult = new JSONObject(source);
				}
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderResult;
	}

	/**
	 * https连接时使用
	 * 
	 * @return
	 */
	private DefaultHttpClient createHttpsClient() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(),
				443));

		HttpParams params = new BasicHttpParams();
		params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
		params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE,
				new ConnPerRouteBean(30));
		params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

		ClientConnectionManager cm = new SingleClientConnManager(params,
				schemeRegistry);
		DefaultHttpClient httpClient = new DefaultHttpClient(cm, params);
		return httpClient;
	}

	private JSONObject getJsonResult(HttpResponse response) {
		JSONObject result = null;
		try {
			String retSrc = EntityUtils.toString(response.getEntity());

			Log.d(TAG, "********Result-----" + retSrc);
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

	/**
	 * 获得图片
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static Drawable getImage(String address) throws IOException {
		URL url = null;
		try {
			url = new URL(address);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		Object content = url.getContent();
		InputStream is = (InputStream) content;
		Drawable d = Drawable.createFromStream(is, "src");
		return d;
	}
}

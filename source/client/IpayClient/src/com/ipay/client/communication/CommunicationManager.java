package com.ipay.client.communication;

import java.io.BufferedInputStream;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import com.ipay.client.model.Market;
import com.ipay.client.model.MarketInfo;
import com.ipay.client.model.Product;
import com.ipay.client.model.Record;
import com.ipay.client.model.RecordDetail;
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
	private int marketId = 31;
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

	public static final String HTTP_BASE = "http://192.168.1.100:8080/";
	public static final String HTTPS_BASE = "https://192.168.1.100:8443/";

	// 用户相关
	public static final String LOGIN_URL = HTTPS_BASE
			+ "client/j_spring_security_check";
	public static final String LOGOUT_URL = HTTP_BASE + "client/logout";
	public static final String USER_INFO_URL = HTTPS_BASE + "client/GetInfo";
	public static final String SET_USER_INFO_URL = HTTPS_BASE
			+ "client/SetInfo";
	public static final String SET_PASSWORD_URL = HTTPS_BASE + "client/SetInfo";

	// 进入商店
	public static final String SEARCH_MARKET_URL = HTTP_BASE
			+ "client/searchMarket?";
	public static final String MARKET_ID_URL = HTTP_BASE
			+ "client/findMarketId";
	public static final String MARKET_INFO_URL = HTTP_BASE
			+ "client/MarketInfo?";
	public static final String SPECIAL_PRODUCT_URL = HTTP_BASE
			+ "client/MarketSpecialProducts?";
	public static final String HOT_PRODUCT_URL = HTTP_BASE
			+ "client/MarketHotProducts?";

	// 扫描商品
	public static final String PRODUCT_INFO_BY_BARCODE_URL = HTTP_BASE
			+ "client/ProductInfoByCode?";
	public static final String PRODUCT_INFO_BY_ID_URL = HTTP_BASE
			+ "client/ProductInfoById?";
	public static final String PRODUCT_ID_URL = HTTP_BASE
			+ "client/ProductIdByCode?";
	// 搜索商品
	public static final String SEARCH_PRODUCT_URL = HTTP_BASE
			+ "client/SearchProduct?";

	// 支付
	public static final String SEND_ORDER_URL = HTTPS_BASE + "client/SendOrder";
	public static final String GET_BANK_PRIVATE_KEY_URL = HTTPS_BASE
			+ "client/getEncryptPrivateKey";
	public static final String GET_BANK_PUBLIC_KEY_URL = HTTP_BASE
			+ "client/getBankPublickey";
	public static final String GET_MARKET_PUBLIC_KEY_URL = HTTP_BASE
			+ "client/getMarketPublickey?";
	public static final String PAY_URL = HTTPS_BASE + "client/PayRequest";
	private static final String PUBLIC_KEY = "public";
	private static final String PRIVATE_KEY = "private";
	private static final int KEY_SIZE = 162;
	private static final int ENCRYPT_KEY_SIZE = 656;

	// 购买记录
	public static final String GET_RECORDS_URL = HTTPS_BASE
			+ "client/getRecords?";
	public static final String GET_RECORDS_DETAILS_URL = HTTPS_BASE
			+ "client/getRecordDetail?";

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
				UserInfo info = UserInfo.parseJSONObject(result);
				return info;
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
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
					Market market = Market.parseJSONObect(jMarkets
							.getJSONObject(i));
					markets.add(market);
				}
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
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
		byte[] key;
		if (type.equals(PUBLIC_KEY)) {
			key = new byte[KEY_SIZE];
		} else {
			key = new byte[ENCRYPT_KEY_SIZE];
		}

		try {
			FileInputStream inputStream = context.openFileInput(session
					.getUsername() + type + ".key");
			Log.d(TAG, "本地已找到key");
			inputStream.read(key);
			inputStream.close();

		} catch (FileNotFoundException e1) {
			// 未找到，需要下载
			Log.d(TAG, "本地没有，尝试下载key");
			if (type.equals(PRIVATE_KEY)) {
				key = downloadKey(GET_BANK_PRIVATE_KEY_URL, ENCRYPT_KEY_SIZE);
			} else if (type.equals(PUBLIC_KEY)) {
				key = downloadKey(GET_BANK_PUBLIC_KEY_URL, KEY_SIZE);
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
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
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
	private byte[] downloadKey(String url, int size)
			throws HttpResponseException, IOException {
		HttpGet get = new HttpGet(url);
		Log.d(TAG, "********** down load key url: " + url);
		try {
			HttpResponse response = httpClient.execute(get);
			Log.d(TAG, "status code:"
					+ response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				byte[] key = EntityUtils.toByteArray(response.getEntity());
				return key;
			} else {
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), "error");
			}
		} catch (ClientProtocolException e) {
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
				MarketInfo marketInfo = MarketInfo.parseJSONObject(result);
				return marketInfo;
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
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
					product.setPrice(jProduct.getDouble(SpecialProduct.NEW_PRICE));
					product.setOldPrice(jProduct
							.getDouble(SpecialProduct.OLD_PRICE));
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
			e.printStackTrace();
		} catch (JSONException e) {
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
			Log.d(TAG,"热门商品:"+result.toString());
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
				Product product = Product.parseJSONObect(result);
				return product;
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
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
				Product product = Product.parseJSONObect(result);
				return product;
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private HttpResponse doPost(String url, JSONObject jsonObject)
			throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost(url);
		StringEntity s = new StringEntity(jsonObject.toString());
		s.setContentType("application/json; charset=UTF-8");
		request.setEntity(s);
		HttpResponse response;
		response = httpClient.execute(request);
		return response;
	}

	/**
	 * 用于获得商场id 每一次客户端成功连接到商场网络时，必须调用此方法
	 * 
	 * @return
	 * @throws IOException
	 */
	public int getMarketId() throws HttpResponseException, IOException {
		HttpGet get = new HttpGet(MARKET_ID_URL);
		get.setHeader(HTTP.CONTENT_TYPE, "application/json");
		try {
			HttpResponse response = httpClient.execute(get);
			JSONObject result = getJsonResult(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				marketId = result.getInt("id");
				return marketId;
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return marketId;
	}

	/**
	 * 
	 * @param payPassword
	 *            支付密码
	 * @param context
	 *            因为需要读取和保存密钥
	 * @return int status code
	 * @throws IOException
	 */
	public int pay(String payPassword, Context context)
			throws HttpResponseException, IOException {
		int statusCode = -1;
		// 第一步：发送订单send order
		JSONObject result = sendOrder(context);
		Log.d(TAG, "*******支付结果**********" + result.toString());

		// pay
		if (result != null) {
			JSONObject data = new JSONObject();
			try {
				JSONObject source = new JSONObject(result.getString("source"));
				int tranId = source.getInt("tranId");
				String cardnum = result.getString("cardnum");
				Log.d(TAG, "开始下载market publick key");
				byte[] marketPublicKey = downloadKey(GET_MARKET_PUBLIC_KEY_URL
						+ "mid=" + marketId, KEY_SIZE);
				Log.d(TAG, "开始获取bank publick key");
				byte[] bankPublicKey = getBankKey(PUBLIC_KEY, context);
				Log.d(TAG, "解密私钥....");
				byte[] bankPrivateKey = KeyManager.decryptPrivatekey(
						getBankKey(PRIVATE_KEY, context), payPassword, cardnum);

				// 准备支付信息
				data.put("mid", marketId);

				// OI
				JSONObject oi = new JSONObject();
				oi.put("tranId", tranId);
				data.put(
						"encryptOI",
						Base64.encodeToString(
								KeyManager.encryptByRSA(marketPublicKey,
										oi.toString()), Base64.DEFAULT));

				// pi
				JSONObject pi = new JSONObject();
				pi.put("tranId", tranId);
				pi.put("cardnum", cardnum);
				data.put(
						"encryptPI",
						Base64.encodeToString(
								KeyManager.encryptByRSA(bankPublicKey,
										pi.toString()), Base64.DEFAULT));

				// OIMD PIMD
				data.put("OIMD", Base64.encodeToString(
						KeyManager.sign(bankPrivateKey, oi.toString()),
						Base64.DEFAULT));
				data.put("PIMD", Base64.encodeToString(
						KeyManager.sign(bankPrivateKey, pi.toString()),
						Base64.DEFAULT));
			} catch (JSONException e) {
				e.printStackTrace();
			}

			// 第二步：支付
			try {
				Log.d(TAG, "开始支付。。。。。。");
				HttpResponse response = doPost(PAY_URL, data);
				JSONObject result2 = getJsonResult(response);
				Log.d(TAG, "支付获得的结果:" + result2.toString() + '\n' + "状态码: "
						+ response.getStatusLine().getStatusCode());
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String source = result2.getString("source");
					byte[] sign = Base64.decode(result2.getString("sign"),
							Base64.DEFAULT);
					boolean verify = KeyManager.verify(
							getBankKey(PUBLIC_KEY, context), source, sign);
					if (verify == true) {
						JSONObject payResult = new JSONObject(source);
						statusCode = payResult.getInt("statusCode");

					}
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return statusCode;
	}

	private JSONObject sendOrder(Context context) throws HttpResponseException,
			IOException {
		JSONObject data = new JSONObject();
		JSONArray products = new JSONArray();
		int myAmount = 0;
		try {
			data.put("mid", marketId);

			ShoppingCart cart = ShoppingCart.getInstance();
			int size = cart.getSize();
			for (int i = 0; i < size; i++) {
				Product product = cart.get(i);
				JSONObject jProduct = new JSONObject();
				jProduct.put("pid", product.getId());
				jProduct.put("quantity", product.getQuantity());
				products.put(jProduct);

				// 算我的总额
				myAmount += product.getQuantity() * product.getPrice();
			}
			data.put("orders", products);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Log.d(TAG, "*******源数据" + data.toString());
		// post
		try {
			HttpResponse response = doPost(SEND_ORDER_URL, data);
			JSONObject result = getJsonResult(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String source = result.getString("source");
				JSONObject jSource = new JSONObject(source);
				int amount = jSource.getInt("amount");
				byte[] sign = Base64.decode(result.getString("sign"),
						Base64.DEFAULT);

				Log.d(TAG, "*****source: " + source);
				Log.d(TAG, "*****sign: " + new String(sign));
				byte[] publicKey = getBankKey(PUBLIC_KEY, context);
				Log.d(TAG, "*****public key: " + new String(publicKey));
				boolean verify = KeyManager.verify(publicKey, source, sign);

				if (verify == true || amount == myAmount) {
					Log.d(TAG, "********verify OK");
					return result;
				}
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			Log.d(TAG, "ClientProtocol");
			e.printStackTrace();
		} catch (JSONException e) {
			Log.d(TAG, "JSON excep");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得消费简要消费记录列表
	 * 
	 * @return
	 * @throws HttpResponseException
	 * @throws IOException
	 */
	public ArrayList<Record> getRecords(int pageNum)
			throws HttpResponseException, IOException {
		ArrayList<Record> records = new ArrayList<Record>();
		HttpGet get = new HttpGet(GET_RECORDS_URL + "page=" + pageNum);
		get.setHeader(HTTP.CONTENT_TYPE, "application/json");
		try {
			HttpResponse response = httpClient.execute(get);
			JSONObject result = getJsonResult(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				JSONArray jRecords = result.getJSONArray("records");
				for (int i = 0; i < jRecords.length(); i++) {
					JSONObject jRecord = jRecords.getJSONObject(i);
					Record record = Record.parseJSONObect(jRecord);
					records.add(record);
				}
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return records;
	}

	public RecordDetail getRecordDetail(int recordId) throws IOException {
		HttpGet get = new HttpGet(GET_RECORDS_DETAILS_URL + "rid=" + recordId);
		get.setHeader(HTTP.CONTENT_TYPE, "application/json");
		RecordDetail detail = null;
		try {
			HttpResponse response = httpClient.execute(get);
			JSONObject result = getJsonResult(response);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				detail = RecordDetail.parseJSONObect(result);
			} else {
				String error = result.getString("error");
				throw new HttpResponseException(response.getStatusLine()
						.getStatusCode(), error);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return detail;
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
			result = new JSONObject(retSrc);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
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
			e.printStackTrace();
			return null;
		}
		Object content = url.getContent();
		InputStream is = (InputStream) content;
		Drawable d = Drawable.createFromStream(is, "src");
		return d;
	}

	/**
	 * 获取bitmap图片
	 * 
	 * @param url
	 *            图片uri
	 * @return bitmap
	 * @throws IOException
	 */
	public Bitmap getBitmap(String url) throws IOException {
		Bitmap bitmap = null;
		HttpGet request = new HttpGet(url);
		HttpResponse response = httpClient.execute(request);
		InputStream is = response.getEntity().getContent();
		bitmap = BitmapFactory.decodeStream(new BufferedInputStream(is));
		if (bitmap == null) {
			throw new IOException();
		} else {
			return bitmap;
		}

	}

	/**
	 * 通用get方法
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public HttpResponse get(String url) throws IOException {
		HttpGet request = new HttpGet(url);
		return httpClient.execute(request);
	}
}

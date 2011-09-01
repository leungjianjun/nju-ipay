package com.ipay.server.security;

public final class ExceptionMessage {
	
	public static final String ACCOUNT_OR_PASSWORD_ERROR = "用户名或密码错误";
	
	public static final String CLIENT_NOT_FOUND = "不存在该用户";
	
	public static final String MARKET_NOT_FOUND = "不存在该商场";
	
	public static final String PRODUC_NOT_FOUND = "不存在该商品";

	public static final String RESOURCE_NOT_FOUND = "不存在该资源";
	
	public static final String PRODUCT_NOT_IN_THE_SAME_MARKET = "商品不是在同一个商场";
	
	public static final String BANK_SERVER_NETWORK_ERROR = "银行服务器链接异常";
	
	public static final String PAYREQUEST_BANK_ERROR = "银行服务器返回数据错误";
	
	public static final String PAYRESPONSE_SIGN_ERROR = "银行返回支付请求没有通过签名";
	
	public static final String TRANSACTION_ID_NOT_CORRECT = "交易号不正确";
	
	public static final String DATA_FORMATE_ERROR = "数据格式错误";
	
	public static final String ENCRYPT_PRIVATEKEY_NOT_FOUND = "找不到加密的私钥";
	
	public static final String PUBLICKEY_NOT_FOUND = "无法获取公钥";
	
	public static final String BANK_TRANSZCTION_FORMATE_ERROR = "银行数据内容错误";
	
	public static final String BANK_INTERNAL_ERROR = "银行服务器出现错误";

}

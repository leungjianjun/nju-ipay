package com.ipay.server.bankproxy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.common.collect.Maps;
import com.ipay.server.security.ExceptionMessage;

public class HttpConnection {
	
	 private static final String SERVLET_POST = "POST" ;
     private static final String SERVLET_GET = "GET" ;
     private static final String SERVLET_DELETE = "DELETE" ;
     private static final String SERVLET_PUT = "PUT" ;
     
     private static ObjectMapper mapper = new ObjectMapper();
     
     private static String prepareParam(Map<String,Object> paramMap){
         StringBuffer sb = new StringBuffer();
         if(paramMap.isEmpty()){
             return "" ;
         }else{
             for(String key: paramMap.keySet()){
                 String value = (String)paramMap.get(key);
                 if(sb.length()<1){
                     sb.append(key).append("=").append(value);
                 }else{
                     sb.append("&").append(key).append("=").append(value);
                 }
             }
             return sb.toString();
         }
     }
     
	
	public static PayResponse doPayRequestPost(String urlStr,PayRequest payRequest) throws BankProxyServerException{
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(SERVLET_POST);
			conn.setDoInput(true);
	        conn.setDoOutput(true);
	        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			OutputStream os = conn.getOutputStream();
			mapper.writeValue(os, payRequest);
			os.close();
			
			InputStream is = conn.getInputStream();
			PayResponse payResponse = mapper.readValue(is, PayResponse.class);
			return payResponse;
		} catch (MalformedURLException e) {
			throw new BankProxyServerException(ExceptionMessage.BANK_SERVER_NETWORK_ERROR);
		} catch (IOException e) {
			throw new BankProxyServerException("发送数据错误");
		} finally {
			conn.disconnect();
		}
	}
	
	public static PayResponse doPayRequestSignPost(String urlStr,PayRequestSign payRequestSign) throws BankProxyServerException{
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(SERVLET_POST);
			conn.setDoInput(true);
	        conn.setDoOutput(true);
	        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			OutputStream os = conn.getOutputStream();
			mapper.writeValue(os, payRequestSign);
			os.close();
			
			InputStream is = conn.getInputStream();
			PayResponse payResponse = mapper.readValue(is, PayResponse.class);
			return payResponse;
		} catch (MalformedURLException e) {
			throw new BankProxyServerException(ExceptionMessage.BANK_SERVER_NETWORK_ERROR);
		} catch (IOException e) {
			throw new BankProxyServerException("发送数据错误");
		} finally {
			conn.disconnect();
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		/*Map<String,Map<String,byte[]>> contents = Maps.newHashMap();
		contents.put("encryptData", "qwew");
		contents.put("signData", "dfdsa");
		HttpConnection.doJsonPost(Configure.PayRequest(), contents);*/
	}
	
	public static byte[] doGet(String urlStr,Map<String,Object> paramMap) throws IOException{
		String paramStr = prepareParam(paramMap);
        if(paramStr != null && paramStr.trim().length()>0){
            urlStr +="?"+paramStr;
        }
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod(SERVLET_GET);
        conn.connect();
        byte[] contents = new byte[conn.getContentLength()];
        conn.getInputStream().read(contents);
        conn.disconnect();
        return contents;
	}
	
	public static Object doJsonGet(String urlStr,Map<String,Object> paramMap) throws IOException{
		String paramStr = prepareParam(paramMap);
        if(paramStr != null && paramStr.trim().length()>0){
            urlStr +="?"+paramStr;
        }
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod(SERVLET_GET);
        conn.connect();
        
        InputStream is = conn.getInputStream();
        Map<String,Object> result = mapper.readValue(is, Map.class);
        return result;
	}

}

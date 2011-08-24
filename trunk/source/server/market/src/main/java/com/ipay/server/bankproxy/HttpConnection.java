package com.ipay.server.bankproxy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class HttpConnection {
	
	 private static final String SERVLET_POST = "POST" ;
     private static final String SERVLET_GET = "GET" ;
     private static final String SERVLET_DELETE = "DELETE" ;
     private static final String SERVLET_PUT = "PUT" ;
     
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
	
	public void doPost(String urlStr,Map<String,Object> paramMap) throws IOException{
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
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
        return contents;
	}

}

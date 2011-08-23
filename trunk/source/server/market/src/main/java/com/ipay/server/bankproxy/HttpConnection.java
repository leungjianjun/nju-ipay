package com.ipay.server.bankproxy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class HttpConnection {
	
	public void doPost(String urlStr,Map<String,Object> paramMap) throws IOException{
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	}

}

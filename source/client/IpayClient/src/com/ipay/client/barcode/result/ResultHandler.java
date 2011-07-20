package com.ipay.client.barcode.result;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import android.app.Activity;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;


public class ResultHandler {
	
	private static final String TAG = ResultHandler.class.getSimpleName();
	
	private static final DateFormat DATE_FORMAT;
	static{
		DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
	}
	private static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
	
	private final ParsedResult result;
	private final Activity activity;
	private Result rawResult;
	
	ResultHandler(Activity activity,ParsedResult result){
		this(activity,result,null);
	}
	
	ResultHandler(Activity activity,ParsedResult result,Result rawResult){
		this.activity = activity;
		this.result = result;
		this.rawResult = rawResult;
	}
	
	public ParsedResult getResult(){
		return result;
	}
	
	//返回扫描结果
	public String getDisplayContents(){
		String contents = result.getDisplayResult();
		return contents.replace("\r", "");
	}

}

package com.ipay.client.barcode.result;

import android.app.Activity;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import com.google.zxing.client.result.ResultParser;

public final class ResultHandlerFactory {

	private ResultHandlerFactory(){
	}
	
	public static ResultHandler makeResultHandler(Activity activity, Result rawResult){
		ParsedResult result = parseResult(rawResult);
		ParsedResultType type = result.getType();
		
		if(type.equals(ParsedResultType.PRODUCT))
			return new ProductResultHandler(activity,result,rawResult);
		if(type.equals(ParsedResultType.ISBN))
			return new ISBNResultHandler(activity,result,rawResult);
		else
			return new TextResultHandler(activity,result,rawResult);
	}
	
	private static ParsedResult parseResult(Result rawResult){
		return ResultParser.parseResult(rawResult);
	}
}

package com.ipay.client.barcode.result;

import android.app.Activity;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;

public class ISBNResultHandler extends ResultHandler{

	ISBNResultHandler(Activity activity, ParsedResult result, Result rawResult) {
		super(activity, result, rawResult);
	}

}

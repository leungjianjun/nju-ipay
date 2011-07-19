package com.ipay.client.barcode;

import java.util.Hashtable;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.ipay.client.R;
import com.ipay.client.barcode.camera.CameraManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class DecodeHandler extends Handler{
	
	private static final String TAG = DecodeHandler.class.getSimpleName();
	
	private final CaptureActivity activity;
	private final MultiFormatReader multiFormatReader;
	private boolean running = true;
	
	DecodeHandler(CaptureActivity activity,Hashtable<DecodeHintType,Object> hints){
		multiFormatReader = new MultiFormatReader();
		multiFormatReader.setHints(hints);
		this.activity = activity;
	}

	//处理消息decode和quit,decode来自previewcallback
	@Override
	public void handleMessage(Message message){
		if(!running)
			return;
		switch(message.what){
		case R.id.decode:
			decode((byte[]) message.obj,message.arg1,message.arg2);
			break;
		case R.id.quit:
			running = false;
			Looper.myLooper().quit();
			break;
		}
	}

	//解码,并把结果返回给CaptureActivityHandler
	private void decode(byte[] data,int width,int height){
		long start = System.currentTimeMillis();
		Result rawResult = null;
		PlanarYUVLuminanceSource source = CameraManager.get().buildLuminanceSource(data, width, height);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		try{
			//根据图像返回结果
			rawResult = multiFormatReader.decodeWithState(bitmap);
		}catch(ReaderException re){
		}finally{
			multiFormatReader.reset();
		}
		
		//结果不为空就传回decode_succeeded
		if(rawResult != null){
			long end = System.currentTimeMillis();
			Log.d(TAG, "Found barcode in "+(end - start)+" ms");
			Message message = Message.obtain(activity.getHandler(), R.id.decode_succeeded,rawResult);
			Bundle bundle = new Bundle();
			bundle.putParcelable(DecodeThread.BARCODE_BITMAP, source.renderCroppedGreyscaleBitmap());
			message.setData(bundle);
			message.sendToTarget();
		}else{//否则传回decode_failed
			Message message = Message.obtain(activity.getHandler(), R.id.decode_failed);
			message.sendToTarget();
		}
	}
	
}

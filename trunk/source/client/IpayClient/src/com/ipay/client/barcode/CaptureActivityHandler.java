package com.ipay.client.barcode;

import java.util.Vector;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.ipay.client.R;
import com.ipay.client.barcode.camera.CameraManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class CaptureActivityHandler extends Handler{
	
	private static final String TAG = CaptureActivityHandler.class.getSimpleName();
	
	private final CaptureActivity activity;
	private final DecodeThread decodeThread;
	private State state;
	
	private enum State{
		PREVIRE,
		SUCCESS,
		DONE
	}

	CaptureActivityHandler(CaptureActivity activity,Vector<BarcodeFormat> decodeFormats,
								String characterSet){
		this.activity = activity;
		decodeThread = new DecodeThread(activity,decodeFormats,characterSet,
						new ViewfinderResultPointCallback(activity.getViewfinderView()));
		decodeThread.start();
		state = State.SUCCESS;
		
		//开始把图像画在屏幕上
		CameraManager.get().startPreview();
		//开始取图像和调焦
		restartPreviewAndDecode();
	}
	
	//处理消息
	@Override
	public void handleMessage(Message message){
		switch(message.what){
		case R.id.auto_focus:
			if(state == State.PREVIRE)
				CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
			break;
		case R.id.restart_preview:
			Log.d(TAG,"Got restart preview message");
			restartPreviewAndDecode();
			break;
		case R.id.decode_succeeded:
			Log.d(TAG,"Got restart preview message");
			state = State.SUCCESS;
			Bundle bundle = message.getData();
			Bitmap barcode = bundle == null ? null:
				(Bitmap) bundle.getParcelable(DecodeThread.BARCODE_BITMAP);
			activity.handleDecode((Result)message.obj,barcode);
			break;
		case R.id.decode_failed:
			state = State.PREVIRE;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
			break;
		case R.id.return_scan_result:
			Log.d(TAG,"Got return scan result message");
			activity.setResult(Activity.RESULT_OK,(Intent)message.obj);
			activity.finish();
			break;
		}
	}
	
	//退出
	public void quitSynchronously(){
		state = State.DONE;
		CameraManager.get().stopPreview();
		//把退出消息传给decodehandler
		Message quit = Message.obtain(decodeThread.getHandler(),R.id.quit);
		quit.sendToTarget();
		try{
			decodeThread.join();
		}catch(InterruptedException e){
			
		}
	}
	
	//请求当前帧，请求自动调焦
	private void restartPreviewAndDecode(){
		if(state == State.SUCCESS){
			state = State.PREVIRE;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),R.id.decode);
			CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
		}
	}
}

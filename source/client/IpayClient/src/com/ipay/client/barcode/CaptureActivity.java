package com.ipay.client.barcode;



import java.io.IOException;
import java.util.Vector;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.ipay.client.R;
import com.ipay.client.barcode.camera.CameraManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Handler;
import com.ipay.client.barcode.result.*;

public class CaptureActivity extends Activity implements SurfaceHolder.Callback{

	private static final String TAG = CaptureActivity.class.getSimpleName();
	
	//CaptureActivity的消息
	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private TextView statusView;
	//上一次的扫描结果
	private Result lastResult;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private InactivityTimer inactivityTimer;
	private String characterSet;
	
	ViewfinderView getViewfinderView(){
		return viewfinderView;
	}
	
	public Handler getHandler(){
		return handler;
	}
	
	@Override
	public void onCreate(Bundle icicle){ 
		super.onCreate(icicle);
		
		//设为全屏
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.capture);
		
		//初始化相机
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		statusView = (TextView) findViewById(R.id.status_view);
		handler = null;
		lastResult = null;
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}
	 
	@Override
	protected void onResume(){
		super.onResume();
		resetStatusView();
		
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if(hasSurface)
			initCamera(surfaceHolder);
		else{
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		inactivityTimer.onResume();
	}

	@Override
	protected void onPause(){
		super.onPause();
		if(handler != null){
			handler.quitSynchronously();
			handler = null;
		}
		inactivityTimer.onPause();
		CameraManager.get().closeDriver();
	}
	
	@Override
	protected void onDestroy(){
		inactivityTimer.shutdown();
		super.onDestroy();
	}
	
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if(!hasSurface){
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;	
	}
	
	//由CaptureActivityandler调用
	public void handleDecode(Result rawResult,Bitmap barcode){
		inactivityTimer.onActivity();
		lastResult = rawResult;
		ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this,rawResult);
		String resultContent = resultHandler.getDisplayContents();
		//返回结果
		Intent intent = getIntent();
		Bundle bundle = new Bundle();
		bundle.putString("BARCODE_RESULT", resultContent);
		intent.putExtras(bundle);
		setResult(RESULT_OK,intent);
		finish();
	}
	
	//重新设置状态视图
	public void resetStatusView(){
		statusView.setText(R.string.msg_default_status);
		statusView.setVisibility(View.VISIBLE);
		viewfinderView.setVisibility(View.VISIBLE);
		lastResult = null;
	}
	
	//初始化相机
	private void initCamera(SurfaceHolder surfaceHolder){
		try{
			CameraManager.get().openDriver(surfaceHolder);
			
			//初始化handler
			if(handler == null)
				handler = new CaptureActivityHandler(this,decodeFormats,characterSet);
		}catch(IOException ioe){
			Log.w(TAG,ioe);
			displayFrameworkBugMessageAndExit();
		}catch(RuntimeException e){
			Log.w(TAG, "Unexpected error initializating camera",e);
			displayFrameworkBugMessageAndExit();
		}
	}
	
	//遇到错误，退出
	private void displayFrameworkBugMessageAndExit(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.app_name));
		builder.setMessage(getString(R.string.msg_camera_framework_bug));
		builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
		builder.setOnCancelListener(new FinishListener(this));
		builder.show();
	}

}

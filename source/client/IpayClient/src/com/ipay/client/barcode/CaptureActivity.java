package com.ipay.client.barcode;

import java.util.Vector;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class CaptureActivity extends Activity implements SurfaceHolder.Callback{

	private static final String TAG = CaptureActivity.class.getSimpleName();
	
	//CaptureActivity的消息处理
	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private TextView statusView;
	private View resultView;
	private Result lastResult;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private InactivityTimer inactivityTimer;
	
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
		
		setContentView(R.layout);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}

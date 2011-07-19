package com.ipay.client.barcode;



import java.util.Vector;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.ipay.client.R;
import com.ipay.client.barcode.camera.CameraManager;

import android.app.Activity;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Handler;

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

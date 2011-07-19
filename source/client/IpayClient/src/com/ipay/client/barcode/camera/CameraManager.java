package com.ipay.client.barcode.camera;

import java.io.IOException;

import com.ipay.client.barcode.PlanarYUVLuminanceSource;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

public class CameraManager {
	
	private static final String TAG = CameraManager.class.getSimpleName();
	
	private static final int MIN_FRAME_WIDTH = 240;
	private static final int MIN_FRAME_HEIGHT = 240;
	private static final int MAX_FRAME_WIDTH = 480;
	private static final int MAX_FRAME_HEIGHT = 480;
	
	private static CameraManager cameraManager;
	
	static final int SDK_INT;
	static {
		int sdkInt;
		try{
			sdkInt = Integer.parseInt(Build.VERSION.SDK);
		}catch (NumberFormatException nfe){
			//为了安全起见
			sdkInt = 10000;
		}
		SDK_INT = sdkInt;
	}

	private final Context context;
	private final CameraConfigurationManager configManager;
	private Camera camera;
	private Rect framingRect;
	private Rect framingRectInPreview;
	boolean initialized;
	boolean previewing;
	boolean reverseImage;
	private final boolean useOneShotPreviewCallback;
	
	private final PreviewCallback previewCallback;
	private final AutoFocusCallback autoFocusCallback;
	
	private CameraManager(Context context){
		this.context = context;
		this.configManager = new CameraConfigurationManager(context);
		
		useOneShotPreviewCallback = Integer.parseInt(Build.VERSION.SDK)>3;
		
		previewCallback = new PreviewCallback(configManager,useOneShotPreviewCallback);
		autoFocusCallback = new AutoFocusCallback();
	}
	
	public static CameraManager get(){
		return cameraManager;
	}
	
	public static void init(Context context){
		if(cameraManager == null)
			cameraManager = new CameraManager(context);
	}

	//打开相机，初始硬件参数
	public void openDriver(SurfaceHolder holder) throws IOException{
		if(camera == null){
			camera = Camera.open();
			if(camera == null)
				throw new IOException();
		}
		//把相应Surfaceview的surfaceHolder给相机
		camera.setPreviewDisplay(holder);
		
		if(!initialized){
			initialized = true;
			configManager.initFromCameraParameters(camera);
		}
		configManager.setDesiredCameraParameters(camera);
	}
	
	//关闭相机
	public void closeDriver(){
		if(camera!=null){
			//关闭前灯
			FlashlightManager.disableFlashlight();
			camera.release();
			camera = null;
			
			framingRect = null;
			framingRectInPreview = null;
		}
	}
	
	//开始把图像画到屏幕上
	public void startPreview(){
		if(camera != null && !previewing){
			camera.startPreview();
			previewing = true;
		}
	}
	
	//停止画图像
	public void stopPreview(){
		if(camera != null && previewing){
			if(!useOneShotPreviewCallback)
				camera.setPreviewCallback(null);
			
			camera.stopPreview();
			previewCallback.setHandler(null, 0);
			autoFocusCallback.setHandler(null, 0);
			previewing = false;
		}
	}

	//得到一个简单的帧，并通过handler返回
	public void requestPreviewFrame(Handler handler, int message){
		if(camera != null && previewing){
			previewCallback.setHandler(handler, message);
			
			if(useOneShotPreviewCallback)
				camera.setOneShotPreviewCallback(null);
			else
				camera.setOneShotPreviewCallback(previewCallback);
		}
	}
	
	//使相机自动对焦
	public void requestAutoFocus(Handler handler, int message){
		if(camera != null && previewing){
			autoFocusCallback.setHandler(handler, message);
			camera.autoFocus(autoFocusCallback);
		}
	}
	
	//得到一个矩形框，用户必须把条形码放入框中
	public Rect getFramingRect(){
		if(framingRect == null){
			if(camera == null)
				return null;
			
			Point screenResolution = configManager.getScreenResolution();
			
			int width = screenResolution.x * 3 / 4;
			if(width < MIN_FRAME_WIDTH)
				width = MIN_FRAME_WIDTH;
			else if(width > MAX_FRAME_WIDTH)
				width = MAX_FRAME_WIDTH;
			
			int height = screenResolution.y * 3 / 4;
			if(height < MIN_FRAME_HEIGHT)
				height = MIN_FRAME_HEIGHT;
			else if(height > MAX_FRAME_HEIGHT)
				height = MAX_FRAME_HEIGHT;
			
			int leftOffset = (screenResolution.x - width) / 2;
			int topOffset = (screenResolution.y - height) / 2;
			framingRect = new Rect(leftOffset,topOffset,leftOffset + width,topOffset + height);
			Log.d(TAG,"Calculated framing rect: "+framingRect);
		}
		return framingRect;
	}
	
	//和getFramingRect()一样，只是坐标是相机的坐标
	public Rect getFramingRectInPreview(){
		if(framingRectInPreview == null){
			Rect rect = new Rect(getFramingRect());
			Point cameraResolution = configManager.getCameraResolution();
			Point screenResolution = configManager.getScreenResolution();
			rect.left = rect.left * cameraResolution.x / screenResolution.x;
			rect.right = rect.right * cameraResolution.x / screenResolution.x;
			rect.top = rect.top * cameraResolution.y / screenResolution.y;
			rect.bottom = rect.bottom * cameraResolution.y / screenResolution.y;
			framingRectInPreview = rect;
		}
		return framingRectInPreview;
	}
	
	//通过传入一帧，返回一个PlanarYUVLuminanceSource
	public PlanarYUVLuminanceSource buildLuminanceSource(byte[] data, int width, int height){
		Rect rect = getFramingRectInPreview();
		int previewFormat = configManager.getPreviewFormat();
		String previewFormatString = configManager.getPreviewFormatString();
		
		switch(previewFormat){
		case PixelFormat.YCbCr_420_SP:
		case PixelFormat.YCbCr_422_SP:
			return new PlanarYUVLuminanceSource(data,width,height,rect.left,rect.top,
												rect.width(),rect.height(),reverseImage);
			default:
				if("yuv420p".equals(previewFormatString))
					return new PlanarYUVLuminanceSource(data,width,height,rect.left,rect.top,
												rect.width(),rect.height(),reverseImage);
		}
		throw new IllegalArgumentException("Unsupported picture format: "+
										previewFormat+"/"+previewFormatString);
	}
	
	
	
	
	
	
	
}

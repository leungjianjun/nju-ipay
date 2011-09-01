package com.ipay.client.ui.component;


import com.ipay.client.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

public class ProgressBarFeedback extends Feedback{
	private static final String TAG="ProgressBarFeedback";
	
	private ProgressBar progressBar;
	
	public ProgressBarFeedback(Context context){
		super(context);
		Log.d(TAG, "created");
		progressBar=(ProgressBar)((Activity)context).findViewById(R.id.navi_bar_progress_bar);
	}
	
	@Override
	public void start(String msg) {
		Log.d(TAG, "start");
		progressBar.setProgress(20);
	}

	@Override
	public void update(Object progress) {
		Log.d(TAG, "update progress: "+ progress);
		progressBar.setProgress((Integer)progress);
		
	}

	@Override
	public void succeed(String msg) {
		progressBar.setProgress(100);
		reset();
	}

	@Override
	public void fail(String msg) {
		reset();
		showMessage(msg);
	}

	@Override
	public void cancel() {
		reset();
	}
	
	private void reset(){
		progressBar.setProgress(0);
	}
	




	
	
	

}

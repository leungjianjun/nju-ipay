package com.ipay.client.ui.component;

import android.content.Context;
import android.widget.Toast;

/**
 * 反馈接口
 * @author tangym
 *
 */
public abstract class Feedback {
	
	protected Context context;
	
	public Feedback(Context context){
		this.context=context;
	}
		
	/**任务开始
	 * @param msg
	 */
	public abstract void start(String msg);
	
	/**
	 * 更新进度
	 * @param progress 
	 */
	public abstract void update(Object progress);
	
	/**任务成功
	 * 
	 * @param msg
	 */
	public abstract void succeed(String msg);
	
	/**任务失败
	 * @param msg 失败原因
	 */
	public abstract void fail(String msg);
	
	/**
	 * 任务取消
	 */
	public abstract void cancel();
	
	/**显示Toast
	 * 
	 * @param msg 需要显示的信息
	 */
	protected void  showMessage(String msg) {
		Toast toast=Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.show();
		
	}
}

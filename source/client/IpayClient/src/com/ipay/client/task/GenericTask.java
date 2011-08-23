/**
 * 
 */
package com.ipay.client.task;

import android.os.AsyncTask;

/**
 * @author tangym
 * 
 */
public abstract class GenericTask extends
		AsyncTask<TaskParams, Integer, TaskResult> {

	private static final String TAG = "GenericTask";

	protected TaskListener listener;

	public GenericTask() {
		super();
	}

	public void setTaskListener(TaskListener listener) {
		this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (listener != null)
			listener.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		if (listener != null) {
			listener.onProgressUpdate(values);
		}
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		if (listener != null) {
			listener.onCancelled();
		}
	}

	@Override
	protected void onPostExecute(TaskResult result) {
		super.onPostExecute(result);
		if (listener != null) {
			listener.onPostExecute(result);
		}
	}

}

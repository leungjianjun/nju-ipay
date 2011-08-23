/**
 * 
 */
package com.ipay.client.task;

/**
 * @author tangym
 *
 */
public interface TaskListener {
	
	public void onPreExecute();
	
	public void onProgressUpdate(Integer...values);
	
	public void onPostExecute(TaskResult result);
	
	public void onCancelled();

}

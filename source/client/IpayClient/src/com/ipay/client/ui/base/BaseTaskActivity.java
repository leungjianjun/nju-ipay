/**
 * 
 */
package com.ipay.client.ui.base;

import android.os.Bundle;

import com.ipay.client.task.GenericTask;
import com.ipay.client.task.TaskListener;
import com.ipay.client.ui.component.Feedback;

/**
 * @author tangym
 *
 */
public abstract class BaseTaskActivity extends BaseActivity {
	
	protected GenericTask task;
	protected TaskListener taskListener;
	protected Feedback feedback;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	abstract protected void doTask();
	
	
	abstract protected void update();
}

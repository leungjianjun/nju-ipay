/*    Copyright 2011 Popcorn

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.ipay.client.ui.base;

import com.ipay.client.task.GenericTask;
import com.ipay.client.task.TaskListener;
import com.ipay.client.ui.component.Feedback;
import com.ipay.client.ui.component.FeedbackFactory.FeedbackType;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask.Status;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * 列表Activity的父类
 * 1. 
 * @author tangym
 *
 */
public abstract class BaseListActivity  extends BaseActivity{
	
	private static final String TAG="BaseListActivity";
	
	protected ListView listView;
	
	protected GenericTask task;
	
	protected TaskListener taskListener;
	
	protected Feedback feedback;
	
	
	/**
	 * 更新listview的adapter
	 */
	abstract protected void  update();
	
	/**
	 * 执行后台网络任务,从服务器获取数据
	 */
	abstract protected void  doRetrive();
	
	/**
	 * 绑定列表item的点击事件
	 */
	abstract protected void bindItemOnClickListener();
	
	
	
	
	protected void  init() {
		
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//取消所有在执行的任务;
		if(task!=null&&task.getStatus()==Status.RUNNING){
			task.cancel(true);
		}
	}
}

/**
 * 
 */
package com.ipay.client;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ipay.client.ui.base.BaseListActivity;
import com.ipay.client.ui.base.Pageable;
import com.ipay.client.ui.component.NaviBarBack;

/**
 * @author tangym
 *
 */
public class BriefHistoryActivity extends BaseListActivity implements Pageable {
	
	private int page=1;
	private View listHeader;
	private View listFooter;
	ProgressBar progressBar;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
	}
	
	
	protected void initViews(){
		setContentView(R.layout.brief_record);
		naviBar=new NaviBarBack(this);
		naviBar.setTitle(R.string.brief_history_title);
		
		listView=(ListView)findViewById(R.id.brief_record_list);
		listHeader = View.inflate(this, R.layout.listview_header, null);
		listFooter = View.inflate(this, R.layout.listview_footer, null);
		listView.addHeaderView(listHeader, null, true);
		listView.addFooterView(listFooter, null, true);
		progressBar = (ProgressBar) listFooter
				.findViewById(R.id.list_footer_progress_bar);

	}
	

	
	@Override
	protected void update() {
		

	}

	
	@Override
	protected void doRetrive() {
		

	}


	@Override
	protected void bindItemOnClickListener() {
		

	}


	@Override
	public void nextPage() {
		page++;
		doRetrive();
		
	}


	@Override
	public int getCurrentPage() {
		return page;
	}

}

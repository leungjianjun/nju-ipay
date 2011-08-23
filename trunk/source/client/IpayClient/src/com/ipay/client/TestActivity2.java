/**
 * 
 */
package com.ipay.client;

import com.ipay.client.ui.component.DialogFeedback;
import com.ipay.client.ui.component.Feedback;
import com.ipay.client.ui.component.FeedbackFactory;
import com.ipay.client.ui.component.FeedbackFactory.FeedbackType;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author tangym
 * 
 */
public class TestActivity2 extends Activity {

	private Button start;
	private Button success;
	private Button fail;
	private Feedback bar;
	private Feedback dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test2);
		TextView text = (TextView) findViewById(R.id.test2);
		text.setText("this is tab2");

		start = (Button) findViewById(R.id.test_start);
		success = (Button) findViewById(R.id.test_success);
		fail = (Button) findViewById(R.id.test_fail);
		FeedbackFactory factory = new FeedbackFactory();
		bar =factory.create(FeedbackType.PROGRESSBAR, this);
		dialog=factory.create(FeedbackType.DIALOG, this);
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bar.start("");
				dialog.start("开始");
			}
		});

		success.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bar.succeed("");
				//dialog.update(100);
			}
		});
		
		fail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bar.fail("failed");
				dialog.fail("失败");
			}
		});

	}

}

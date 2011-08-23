package com.ipay.client.ui.component;

import android.app.ProgressDialog;
import android.content.Context;

public class DialogFeedback extends Feedback {

	private ProgressDialog progressDialog;

	public DialogFeedback(Context context) {
		super(context);
		progressDialog = new ProgressDialog(context);
		progressDialog.setIndeterminate(true);
	}

	@Override
	public void start(String msg) {
		progressDialog.setMessage(msg);
		progressDialog.show();
	}

	@Override
	public void update(int progress) {
		progressDialog.setMessage("" + progress);
	}

	@Override
	public void succeed(String msg) {
		progressDialog.setMessage(msg);
		progressDialog.dismiss();

	}

	@Override
	public void fail(String msg) {
		progressDialog.dismiss();
		showMessage(msg);

	}

	@Override
	public void cancel() {
		progressDialog.dismiss();
	}
}

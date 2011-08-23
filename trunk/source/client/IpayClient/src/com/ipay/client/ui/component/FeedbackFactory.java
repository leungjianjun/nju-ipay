package com.ipay.client.ui.component;

import android.content.Context;

public class FeedbackFactory {

	public static enum FeedbackType {
		PROGRESSBAR, DIALOG
	}
	

	public Feedback create(FeedbackType type, Context context) {
		switch (type) {
		case PROGRESSBAR:
			return new ProgressBarFeedback(context);
		case DIALOG:
			return new DialogFeedback(context);
		default:
			return null;
		}
	}
}

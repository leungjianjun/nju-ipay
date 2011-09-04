/**
 * 
 */
package com.ipay.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ipay.client.ui.base.BaseActivity;
import com.ipay.client.ui.component.NaviBarBack;

/**
 * @author tangym
 *
 */
public class ModifyAccountInfoActivity extends BaseActivity {
	
	
	private EditText accountEdit;
	private EditText phoneEdit;
	private EditText oldPassEdit;
	private EditText newPassEdit;
	private EditText newPassEdit2;
	private Button submitBtn;
	private Button cancelBtn;
	
	private String account;
	private String phoneNum;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();
		account=intent.getStringExtra("account");
		phoneNum=intent.getStringExtra("phone_num");
		initViews();
		
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		update();
	}
	
	
	protected void initViews(){
		setContentView(R.layout.modify_account);
		naviBar=new NaviBarBack(this);
		naviBar.setTitle(R.string.modify_account_info_title);
		accountEdit=(EditText)findViewById(R.id.modify_account_account_edit);
		phoneEdit=(EditText)findViewById(R.id.modify_account_phone_edit);
		oldPassEdit=(EditText)findViewById(R.id.modify_account_old_pass_edit);
		newPassEdit=(EditText)findViewById(R.id.modify_account_new_pass_edit);
		newPassEdit2=(EditText)findViewById(R.id.modify_account_new_pass_edit2);
		submitBtn=(Button)findViewById(R.id.modify_account_submit);
		cancelBtn=(Button)findViewById(R.id.modify_account_cancel);
		
		submitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSubmit();
			}
		});
		
		cancelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void update(){
		accountEdit.setText(account);
		phoneEdit.setText(phoneNum);
	}
	
	private void onSubmit(){
		
	}
	
	private boolean checkAccount(){
		
		return false;
	}
	
	private boolean checkPhoneNum(){
		
		return false;
	}
	
	private boolean checkPass(){
		
		return false;
	}
	
	
	

}

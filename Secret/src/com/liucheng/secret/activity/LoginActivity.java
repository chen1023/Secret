package com.liucheng.secret.activity;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.liucheng.secret.Config;
import com.liucheng.secret.R;
import com.liucheng.secret.net.GetCode;
import com.liucheng.secret.net.Login;
import com.liucheng.secret.utils.MD5Util;

public class LoginActivity extends Activity {
	private EditText etPhoneNum, etCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		etPhoneNum = (EditText) findViewById(R.id.etPhoneNum);
		etCode = (EditText) findViewById(R.id.etCode);

		findViewById(R.id.btnGetCode).setOnClickListener(
				new View.OnClickListener() {

					public void onClick(View v) {
						String phoneNum = etPhoneNum.getText().toString();
						if (TextUtils.isEmpty(phoneNum)) {
							Toast.makeText(LoginActivity.this, "电话号码不能为空",
									Toast.LENGTH_SHORT).show();
							return;
						}
						final ProgressDialog pd = ProgressDialog.show(
								LoginActivity.this, "获取验证码", "正在发送，请稍后");
						new GetCode(phoneNum, new GetCode.SuccessCallback() {
							public void onSuccess() {
								pd.dismiss();
							}
						}, new GetCode.FailCallback() {

							public void onFail() {
								pd.dismiss();
								Toast.makeText(LoginActivity.this, "发送失败",
										Toast.LENGTH_SHORT).show();
							}
						});
					}
				});
		findViewById(R.id.btnLogin).setOnClickListener(
				new View.OnClickListener() {
					public void onClick(View v) {
						final String phone = etPhoneNum.getText().toString();
						if (TextUtils.isEmpty(phone)) {
							Toast.makeText(LoginActivity.this, "电话号码不能为空",
									Toast.LENGTH_SHORT).show();
							return;
						}
						String code = etCode.getText().toString();
						if (TextUtils.isEmpty(code)) {
							Toast.makeText(LoginActivity.this, "验证码不能为空",
									Toast.LENGTH_SHORT).show();
							return;
						}
						final ProgressDialog pd = ProgressDialog.show(
								LoginActivity.this, "登录", "正在登录，请稍后");
						try {
							new Login(MD5Util.md5(phone), code,
									new Login.SuccessCallback() {

										public void onSuccess(String token) {
											Config.cacheToken(
													LoginActivity.this, token);
											Config.cachePhoneNum(
													LoginActivity.this, phone);
											pd.dismiss();
											Intent i = new Intent(
													LoginActivity.this,
													TimeLineActivity.class);
											i.putExtra(Config.KEY_TOKEN, token);
											i.putExtra(Config.KEY_PHONE_NUM,
													phone);
											startActivity(i);

											finish();
										}
									}, new Login.FailCallback() {

										public void onFail() {
											pd.dismiss();
											Toast.makeText(LoginActivity.this,
													"登录失败", Toast.LENGTH_SHORT)
													.show();
										}
									});
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						}
					}
				});
	}
}
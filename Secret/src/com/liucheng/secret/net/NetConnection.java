package com.liucheng.secret.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.liucheng.secret.Config;

import android.os.AsyncTask;
import android.util.Log;

public class NetConnection {
	
	/**
	 * 网络通信基类
	 * @param url 服务器地址
	 * @param method 通信类型（get，post）
	 * @param successCallback 成功状态下回调方法
	 * @param failCallback 失败状态下回调方法
	 * @param strings 参数键值对
	 */
	public NetConnection(final String url, final HttpMethod method,
			final SuccessCallback successCallback,
			final FailCallback failCallback, final String... strings) {
		//
		new AsyncTask<Void, Void, String>() {

			protected String doInBackground(Void... params) {
				//构建请求url的参数键值对
				StringBuffer paramStr = new StringBuffer();
				for (int i = 0; i < strings.length; i += 2) {
					paramStr.append(strings[i]).append("=")
							.append(strings[i + 1]).append("&");
				}
				Log.i("paramStr:", paramStr.toString());
				
				//根据请求类型，分别处理
				try {
					URLConnection uc;
					switch (method) {
					case POST:
						uc = new URL(url).openConnection();
						uc.setDoOutput(true);
						BufferedWriter bw = new BufferedWriter(
								new OutputStreamWriter(uc.getOutputStream(),
										Config.CHARSET));
						bw.write(paramStr.toString());//写出请求参数
						bw.flush();
						Log.i("URLConnection:post:", uc.getURL()+""+paramStr.toString());
						break;
					default:
						uc = new URL(url + "?" + paramStr.toString())
								.openConnection();//构造url
						Log.i("URLConnection:get:", uc.getURL().toString());
						break;
					}
					BufferedReader br = new BufferedReader(
							new InputStreamReader(uc.getInputStream(),
									Config.CHARSET));//获得返回结果
					String line = null;
					StringBuffer result = new StringBuffer();
					while ((line = br.readLine()) != null) {
						result.append(line);
					}
					Log.i("result:", result.toString());
					return result.toString();//返回的结果是onPostExecute方法的参数
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				if (result != null) {
					if (successCallback != null) {
						successCallback.onSuccess(result);
					}
				} else {
					if (failCallback != null) {
						failCallback.onFail();
					}
				}
				super.onPostExecute(result);
			}

		}.execute();
	}

	public static interface SuccessCallback {
		void onSuccess(String result);
	}

	public static interface FailCallback {
		void onFail();
	}
}
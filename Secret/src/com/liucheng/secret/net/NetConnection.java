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
	 * ����ͨ�Ż���
	 * @param url ��������ַ
	 * @param method ͨ�����ͣ�get��post��
	 * @param successCallback �ɹ�״̬�»ص�����
	 * @param failCallback ʧ��״̬�»ص�����
	 * @param strings ������ֵ��
	 */
	public NetConnection(final String url, final HttpMethod method,
			final SuccessCallback successCallback,
			final FailCallback failCallback, final String... strings) {
		//
		new AsyncTask<Void, Void, String>() {

			protected String doInBackground(Void... params) {
				//��������url�Ĳ�����ֵ��
				StringBuffer paramStr = new StringBuffer();
				for (int i = 0; i < strings.length; i += 2) {
					paramStr.append(strings[i]).append("=")
							.append(strings[i + 1]).append("&");
				}
				Log.i("paramStr:", paramStr.toString());
				
				//�����������ͣ��ֱ���
				try {
					URLConnection uc;
					switch (method) {
					case POST:
						uc = new URL(url).openConnection();
						uc.setDoOutput(true);
						BufferedWriter bw = new BufferedWriter(
								new OutputStreamWriter(uc.getOutputStream(),
										Config.CHARSET));
						bw.write(paramStr.toString());//д���������
						bw.flush();
						Log.i("URLConnection:post:", uc.getURL()+""+paramStr.toString());
						break;
					default:
						uc = new URL(url + "?" + paramStr.toString())
								.openConnection();//����url
						Log.i("URLConnection:get:", uc.getURL().toString());
						break;
					}
					BufferedReader br = new BufferedReader(
							new InputStreamReader(uc.getInputStream(),
									Config.CHARSET));//��÷��ؽ��
					String line = null;
					StringBuffer result = new StringBuffer();
					while ((line = br.readLine()) != null) {
						result.append(line);
					}
					Log.i("result:", result.toString());
					return result.toString();//���صĽ����onPostExecute�����Ĳ���
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
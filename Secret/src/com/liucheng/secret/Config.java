package com.liucheng.secret;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class Config {
	public static final String APP_ID="com.liucheng.secret";
	public static final String CHARSET = "UTF-8";
	
	public static final String KEY_TOKEN="token";
	public static final String KEY_ACTION="action";
	public static final String KEY_PHONE_NUM="phone";
	public static final String KEY_PHONE_MD5="phone_md5";
	public static final String KEY_STATUS="status";
	
	
	public static final int RESULT_STATUS_SUCCESS=1;
	public static final int RESULT_STATUS_Fail=0;
	public static final int RESULT_STATUS_INVALID_TOKEN=2;
	
	public static final String SERVER_URL="http://172.29.207.1:8080/TestServer/api.jsp";
	public static final String ACTION_GET_CODE="send_pass";
	public static final String ACTION_LOGIN = "login";
	public static final String KEY_CODE = "code";
	
	/**
	 * ªÒ»°ª∫¥Êµƒ¡Ó≈∆
	 * @param context
	 * @return
	 */
	public static String getCachedToken(Context context){	
		return context.getSharedPreferences(APP_ID, context.MODE_PRIVATE).getString(KEY_TOKEN, null);
	}
	
	public static String getCachedPhoneNum(Context context){	
		return context.getSharedPreferences(APP_ID, context.MODE_PRIVATE).getString(KEY_PHONE_NUM, null);
	}
	
	/**
	 * ±£¥Ê¡Ó≈∆
	 * @param context
	 * @param token
	 */
	public static void cacheToken(Context context,String token){
		Editor editor=context.getSharedPreferences(APP_ID, context.MODE_PRIVATE).edit();
		editor.putString(APP_ID, token);
		editor.commit();
	}
	
	public static void cachePhoneNum(Context context,String phoneNum){
		Editor editor=context.getSharedPreferences(APP_ID, context.MODE_PRIVATE).edit();
		editor.putString(KEY_PHONE_NUM, phoneNum);
		editor.commit();
	}
}

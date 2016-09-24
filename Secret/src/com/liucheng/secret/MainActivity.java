package com.liucheng.secret;

import com.liucheng.secret.activity.LoginActivity;
import com.liucheng.secret.activity.TimeLineActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		String token=Config.getCachedToken(this);
		if(token!=null){
			Intent i=new Intent(this,TimeLineActivity.class);
			i.putExtra(Config.KEY_TOKEN, token);
			startActivity(i);
		}else{
			startActivity(new Intent(this,LoginActivity.class));
		}
		finish();
	}

}

package com.sina.weibo.sdk.demo;


import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.openapi.UsersAPI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public final class WB_AllAAActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_aa);

    	// 获取当前已保存过的 Token
        Oauth2AccessToken mAccessToken = AccessTokenKeeper.readAccessToken(this);
        // 获取用户信息接口
        UsersAPI mUsersAPI = new UsersAPI(mAccessToken);
    	this.findViewById(R.id.addTemfriend).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	try {
            		Toast.makeText(getApplicationContext(), R.string.weibo_mystring_savesuccess, Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Toast.makeText(getApplicationContext(), R.string.weibo_mystring_saveerror, Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
            }
        });
    	
    	this.findViewById(R.id.next).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	 startActivity(new Intent(WB_AllAAActivity.this, WB_nextActivity.class));
            }
    	});
    }
	
}
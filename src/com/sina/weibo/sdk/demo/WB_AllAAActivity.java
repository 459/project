package com.sina.weibo.sdk.demo;


import weibo_mystring_service.DBservice;
import weibo_mystring_service.FileService;
import weibo_mystring_service.Temfriends;
import weibo_mystring_service.Temfriendsservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public final class WB_AllAAActivity extends Activity {
	
	private Temfriendsservice temfriendsservice;
	public void onCreate(Bundle savedInstanceState) {
		DBservice dbservice = new DBservice(getApplicationContext());
        dbservice.getWritableDatabase();
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_aa);  	
        temfriendsservice = new Temfriendsservice(this);//SQLite ;
        final Temfriends tf = new Temfriends();
        this.findViewById(R.id.addTemfriend).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               
                
            	String fnames = "";
            	String data;
            	String[] pdata = {"0","0","0"};//0为id 1为num 2为price
            	int price;
            	int percent;
            	FileService file=new FileService(getApplicationContext());
            	try {
					data = file.read();
					pdata = data.split(";");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	
            	
            	price = Integer.valueOf(pdata[2]).intValue()/Integer.valueOf(pdata[1]).intValue();
            	EditText fname = (EditText) findViewById(R.id.temfriend);
            	fnames = fname.getText().toString();
            	fname.setText("");
            	percent = 100/Integer.valueOf(pdata[1]).intValue();
            	//sql
            	tf.setName(fnames);
            	tf.setProjectid(Integer.valueOf(pdata[0]).intValue());
            	tf.setPayprice(price);
            	tf.setPercent(percent);
            	try {
            		if(temfriendsservice.getCount1(Integer.valueOf(pdata[0]).intValue())<Integer.valueOf(pdata[1]).intValue()){
            			temfriendsservice.save(tf);
            			Toast.makeText(getApplicationContext(), R.string.weibo_mystring_savesuccess, Toast.LENGTH_SHORT).show();
            		}
            		else{
            			Toast.makeText(getApplicationContext(), "超过人数！", Toast.LENGTH_SHORT).show();
            		}
				} catch (Exception e) {
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
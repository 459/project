package com.sina.weibo.sdk.demo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import weibo_mystring_service.FileService;
import weibo_mystring_service.Temfriends;
import weibo_mystring_service.Temfriendsservice;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class WB_nextActivity extends Activity{
	private Temfriendsservice temfriendsservice;
	FileService file = new FileService(this);
	private ListView flist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        temfriendsservice = new Temfriendsservice(this);
    	flist = (ListView)this.findViewById(R.id.flist);
    	show();
    	 this.findViewById(R.id.end).setOnClickListener(new OnClickListener() {

             @Override
             public void onClick(View v) {
                 startActivity(new Intent(WB_nextActivity.this, WBDemoMainActivity.class));
             }
         });
    	 this.findViewById(R.id.wrongprice).setOnClickListener(new OnClickListener() {

             @Override
             public void onClick(View v) {
                 startActivity(new Intent(WB_nextActivity.this, WB_Wrongprice.class));
             }
         });
    }

	private void show() {
		String data1;
		String[] pdata={"0","0","0"};//0为id 1为num 2为price
		try {
			data1 = file.read();
			pdata = data1.split(";");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<Temfriends> temfriends = temfriendsservice.getScrollData(Integer.valueOf(pdata[0]).intValue(),0, 20);
		List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
		for(Temfriends temfriend : temfriends){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("id", temfriend.getProjectid());
			item.put("pname", temfriend.getName());
			item.put("fprice", temfriend.getPayprice());
			data.add(item);
		}
		//构造器
		SimpleAdapter adapter = new SimpleAdapter(this,data,R.layout.activity_fresult,
				new String[]{"id","pname","fprice"},new int[]{R.id.pfLid,R.id.pfLname,R.id.pfLprice});
		
		flist.setAdapter(adapter);
	}
}
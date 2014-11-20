package com.sina.weibo.sdk.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import weibo_mystring_service.FileService;
import weibo_mystring_service.Temfriends;
import weibo_mystring_service.Temfriendsservice;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class WBDetilProject extends Activity{
	private Temfriendsservice temfriendsservice;
	FileService file = new FileService(this);
	private ListView p2flist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_detilproject);
        temfriendsservice = new Temfriendsservice(this);
    	p2flist = (ListView)this.findViewById(R.id.p2flist);
    	show();
	}
	private void show() {
		String pLid = "";
		try {
			pLid = file.read1();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        List<Temfriends> temfriends = temfriendsservice.getScrollData(Integer.valueOf(pLid).intValue(),0, 200);
		List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
		for(Temfriends temfriend : temfriends){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("id", temfriend.getProjectid());
			item.put("pname", temfriend.getName());
			item.put("fprice", temfriend.getPayprice());
			data.add(item);
		}
			//构造器
		SimpleAdapter adapter = new SimpleAdapter(this,data,R.layout.activity_p2fresult,
				new String[]{"id","pname","fprice"},new int[]{R.id.p2fLid,R.id.p2fLname,R.id.p2fLprice});
		p2flist.setAdapter(adapter);
	}
}

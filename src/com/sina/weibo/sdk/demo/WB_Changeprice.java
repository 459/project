package com.sina.weibo.sdk.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import weibo_mystring_service.FileService;
import weibo_mystring_service.Project;
import weibo_mystring_service.Projectservice;
import weibo_mystring_service.Temfriends;
import weibo_mystring_service.Temfriendsservice;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class WB_Changeprice extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        String data;
        String[] pdata = {"0","0","0"};//0为id 1为num 2为price
        FileService file=new FileService(getApplicationContext());
        Projectservice ps = new Projectservice(getApplicationContext());
        Project pro;
        String tprice="";
        try {
        	tprice = file.read2();//true price
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        Temfriendsservice tfs = new Temfriendsservice(this);
        try {
			data = file.read();
			pdata = data.split(";");//pdata[2] old price
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        pro = ps.find(Integer.valueOf(pdata[0]).intValue());
        pro.setPrice(Integer.valueOf(tprice).intValue());
        ps.update(pro);
        
        ListView tflist;
        tflist = (ListView)this.findViewById(R.id.tflist);
        List<Temfriends> temfriends = tfs.getScrollData(Integer.valueOf(pdata[0]).intValue(),0, 200);
		List<HashMap<String,Object>> d = new ArrayList<HashMap<String,Object>>();
		for(Temfriends temfriend : temfriends){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("id", temfriend.getProjectid());
			Temfriends tf=temfriend;
			tf.setProjectid(temfriend.getProjectid());
			item.put("pname", temfriend.getName());
			tf.setName(temfriend.getName());
			item.put("fprice", temfriend.getPercent()/100*Integer.valueOf(tprice).intValue()-temfriend.getPayprice());
			tf.setPayprice(temfriend.getPercent()*Integer.valueOf(tprice).intValue()/100);
			tf.setPercent(temfriend.getPayprice());
			tfs.update(tf);
			d.add(item);
		}
			//构造器
		SimpleAdapter adapter = new SimpleAdapter(this,d,R.layout.activity_change1,
				new String[]{"id","pname","fprice"},new int[]{R.id.tfLid,R.id.tfLname,R.id.tprice});
		tflist.setAdapter(adapter);
        
    	 this.findViewById(R.id.end).setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(WB_Changeprice.this, WBDemoMainActivity.class));
             }
         });
    }
}
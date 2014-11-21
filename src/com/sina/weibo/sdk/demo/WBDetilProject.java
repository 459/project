package com.sina.weibo.sdk.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import weibo_mystring_service.FileService;
import weibo_mystring_service.Projectservice;
import weibo_mystring_service.Temfriends;
import weibo_mystring_service.Temfriendsservice;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class WBDetilProject extends Activity{
	private Temfriendsservice temfriendsservice;
	private Projectservice projectservice;
	FileService file = new FileService(this);
	private ListView p2flist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_detilproject);
        temfriendsservice = new Temfriendsservice(this);
    	p2flist = (ListView)this.findViewById(R.id.p2flist);
    	show();
    	this.findViewById(R.id.delectp).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	AlertDialog.Builder dialog = new AlertDialog.Builder(WBDetilProject.this);  
                dialog.setTitle("提示");//窗口名           
                dialog.setMessage("确认删除？ ");  
                dialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int which) {  
                        // TODO Auto-generated method stub
                    	delete();
                    }

					
                });  
                dialog.setNegativeButton("取消",new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int which) {  
                        // TODO Auto-generated method stub  
                    }  
                }); 
                dialog.show();
            }
    	});
    	
    	this.findViewById(R.id.sharep).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	 startActivity(new Intent(WBDetilProject.this,WBShareMainActivity.class));
            }
    	});
	}
	private void delete() {
		String pLid = "";
		try {
			pLid = file.read1();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		projectservice.delete(Integer.valueOf(pLid));			
	}  
	private  void show() {
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

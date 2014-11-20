package com.sina.weibo.sdk.demo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import weibo_mystring_service.FileService;
import weibo_mystring_service.Project;
import weibo_mystring_service.Projectservice;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast; 
import android.view.View;   

public class WBFindActivity extends Activity{
	private Projectservice projectservice;
	private ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findmain);
    	projectservice = new Projectservice(this);
    	listview = (ListView)this.findViewById(R.id.listview);
    	show();
    	
    	listview.setOnItemClickListener(new OnItemClickListener(){   
            @Override   
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {   
				TextView testview=(TextView) arg1.findViewById(R.id.pLid);
            	String pLid = testview.getText().toString();
            	 
            	 FileService file = new FileService(getApplicationContext());
            	 try {
				 	file.save1(pLid);
				 } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }
            	 
                startActivity(new Intent(WBFindActivity.this, WBDetilProject.class));
            }       
        });   
    }

	private void show() {
		List<Project> projects = projectservice.getScrollData(0, 20);
		List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
		for(Project project : projects){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("id", project.getProjectid());
			item.put("pname", project.getName());
			item.put("pprice", project.getPrice());
			item.put("memnum", project.getMembernum());
			data.add(item);
		}
		//构造器
		SimpleAdapter adapter = new SimpleAdapter(this,data,R.layout.activity_find,
				new String[]{"id","pname","pprice","memnum"},new int[]{R.id.pLid,R.id.pLname,R.id.pLprice,R.id.pLmemnum});
		
		listview.setAdapter(adapter);
	}
}

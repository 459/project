/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sina.weibo.sdk.demo;


import java.io.File;

import weibo_mystring_service.DBservice;
import weibo_mystring_service.FileService;
import weibo_mystring_service.Project;
import weibo_mystring_service.Projectservice;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 费用分配选择，好友选择界面。
 */
public final class WBAddProject extends Activity {
	private Projectservice projectservice;
	
	CheckBox AA=null; 
    CheckBox percent=null; 
    @Override 
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_add);

    	projectservice = new Projectservice(this);//SQLite
    	final Project project = new Project();
    	
    	AA=(CheckBox)findViewById(R.id.AA); 
        percent=(CheckBox)findViewById(R.id.percent);
        
        AA.setChecked(false);  
        percent.setChecked(false);  
        //添加事件监听
        AA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            @Override 
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {} 
        }); 
        
        percent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
        	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {} }); 
        	
        	this.findViewById(R.id.selOK).setOnClickListener(new OnClickListener() {
            @Override
            	public void onClick(View v) {
            	String pnames = "";
            	String pprices = "";
            	String pnums = "";
            	String id = "";
            	int pnumi;
            	EditText pname = (EditText) findViewById(R.id.projectname);
                EditText pprice = (EditText) findViewById(R.id.projectprice);
                EditText pnum = (EditText) findViewById(R.id.projectnum);
                
                pnames = pname.getText().toString();
                pprices = pprice.getText().toString();
                pnums = pnum.getText().toString();
                pnumi = Integer.valueOf(pnums).intValue();
                
                /*数据库存入*/
                DBservice dbservice = new DBservice(getApplicationContext());
                dbservice.getWritableDatabase();
                project.setName(pnames);
                project.setPrice(Integer.valueOf(pprices).intValue());
                project.setMembernum(pnumi);
                id = String.valueOf(projectservice.getCount()+1);
                FileService files = new FileService(getApplicationContext());
                try {
					files.save(id,pnums,pprices);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            	if(pnames!=null && pprices!=null){
            		if(AA.isChecked() && !percent.isChecked()){ 
            			startActivity(new Intent(WBAddProject.this, WB_AllAAActivity.class));
            			projectservice.save(project);//sqlSave
            		}
            		else if(!AA.isChecked() && percent.isChecked()){ 
            			startActivity(new Intent(WBAddProject.this, WB_PercentActivity.class));
            			projectservice.save(project);//sqlSave
            		} 
            		else{
            			Toast.makeText(getApplicationContext(), R.string.weibo_mystring_selecterror, Toast.LENGTH_SHORT).show();
            		}
            	}
            	else{
            		Toast.makeText(getApplicationContext(), R.string.weibo_mystring_inputPerror, Toast.LENGTH_SHORT).show();
            	}
            }
        });
    }
}
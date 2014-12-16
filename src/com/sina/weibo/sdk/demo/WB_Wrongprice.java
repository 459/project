package com.sina.weibo.sdk.demo;


import weibo_mystring_service.FileService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;


public class WB_Wrongprice extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrongprice);
        
        this.findViewById(R.id.oktrueprice).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	FileService fs = new FileService(getApplicationContext());
            	EditText trueprice = (EditText) findViewById(R.id.trueprice);
            	String tprices = trueprice.getText().toString();
            	try {
					fs.save2(tprices);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	Toast.makeText(getApplicationContext(),tprices, Toast.LENGTH_SHORT).show();
            	 startActivity(new Intent(WB_Wrongprice.this, WB_Changeprice.class));
            }
    	});
	}
}
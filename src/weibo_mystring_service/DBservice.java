package weibo_mystring_service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBservice extends SQLiteOpenHelper{

	public DBservice(Context context) {
		super(context, "PList.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("CREATE TABLE project(projectid integer primary key autoincrement,name varchar(20),price integer,membernum integer);");
		arg0.execSQL("CREATE TABLE temfriends(projectid integer,name varchar(20),payprice integer,percent integer);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
}
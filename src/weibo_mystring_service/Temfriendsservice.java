package weibo_mystring_service;
/**
 * 对于project在数据库中的一些操作
 */
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Temfriendsservice {
	private DBservice dbservice;
	
	public Temfriendsservice(Context context) {
		this.dbservice = new DBservice(context);
	}
	public void save(Temfriends tf)
	{
		SQLiteDatabase db = dbservice.getWritableDatabase();
		db.execSQL("insert into temfriends(projectid,name,payprice,percent) values(?,?,?,?)",
				new Object[]{tf.getProjectid(),tf.getName(),tf.getPayprice(),tf.getPercent()});
		db.close();
	}
	public void delete(String name)
	{
		SQLiteDatabase db = dbservice.getWritableDatabase();
		db.execSQL("delete from temfriends where name = ?",
				new Object[]{name});
		db.close();
	}
	public void update(Temfriends tf)
	{
		SQLiteDatabase db = dbservice.getWritableDatabase();
		db.execSQL("update temfriends set payprice=?,percent=? where name=?",
				new Object[]{tf.getPayprice(),tf.getPercent(),tf.getName()});
	}
	public Temfriends find(Integer id)
	{
		SQLiteDatabase db = dbservice.getReadableDatabase();
		Cursor cursor =db.rawQuery("select *from temfriends where projectid=?", new String[]{id.toString()});
		if(cursor.moveToFirst()) 
		{
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int price = cursor.getInt(cursor.getColumnIndex("price"));
			int per = cursor.getInt(cursor.getColumnIndex("percent"));
			return new Temfriends(id,name,price,per);
		}
		cursor.close();
		return null;
	}
	public List<Temfriends> getScrollData(int pid,int offset,int maxResult)
	{
		List<Temfriends> tfs = new ArrayList<Temfriends>();
		SQLiteDatabase db = dbservice.getReadableDatabase();
		Cursor cursor =db.rawQuery("select *from temfriends where projectid = '"+pid+"' order by projectid asc limit ?,?", new String[]{String.valueOf(offset),String.valueOf(maxResult)});
		while(cursor.moveToNext())
		{
			int id = cursor.getInt(cursor.getColumnIndex("projectid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int payprice = cursor.getInt(cursor.getColumnIndex("payprice"));
			int per = cursor.getInt(cursor.getColumnIndex("percent"));
			tfs.add(new Temfriends(id,name,payprice,per));
		}
		cursor.close();
		return tfs;
	}
	public long getCount()
	{
		SQLiteDatabase db = dbservice.getReadableDatabase();
		Cursor cursor =db.rawQuery("select count(*) from temfriends",null);
		cursor.moveToFirst();
		long result=cursor.getLong(0);
		cursor.close();
		return result;
	}
	public long getCount1(int id)
	{
		SQLiteDatabase db = dbservice.getReadableDatabase();
		Cursor cursor =db.rawQuery("select count(*) from temfriends where projectid = '"+id+"'",null);
		cursor.moveToFirst();
		long result=cursor.getLong(0);
		cursor.close();
		return result;
	}
}
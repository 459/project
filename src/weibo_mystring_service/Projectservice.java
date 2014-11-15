package weibo_mystring_service;
/**
 * 对于project在数据库中的一些操作
 */
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Projectservice {
	private DBservice dbservice;
	
	public Projectservice(Context context) {
		this.dbservice = new DBservice(context);
	}
	public void save(Project project)
	{
		SQLiteDatabase db = dbservice.getWritableDatabase();
		db.execSQL("insert into project(name,price,membernum) values(?,?,?)",
				new Object[]{project.getName(),project.getPrice(),project.getMembernum()});
		db.close();
	}
	public void delete(Integer id)
	{
		SQLiteDatabase db = dbservice.getWritableDatabase();
		db.execSQL("delete from project where projectid = ?",
				new Object[]{id});
		db.close();
	}
	public void update(Project project)
	{
		SQLiteDatabase db = dbservice.getWritableDatabase();
		db.execSQL("update project set name=?,price=? where projectid=?",
				new Object[]{project.getName(),project.getPrice(),project.getProjectid()});
	}
	public Project find(Integer id)
	{
		SQLiteDatabase db = dbservice.getReadableDatabase();
		Cursor cursor =db.rawQuery("select *from project where projectid=?", new String[]{id.toString()});
		if(cursor.moveToFirst()) 
		{
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int price = cursor.getInt(cursor.getColumnIndex("price"));
			int membernum = cursor.getInt(cursor.getColumnIndex("membernum"));
			return new Project(id,name,price,membernum);
		}
		cursor.close();
		return null;
	}
	public List<Project> getScrollData(int offset,int maxResult)
	{
		List<Project> projects = new ArrayList<Project>();
		SQLiteDatabase db = dbservice.getReadableDatabase();
		Cursor cursor =db.rawQuery("select *from project order by projectid asc limit ?,?", new String[]{String.valueOf(offset),String.valueOf(maxResult)});
		while(cursor.moveToNext())
		{
			int id = cursor.getInt(cursor.getColumnIndex("projectid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int price = cursor.getInt(cursor.getColumnIndex("price"));
			int membernum = cursor.getInt(cursor.getColumnIndex("membernum"));
			projects.add(new Project(id,name,price,membernum));
		}
		cursor.close();
		return projects;
	}
	public long getCount()
	{
		SQLiteDatabase db = dbservice.getReadableDatabase();
		Cursor cursor =db.rawQuery("select count(*) from project",null);
		cursor.moveToFirst();
		long result=cursor.getLong(0);
		cursor.close();
		return result;
	}
}

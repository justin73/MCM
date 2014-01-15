package com.compassionapp.diary;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class MynotesOperate
{
	public static final String TABLENAME="notes";//表名�?
	private SQLiteDatabase db;					  // SQLiteDatabase
	public MynotesOperate(SQLiteDatabase db){	  //构�?方法
		this.db=db;
	}

	public void insert(String title, String content, String date ){
		/*String sql="INSERT INTO"+ TABLENAME+"(title, content, date)VALUES(?,?,?)";
		Object args[]=new Object[]{title,content,date};
		this.db.execSQL(sql,args);
		this.db.close();*/
		ContentValues cv=new ContentValues();
		cv.put("title", title);
		cv.put("content",content);
		cv.put("date",date);
		this.db.insert(TABLENAME,null,cv);
		this.db.close();
	}
	public void update(int id, String title, String content, String date){
		/*String sql="UPDATE"+TABLENAME+ "SET title=?, content=?, date=?, WHERE id=?";
		Object args[]=new Object[]{title,content,date,id};
		this.db.execSQL(sql,args);
		this.db.close();*/
		String whereClause="id=?";
		String whereArgs[]=new String[]{String.valueOf(id)};
		ContentValues cv=new ContentValues();
		cv.put("title", title);
		cv.put("content",content);
		cv.put("date",date);
		this.db.update(TABLENAME, cv, whereClause, whereArgs);
		this.db.close();
	}
	/*public void select(int id){
		String sql="SELECT title,content FROM"+TABLENAME+"WHERE id=?";
		Object args[]=new Object[]{id};
		this.db.execSQL(sql,args);
		this.db.close();
	}*/
	public void delete(String title, String date){//listview中删除时使用
		String sql="DELETE FROM "+ TABLENAME+" WHERE  id=(Select id FROM "+TABLENAME+" WHERE title='"+title+"' AND date='"+date+"')";
		
		this.db.execSQL(sql);
		this.db.close();
		/*String whereClause="id=?";
		String whereArgs[]=new String[]{String.valueOf(id)};
		this.db.delete(TABLENAME, whereClause, whereArgs);
		this.db.close();*/
	}
	public void delete(int id){//在编辑notes时删除使�?
		String whereClause="id=?";
		String whereArgs[]=new String[]{String.valueOf(id)};
		this.db.delete(TABLENAME, whereClause, whereArgs);
		this.db.close();
	}
	
}

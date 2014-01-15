package com.compassionapp.diary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MynotesCursor
{
	private static final String TABLENAME="notes";
	private SQLiteDatabase db=null;
	public MynotesCursor(SQLiteDatabase db){
		this.db=db;
	}
	public int getCount(){// 返回记录�?
		int count=0;			//记录保存结果
		String sql="SELECT COUNT(id) FROM "+TABLENAME;
		Cursor result=db.rawQuery(sql, null);
		for(result.moveToFirst();!result.isAfterLast();result.moveToNext()){
			count=result.getInt(0);
		}
		return count;
	}
	public List<Map<String,Object>> find(int currentPage,int lineSize){
		List<Map<String,Object>> all=new ArrayList<Map<String,Object>>();
		String sql="SELECT id, title, content, date FROM "+TABLENAME+" LIMIT?,?";
		String selectionArgs[]=new String[]{
				String.valueOf((currentPage-1)*lineSize),String.valueOf(lineSize)
		};
		Cursor resultCursor=this.db.rawQuery(sql, selectionArgs);
		for(resultCursor.moveToFirst();!resultCursor.isAfterLast();resultCursor.moveToNext()){
		 /*all.add("�?+resultCursor.getInt(0)+"�?+" "+resultCursor.getString(1)+","+resultCursor.getString(2)
				 +","+resultCursor.getString(3));*/
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", resultCursor.getInt(0));
			map.put("title", resultCursor.getString(1));
			map.put("content", resultCursor.getString(2));
			map.put("date", resultCursor.getString(3));
			all.add(map);
		}
		this.db.close();
		return all;
		
	}
	/*public List<Map<String, Object>>  readNotes(String title, String content){
		List<Map<String,Object >> all= new  ArrayList<Map<String, Object>>();
		String sql="SELECT title, content FROM "+ TABLENAME+"WHERE id=?";
		
		Cursor result=this.db.rawQuery(sql, null);
		for(result.moveToFirst();!result.isAfterLast();result.moveToNext()){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", result.getInt(0));
			map.put("title", result.getString(1));
			map.put("content", result.getString(2));
			all.add(map);
		}
		this.db.close();
		return all;
	}*/
}

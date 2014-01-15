package com.compassionapp.diary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper
{
	private static final String DATABASENAME="compassionApp.db";
	private static final int DATABASWVERSION=1;
	private static String TABLENAME="notes";
	public MyDatabaseHelper(Context context)//定义构�?
	{
		super(context,DATABASENAME, null, DATABASWVERSION);//调用父类构�?
		// TODO Auto-generated constructor stub
	}

	@Override 
	public void onCreate(SQLiteDatabase db) //�?��建数据表
	{
		// TODO Auto-generated method stub
		String sql="CREATE TABLE "+ TABLENAME +"(" +
				"id		 INTEGER 		PRIMARY KEY," +
				/*"diaryID INTEGER		NOT NULL," +*/
				"title   VARCHAR(100) 	NOT NULL," +
				"content VARCHAR(10000) NOT NULL," +
				"date 	 DATE 			NOT NULL)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO Auto-generated method stub
		String sql="DROP TABLE IF EXISTS "+TABLENAME;
		db.execSQL(sql);
		this.onCreate(db);
	}

}

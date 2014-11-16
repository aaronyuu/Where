package com.example.yss;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	
	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, null, version);
	}

	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override //创建数据库时自动执行
	public void onCreate(SQLiteDatabase db) {
		String sql="create table if not exists gpsinfo (" +
				"_id integer primary key autoincrement, " +
				"lo double not null ,  " +
				"la double not  null ,  " +
				"addr text not null  ," +
				"status int default 1)";
		
		db.execSQL(sql);
		
		/**
		 * 插入两条测试数据 
		 * 正式上线的时候要删除
		 */
		sql="insert into gpsinfo (lo,la,addr) values(116.445755,39.900995,'北京市东城区白桥大街15号')";
		db.execSQL(sql);	
	}

	@Override  //当数据库版本变化时会自动执行
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}

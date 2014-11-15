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

	@Override //�������ݿ�ʱ�Զ�ִ��
	public void onCreate(SQLiteDatabase db) {
		String sql="create table if not exists gpsinfo (" +
				"_id integer primary key autoincrement, " +
				"longitude double not null ,  " +
				"latitude double not  null ,  " +
				"address text not null  ," +
				"status int default 1)";
		
		db.execSQL(sql);
		
		/**
		 * ���������������� 
		 * ��ʽ���ߵ�ʱ��Ҫɾ��
		 */
		sql="insert into gpsinfo (longitude,latitude,address) values(116.66666,39.99999,'�������Ŵ��')";
		db.execSQL(sql);
		sql="insert into gpsinfo (longitude,latitude,address) values(116.777777,39.88888,'����һ���߹���')";
		db.execSQL(sql);	
	
		
	}

	@Override  //�����ݿ�汾�仯ʱ���Զ�ִ��
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}

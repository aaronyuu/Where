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
				"lo double not null ,  " +
				"la double not  null ,  " +
				"addr text not null  ," +
				"status int default 1)";
		
		db.execSQL(sql);
		
		/**
		 * ���������������� 
		 * ��ʽ���ߵ�ʱ��Ҫɾ��
		 */
		sql="insert into gpsinfo (lo,la,addr) values(116.445755,39.900995,'�����ж��������Ŵ��15��')";
		db.execSQL(sql);	
	}

	@Override  //�����ݿ�汾�仯ʱ���Զ�ִ��
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}

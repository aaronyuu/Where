package com.example.yss;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBUtils {

	private static int DATABASE_VERSION = 1;
	private static String DATABASE_NAME = "YSSGPS.db";
	private static String TAB_GPSINFO = "gpsinfo";
	private static SQLiteDatabase db;
	private static DBOpenHelper helper;
	
	private static void openDB(Context context){
		/**
		 * �򿪻򴴽����ݿ�,ע�����ݿ�汾
		 */
		 helper = new DBOpenHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
		db = helper.getReadableDatabase();
	}
	
	/**
	 * ��������
	 */
	public static void resetGPSInfo(Context context) {
		openDB(context);
		db.delete(TAB_GPSINFO, null, null);
		// ���湫˾��ַ
		String sql = "insert into gpsinfo (lo,la,addr) values(116.445755,39.900995,'�����ж��������Ŵ��15��')";
		db.execSQL(sql);
		db.close();
		helper.close();
	}
	
	
	/**
	 * ��ȡ��ַ�б�
	 */
	public static String[] getList() {

		Cursor cr = db.query(TAB_GPSINFO, new String[] { "address" },
				"status=?", new String[] { "1" }, null, null, "_id");
		String[] address = new String[cr.getCount()];
		int i = 0;
		while (cr.moveToNext()) {
			address[i] = cr.getString(cr.getColumnIndex("address"));
			i++;
		}
		cr.close();
		return address;
	}
}

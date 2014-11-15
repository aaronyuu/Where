package com.example.yss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListActivity extends Activity {
	Context mContext;
	Button btnLocal;
	Button btnList;
	Button btnAbout;
	ListView mList;
//	TextView mUserInfo;
	SQLiteDatabase db;
	List< Map<String, GPSInfo>> data;
	
	private static int DATABASE_VERSION = 1;
	private static String DATABASE_NAME = "YSSGPS.db";
	private static String TAB_GPSINFO = "gpsinfo";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		// ��ȡ�ؼ�
		mContext = this;
//		mUserInfo = (TextView) findViewById(R.id.userinfo);
		mList = (ListView) findViewById(R.id.listViewGpslist);
		data=new ArrayList<Map<String,GPSInfo>>();
		titleInit();
		/**
		 * �򿪻򴴽����ݿ�,ע�����ݿ�汾
		 */
		DBOpenHelper helper = new DBOpenHelper(this, DATABASE_NAME, null,
				DATABASE_VERSION);
		db = helper.getReadableDatabase();

		/**
		 * �������ݽӿ�
		 */
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
//				android.R.layout.simple_list_item_1, getList());
//		//
//		new ArrayAdapter(context, textViewResourceId, objects)<GPSInfo>(mContext, textViewResourceId)
		/**
		 * SimpleAdapter
		 * 1.context ������
		 * data :  List<? extends Map<String, ?>> data һ��Map����ɵ�List����
		 * resource: �����ļ���ID,��R.layout.item_gpsinfo
		 */
		
		
		SimpleAdapter simpleadapter = new SimpleAdapter(mContext, getData(), R.layout.item_gpsinfo, 
				new String[]{"lo","la","addr"}, 
				new int[]{R.id.tv_item_lo,R.id.tv_item_la,R.id.tv_item_addr});
		
		
		
		/**
		 * �󶨽ӿڵ�ViewList�ؼ�
		 */
//		mUserList.setAdapter(adapter);
		mList.setAdapter(simpleadapter);

		/**
		 * ���õ�������¼�
		 */
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ��ȡ�û���ʵ����
//				String realName = mUserList.getItemAtPosition(position)
//						.toString();
//				// ͨ����ʵ����,ȡ���û���������ϸ��Ϣ
//				User user = getUserinfo(realName);
//
//				// ��ʾ�û���ϸ��Ϣ����.
//				mUserInfo.setText(user.toString());
//				mUserInfo.setVisibility(View.VISIBLE);

			}
		});

//		/**
//		 * �����û���ϸ��Ϣҳ��ĵ���¼�, ��������ظ�ҳ��.
//		 */
//		mUserInfo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				v.setVisibility(View.INVISIBLE);
//			}
//		});

		/**
		 * db.close() ���� onDestroy()������.
		 */

	}

	/**
	 * ��Users���͵��û���Ϣ���浽���ݿ�, ����ֵ�Ǹ�����¼�ɹ����������ݱ��е�id ����-1��ʾ����ʧ��
	 */
	private long addUser(User user) {

		long rowId = -1;
		if (getUserCount(user.getRealname()) <= 0) {
			rowId = db.insert(TAB_GPSINFO, null, user.getContentValues());
		}
		return rowId;
	}

	// public void testAddUser() {
	// String addUserName;
	// Random r = new Random();
	// addUserName = "����" + r.nextInt(99);
	// if (addUser(addUserName) != 0) {
	// Toast.makeText(mContext, "��ӳɹ�", Toast.LENGTH_LONG).show();
	// }
	// }

	/**
	 * ͨ����ʵ����ɾ���û�
	 */
	private void delUserByRealName(String realname) {

		db.delete(TAB_GPSINFO, "realname =?", new String[] { realname });
	}

	/**
	 * ɾ��ȫ���û�
	 */
	private void delAllUser() {
		db.delete(TAB_GPSINFO, null, null);
	}

	/**
	 * �޸��û�
	 */
	private void updateUser(User user) {
		ContentValues values = user.getContentValues();
		values.put("phonenum", "136-9364-6379-888");
		// Set<Entry<String, Object>> a = values.valueSet();

		db.update(TAB_GPSINFO, values, "realname=?", new String[] { "�ڴ�ϲ" });
	}

	/**
	 * ��ѯ�û��б�
	 */
	private void listUser() {
		Cursor cr = db.query(TAB_GPSINFO, null, null, null, null, null, null);
		String[] colNames = cr.getColumnNames();
		while (cr.moveToNext()) {
			for (String string : colNames) {
				System.out.println(string + ":"
						+ cr.getString(cr.getColumnIndex(string)));
			}
		}
		cr.close();
		System.out.println("***********************************");
	}

	private long getUserCount(String realname) {
		Cursor cr = db.query(TAB_GPSINFO,
				new String[] { "count(*) as usercount" }, "name=?",
				new String[] { realname }, null, null, "_id");
		cr.moveToFirst();
		return cr.getLong(cr.getColumnIndex("usercount"));
	}

	/**
	 * ��ȡ��ַ�б�
	 */
	private String[] getList() {

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

	/**
	 * ͨ���û�����ȡ�û���ϸ��Ϣ,����һ��ContentValues���͵ļ�������
	 * 
	 * @param name
	 * @return
	 */
	private User getUserinfo(String realname) {
		ContentValues values = new ContentValues();

		Cursor cr = db.query(TAB_GPSINFO, null, "realname=?",
				new String[] { realname }, null, null, null);
		String[] colNames = cr.getColumnNames();

		if (cr.moveToFirst()) {

			for (String colname : colNames) {
				values.put(colname, cr.getString(cr.getColumnIndex(colname)));
			}
		}
		cr.close();

		return new User(values);
	}

	// private void listUser2() {
	// Cursor c = db.rawQuery("select * from userinfo", null); // ��ȡ�α�c,
	//
	// if (c != null) {
	// // ��ȡ��������, ������������� colNames
	// String[] colNames = c.getColumnNames();
	// while (c.moveToNext()) { // �����ȡ��һ������Ϊ��Ļ�,��ȡ����������.
	// for (String string : colNames) { // ��Foreachѭ�����������ֶ�.
	// System.out.println(c.getString(c.getColumnIndex(string))); // ��׼�������ݷ�ʽ
	// }
	// // Log.i("info", "_id:"+c.getInt(c.getColumnIndex("_id")));
	// // Log.i("info", "name:"+c.getString(c.getColumnIndex("name")));
	// // Log.i("info",
	// // "phonenum:"+c.getString(c.getColumnIndex("phonenum")));
	// // Log.i("info",
	// // "jiguan:"+c.getString(c.getColumnIndex("jiguan")));
	// // Log.i("info",
	// // "address:"+c.getString(c.getColumnIndex("address")));
	// // Log.i("info",
	// // "email:"+c.getString(c.getColumnIndex("email")));
	// // Log.i("info", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	// }
	// c.close();
	// }
	// }

	@Override
	protected void onDestroy() {
		if (db != null)
			db.close();
		super.onDestroy();
	};

	
	private void titleInit() {
		btnLocal = (Button) findViewById(R.id.btnLocal);
		btnList = (Button) findViewById(R.id.btnList);
		btnAbout = (Button) findViewById(R.id.btnAbout);

		// ���ü����¼�
		btnLocal.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnList.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnAbout.setOnClickListener(YUtils.gotoOtherActivity(mContext));
	}

	
	private List<Map<String, GPSInfo>>  getData(){
		for (int i=0;i<20;i++){
			Map<String,GPSInfo> map=new HashMap<String,GPSInfo>();
			GPSInfo gps=new GPSInfo();
			gps.lo=116+i*0.01;
			gps.la=39+i*0.01;
			gps.addr= "λ��:"+i;
			map.put(gps.addr, gps);
			data.add(map);
	
		}
		return data;
		
	}
}

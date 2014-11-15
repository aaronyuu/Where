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

		// 获取控件
		mContext = this;
//		mUserInfo = (TextView) findViewById(R.id.userinfo);
		mList = (ListView) findViewById(R.id.listViewGpslist);
		data=new ArrayList<Map<String,GPSInfo>>();
		titleInit();
		/**
		 * 打开或创建数据库,注意数据库版本
		 */
		DBOpenHelper helper = new DBOpenHelper(this, DATABASE_NAME, null,
				DATABASE_VERSION);
		db = helper.getReadableDatabase();

		/**
		 * 创建数据接口
		 */
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
//				android.R.layout.simple_list_item_1, getList());
//		//
//		new ArrayAdapter(context, textViewResourceId, objects)<GPSInfo>(mContext, textViewResourceId)
		/**
		 * SimpleAdapter
		 * 1.context 上下文
		 * data :  List<? extends Map<String, ?>> data 一个Map所组成的List集合
		 * resource: 布局文件的ID,如R.layout.item_gpsinfo
		 */
		
		
		SimpleAdapter simpleadapter = new SimpleAdapter(mContext, getData(), R.layout.item_gpsinfo, 
				new String[]{"lo","la","addr"}, 
				new int[]{R.id.tv_item_lo,R.id.tv_item_la,R.id.tv_item_addr});
		
		
		
		/**
		 * 绑定接口到ViewList控件
		 */
//		mUserList.setAdapter(adapter);
		mList.setAdapter(simpleadapter);

		/**
		 * 设置点击监听事件
		 */
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 获取用户真实姓名
//				String realName = mUserList.getItemAtPosition(position)
//						.toString();
//				// 通过真实姓名,取出用户的所有详细信息
//				User user = getUserinfo(realName);
//
//				// 显示用户详细信息界面.
//				mUserInfo.setText(user.toString());
//				mUserInfo.setVisibility(View.VISIBLE);

			}
		});

//		/**
//		 * 设置用户详细信息页面的点击事件, 点击后隐藏该页面.
//		 */
//		mUserInfo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				v.setVisibility(View.INVISIBLE);
//			}
//		});

		/**
		 * db.close() 放在 onDestroy()过程中.
		 */

	}

	/**
	 * 将Users类型的用户信息保存到数据库, 返回值是该条记录成功保存在数据表中的id 返回-1表示保存失败
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
	// addUserName = "左凤阁" + r.nextInt(99);
	// if (addUser(addUserName) != 0) {
	// Toast.makeText(mContext, "添加成功", Toast.LENGTH_LONG).show();
	// }
	// }

	/**
	 * 通过真实姓名删除用户
	 */
	private void delUserByRealName(String realname) {

		db.delete(TAB_GPSINFO, "realname =?", new String[] { realname });
	}

	/**
	 * 删除全部用户
	 */
	private void delAllUser() {
		db.delete(TAB_GPSINFO, null, null);
	}

	/**
	 * 修改用户
	 */
	private void updateUser(User user) {
		ContentValues values = user.getContentValues();
		values.put("phonenum", "136-9364-6379-888");
		// Set<Entry<String, Object>> a = values.valueSet();

		db.update(TAB_GPSINFO, values, "realname=?", new String[] { "于传喜" });
	}

	/**
	 * 查询用户列表
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
	 * 获取地址列表
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
	 * 通过用户名获取用户详细信息,返回一个ContentValues类型的集合数据
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
	// Cursor c = db.rawQuery("select * from userinfo", null); // 获取游标c,
	//
	// if (c != null) {
	// // 获取所有列名, 将结果存入数组 colNames
	// String[] colNames = c.getColumnNames();
	// while (c.moveToNext()) { // 如果获取下一条数据为真的话,则取出这条数据.
	// for (String string : colNames) { // 用Foreach循环遍历所有字段.
	// System.out.println(c.getString(c.getColumnIndex(string))); // 标准遍历数据方式
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

		// 设置监听事件
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
			gps.addr= "位置:"+i;
			map.put(gps.addr, gps);
			data.add(map);
	
		}
		return data;
		
	}
}

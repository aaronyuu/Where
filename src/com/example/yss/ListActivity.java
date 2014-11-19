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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

//import com.baidu.baidulocationdemo.LocationApplication;

public class ListActivity extends Activity {
	Context mContext;
	Button btnLocal;
	Button btnList;
	Button btnAbout;
	ListView mList;
	View mView;
	Button btnSave;
	Button btnCancle;
	Button buttonAdd;
	Button buttonOK;
	EditText et;
	SQLiteDatabase db;
	List<Map<String, Object>> data;

	private EditText editTextid;
	private EditText editTextlo;
	private EditText editTextla;
	private EditText editTextaddr;

	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();

	SimpleAdapter mSimpleAdapter;
	private Button buttonDel;

	private static int DATABASE_VERSION = 1;
	private static String DATABASE_NAME = "YSSGPS.db";
	private static String TAB_GPSINFO = "gpsinfo";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		// 获取控件
		mContext = this;
		// mUserInfo = (TextView) findViewById(R.id.userinfo);
		mList = (ListView) findViewById(R.id.listViewGpslist);
		btnCancle = (Button) findViewById(R.id.buttoncancle);
		btnSave = (Button) findViewById(R.id.buttonsave);
		buttonAdd = (Button) findViewById(R.id.buttonAdd);
		buttonDel = (Button) findViewById(R.id.buttonDel);
		buttonOK = (Button) findViewById(R.id.buttonOK);

		mView = findViewById(R.id.linearlayoutuserinfo);

		editTextid = (EditText) findViewById(R.id.editTextid);
		editTextlo = (EditText) findViewById(R.id.editTextlo);
		editTextla = (EditText) findViewById(R.id.editTextla);
		editTextaddr = (EditText) findViewById(R.id.editTextaddr);

		data = new ArrayList<Map<String, Object>>();
		titleInit();

		/*
		 * 启动定位
		 */

		/**
		 * 打开或创建数据库,注意数据库版本
		 */
		DBOpenHelper helper = new DBOpenHelper(this, DATABASE_NAME, null,
				DATABASE_VERSION);
		db = helper.getReadableDatabase();

		/**
		 * 创建数据接口 SimpleAdapter 1.context 上下文 data : List<? extends Map<String,
		 * ?>> data 一个Map所组成的List集合 resource: 布局文件的ID,如R.layout.item_gpsinfo
		 */

		mSimpleAdapter = new SimpleAdapter(mContext, getData(),
				R.layout.item_gpsinfo,
				new String[] { "_id", "lo", "la", "addr" }, new int[] {
						R.id.tv_item_id, R.id.tv_item_lo, R.id.tv_item_la,
						R.id.tv_item_addr });

		/**
		 * 绑定接口到ViewList控件
		 */
		mList.setAdapter(mSimpleAdapter);

		/**
		 * 点击列表项显示修改界面
		 */
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 设置修改界面值
				editTextid.setText(String
						.valueOf(data.get(position).get("_id")));
				editTextlo.setText(String.valueOf(data.get(position).get("lo")));
				editTextla.setText(String.valueOf(data.get(position).get("la")));
				editTextaddr.setText(String.valueOf(data.get(position).get(
						"addr")));
				// 显示出来
				mView.setVisibility(View.VISIBLE);

			}
		});

		/**
		 * 返回按钮事件, 点击后隐藏该页面.
		 */
		btnCancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editTextid.setText("");
				editTextlo.setText("");
				editTextla.setText("");
				editTextaddr.setText("");
				mView.setVisibility(View.INVISIBLE);
			}
		});

		/**
		 * 添加按钮
		 */
		buttonAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 启动GPS定位,
				startGPS();
				// 显示编辑界面出来就行了
				mView.setVisibility(View.VISIBLE);
			}

		});

		/**
		 * 删除按钮
		 */
		buttonDel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (editTextid.getText().length() > 0) {
					if (delGPSInfo(Long
							.valueOf(editTextid.getText().toString())) > 0) {
						mView.setVisibility(View.INVISIBLE);
						// 刷新数据
						getData();
						mSimpleAdapter.notifyDataSetChanged();
						Toast.makeText(mContext, "删除成功!!", Toast.LENGTH_SHORT)
								.show();
					} else {
						Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT)
								.show();
					}

				}
			}
		});

		/**
		 * 设置前位置
		 */
		buttonOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 保存数据到文件
				double lo = Double.valueOf(editTextlo.getText().toString());
				double la = Double.valueOf(editTextla.getText().toString());
				String addr = editTextaddr.getText().toString();

				boolean isSaved = YssGPS.setGPSInfo(lo, la, addr);
				if (!isSaved) {
					Toast.makeText(mContext, "保存错误", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(mContext, "成功设置当前位置,现在打开破解版的CRM软件定位一下吧!",
							Toast.LENGTH_LONG).show();
					// 打开当前位置界面
				}

			}
		});

		/**
		 * 保存按钮事件.
		 */
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				double lo, la;
				long id = -1;

				String addr;
				if (editTextid.getText().length() > 0) {
					id = Long.valueOf(editTextid.getText().toString());
				}
				// 检查数据有效性
				lo = Double.valueOf(editTextlo.getText().toString());
				la = Double.valueOf(editTextla.getText().toString());
				addr = editTextaddr.getText().toString();

				if (saveOrModifyGPSInfo(id, lo, la, addr) >= 0) {
					mView.setVisibility(View.INVISIBLE);
					Toast.makeText(mContext, "保存数据成功!!", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(mContext, "保存数据失败", Toast.LENGTH_SHORT)
							.show();
				}
				// 刷新数据
				getData();
				mSimpleAdapter.notifyDataSetChanged();
			}
		});

	}

	private long saveOrModifyGPSInfo(long id, double lo, double la, String addr) {
		long rowId = -1;
		ContentValues cv = new ContentValues();
		cv.put("lo", lo);
		cv.put("la", la);
		cv.put("addr", addr);

		if (getCount(id) > 0) {
			db.update(TAB_GPSINFO, cv, "_id=?", new String[] { id + "" });
			rowId = id;
		} else {
			rowId = db.insert(TAB_GPSINFO, null, cv);
		}

		return rowId;

	}

	/**
	 * 删除
	 */
	private int delGPSInfo(long _id) {
		if (_id >= 0) {
			return db.delete(TAB_GPSINFO, "_id =?",
					new String[] { String.valueOf(_id) });
		} else {
			return -1;
		}
	}

	private long getCount(long id) {

		Cursor cr = db.query(TAB_GPSINFO, new String[] { "count(*) as count" },
				"_id=?", new String[] { id + "" }, null, null, "_id");
		cr.moveToFirst();
		return cr.getLong(cr.getColumnIndex("count"));
	}

	// @Override
	// protected void onDestroy() {
	// if (db != null)
	// db.close();
	// super.onDestroy();
	// };

	private void titleInit() {
		btnLocal = (Button) findViewById(R.id.btnLocal);
		btnList = (Button) findViewById(R.id.btnList);
		btnAbout = (Button) findViewById(R.id.btnAbout);

		// 设置监听事件
		btnLocal.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnList.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnAbout.setOnClickListener(YUtils.gotoOtherActivity(mContext));
	}

	private void startGPS() {
		// TODO Auto-generated method stub
		myListener = new MyLocationListener();
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开GPS
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(800);// 设置发起定位请求的间隔时间,大于1000,连续定位,小于1000,定位一次
		option.setIsNeedAddress(true); // 包含地址信息
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	private List<Map<String, Object>> getData() {

		data.clear();
		Cursor cr = db.query(TAB_GPSINFO, new String[] { "_id", "lo", "la",
				"addr" }, "status=?", new String[] { "1" }, null, null, "_id");

		while (cr.moveToNext()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("_id", cr.getLong(cr.getColumnIndex("_id")));
			map.put("lo", cr.getDouble(cr.getColumnIndex("lo")));
			map.put("la", cr.getDouble(cr.getColumnIndex("la")));
			map.put("addr", cr.getString(cr.getColumnIndex("addr")));
			data.add(map);
		}
		cr.close();

		return data;

	}

	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			double lo = -1;
			double la = -1;
			String addr = "";
			// Receive Location
			// StringBuffer sb = new StringBuffer(256);
			// sb.append("time : ");
			// sb.append(location.getTime());
			// sb.append("\nerror code : ");
			// sb.append(location.getLocType());
			// sb.append("\nlatitude : ");
			la = location.getLatitude();
			// sb.append(location.getLatitude());
			// sb.append("\nlontitude : ");
			lo = location.getLongitude();
			// sb.append(location.getLongitude());
			// sb.append("\nradius : ");
			// sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				// sb.append("\nspeed : ");
				// sb.append(location.getSpeed());
				// sb.append("\nsatellite : ");
				// sb.append(location.getSatelliteNumber());
				// sb.append("\ndirection : ");
				// sb.append("\naddr : ");
				addr = location.getAddrStr();
				// sb.append(location.getAddrStr());
				// Toast.makeText(mContext, location.getAddrStr(),
				// Toast.LENGTH_SHORT).show();
				// sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				addr = location.getAddrStr();
				// sb.append("\naddr : ");
				// sb.append(location.getAddrStr());
				// //运营商信息
				// sb.append("\noperationers : ");
				// sb.append(location.getOperators());
			}
			logMsg(lo, la, addr);
			// Log.i("BaiduLocationApiDem", sb.toString());
		}

	}

	/**
	 * 显示请求字符串
	 * 
	 * @param str
	 */
	public void logMsg(double lo, double la, String addr) {
		/*
		 * 将传入的值显示到界面上.
		 */
		if (editTextlo != null)
			editTextlo.setText(lo + "");
		if (editTextla != null)
			editTextla.setText(la + "");
		if (editTextaddr != null)
			editTextaddr.setText(addr + "");
		Toast.makeText(mContext, "定位成功!", Toast.LENGTH_LONG).show();

	}

}

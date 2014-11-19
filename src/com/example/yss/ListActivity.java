package com.example.yss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

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

		// ��ȡ�ؼ�
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
		/**
		 * �򿪻򴴽����ݿ�,ע�����ݿ�汾
		 */
		DBOpenHelper helper = new DBOpenHelper(this, DATABASE_NAME, null,
				DATABASE_VERSION);
		db = helper.getReadableDatabase();

		/**
		 * �������ݽӿ� SimpleAdapter 1.context ������ data : List<? extends Map<String,
		 * ?>> data һ��Map����ɵ�List���� resource: �����ļ���ID,��R.layout.item_gpsinfo
		 */

		mSimpleAdapter = new SimpleAdapter(mContext, getData(),
				R.layout.item_gpsinfo,
				new String[] { "_id", "lo", "la", "addr" }, new int[] {
						R.id.tv_item_id, R.id.tv_item_lo, R.id.tv_item_la,
						R.id.tv_item_addr });

		/**
		 * �󶨽ӿڵ�ViewList�ؼ�
		 */
		mList.setAdapter(mSimpleAdapter);

		/**
		 * ����б�����ʾ�޸Ľ���
		 */
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// �����޸Ľ���ֵ
				editTextid.setText(String
						.valueOf(data.get(position).get("_id")));
				editTextlo.setText(String.valueOf(data.get(position).get("lo")));
				editTextla.setText(String.valueOf(data.get(position).get("la")));
				editTextaddr.setText(String.valueOf(data.get(position).get(
						"addr")));
				// ��ʾ����
				mView.setVisibility(View.VISIBLE);

			}
		});

		/**
		 * ���ذ�ť�¼�, ��������ظ�ҳ��.
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
		 * ��Ӱ�ť
		 */
		buttonAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��ʾ�༭�������������
				mView.setVisibility(View.VISIBLE);
			}
		});

		/**
		 * ɾ����ť
		 */
		buttonDel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				 
				if (editTextid.getText().length() >0) {
					if (delGPSInfo(Long.valueOf(editTextid.getText().toString())) > 0) {
						mView.setVisibility(View.INVISIBLE);
						// ˢ������
						getData();
						mSimpleAdapter.notifyDataSetChanged();
						Toast.makeText(mContext, "ɾ���ɹ�!!", Toast.LENGTH_SHORT)
								.show();
					} else {
						Toast.makeText(mContext, "ɾ��ʧ��", Toast.LENGTH_SHORT)
								.show();
					}

				}
			}
		});

		/**
		 * ����ǰλ��
		 */
		buttonOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �������ݵ��ļ�
				double lo = Double.valueOf(editTextlo.getText().toString());
				double la = Double.valueOf(editTextla.getText().toString());
				String addr = editTextaddr.getText().toString();

				boolean isSaved = GPSUtils.setGPSInfo(lo, la, addr);
				if (!isSaved) {
					Toast.makeText(mContext, "�������", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(mContext, "�ɹ����õ�ǰλ��,���ڴ��ƽ���CRM�����λһ�°�!",
							Toast.LENGTH_LONG).show();
					// �򿪵�ǰλ�ý���
				}

			}
		});

		/**
		 * ���水ť�¼�.
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
				// ���������Ч��
				lo = Double.valueOf(editTextlo.getText().toString());
				la = Double.valueOf(editTextla.getText().toString());
				addr = editTextaddr.getText().toString();

				if (saveOrModifyGPSInfo(id, lo, la, addr) >= 0) {
					mView.setVisibility(View.INVISIBLE);
					Toast.makeText(mContext, "�������ݳɹ�!!", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(mContext, "��������ʧ��", Toast.LENGTH_SHORT)
							.show();
				}
				// ˢ������
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
	 * ɾ��
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

		// ���ü����¼�
		btnLocal.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnList.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnAbout.setOnClickListener(YUtils.gotoOtherActivity(mContext));
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
}

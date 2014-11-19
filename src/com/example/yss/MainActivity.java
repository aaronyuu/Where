package com.example.yss;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView tv;
	Button btnLocal;
	Button btnList;
	Button btnAbout;
	Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.textViewDangqianWeizhi);
		mContext = this;
		
		String s = "";
		s += "经度(double):" + YssGPS.getLo(0) + "\n";
		s += "经度(String):" + YssGPS.getLoByString(0+"") + "\n";
		s += "纬度(double):" + YssGPS.getLa(0) + "\n";
		s += "纬度:(String)" + YssGPS.getLaByString(0+"") + "\n";
		s += "地址:" + YssGPS.getAddr("未设置地址!!签到软件取自己定位的地址.") + "\n";
		
		//tv.setText(YssGPS.getGPSInfoFromFile());
		tv.setText(s);
		titleInit();
	}

	private void titleInit() {
		btnLocal = (Button) findViewById(R.id.btnLocal);
		btnList = (Button) findViewById(R.id.btnList);
		btnAbout = (Button) findViewById(R.id.btnAbout);

		//设置监听事件
		btnLocal.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnList.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnAbout.setOnClickListener(YUtils.gotoOtherActivity(mContext));
	}



}

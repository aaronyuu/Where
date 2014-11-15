package com.example.yss;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

//		// 保存测试数据
//		boolean isSaved = GPSUtils.setGPSInfo(126d, 39.8888d, "测试地址");
//		if (!isSaved) {
//			Toast.makeText(this, "保存错误", Toast.LENGTH_SHORT).show();
//		}

		tv.setText(GPSUtils.getGPSInfoFromFile());

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

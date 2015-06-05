package com.example.yss;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView tv;
	Button btnLocal;
	Button btnList;
	Button btnAbout;
	Button buttonDelfile;
	Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.textViewDangqianWeizhi);
		buttonDelfile = (Button) findViewById(R.id.buttonDelFile);
		mContext = this;

		tv.setText(YssGPS.getGPSInfoFromFile());

		titleInit();

		buttonDelfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				YssGPS.setGPSInfo(0, 0, "");
				tv.setText(YssGPS.getGPSInfoFromFile());
			}
		});

	}

	private void titleInit() {
		btnLocal = (Button) findViewById(R.id.btnLocal);
		btnList = (Button) findViewById(R.id.btnList);
		btnAbout = (Button) findViewById(R.id.btnAbout);

		// ÉèÖÃ¼àÌýÊÂ¼þ
		btnLocal.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnList.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnAbout.setOnClickListener(YUtils.gotoOtherActivity(mContext));
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		tv.setText(YssGPS.getGPSInfoFromFile());
	}

}

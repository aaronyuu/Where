package com.example.yss;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

public class AboutActivity extends Activity {

	Button btnLocal;
	Button btnList;
	Button btnAbout;
	Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		mContext=this;
		titleInit();
	}

	private void titleInit() {
		btnLocal = (Button) findViewById(R.id.btnLocal);
		btnList = (Button) findViewById(R.id.btnList);
		btnAbout = (Button) findViewById(R.id.btnAbout);

		//ÉèÖÃ¼àÌýÊÂ¼þ
		btnLocal.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnList.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnAbout.setOnClickListener(YUtils.gotoOtherActivity(mContext));
	}

	
}

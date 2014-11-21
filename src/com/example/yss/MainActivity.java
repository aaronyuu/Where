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
		buttonDelfile=(Button) findViewById(R.id.buttonDelFile);
		mContext = this;
		
//		String s = "";
//		s += "����(double):" + YssGPS.getLo(0) + "\n";
//		s += "����(String):" + YssGPS.getLoByString(0+"") + "\n";
//		s += "γ��(double):" + YssGPS.getLa(0) + "\n";
//		s += "γ��:(String)" + YssGPS.getLaByString(0+"") + "\n";
//		s += "��ַ:" + YssGPS.getAddr("δ���õ�ַ!!ǩ�����ȡ�Լ���λ�ĵ�ַ.") + "\n";
//		
//		 StringBuffer sb = new StringBuffer(256);
//		 sb.append("����(double):");
//		double lo=0d;
//		sb.append(YssGPS.getLo(lo));
//		sb.append("\n");
//		sb.append("γ��(double):");
//		double la=0d;
//		sb.append(YssGPS.getLa(la));
//		sb.append("\n");
//		sb.append("��ַ:");
//		String addr="δ���õ�ַ!!\nǩ�����ȡʵ�ʵ�ַ��λ.";
//		sb.append(YssGPS.getAddr(addr));
//		sb.append("\n");
//		sb.append("����(String):");
//		String slo="0";
//		sb.append(YssGPS.getLoByString(slo));
//		sb.append("\n");
//		sb.append("γ��(String):");
//		String sla="0";
//		sb.append(YssGPS.getLaByString(sla));
//		sb.append("\n");
//		System.out.println(sb.toString());
		tv.setText(YssGPS.getGPSInfoFromFile());
//		tv.setText(sb.toString());
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
		
		//���ü����¼�
		btnLocal.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnList.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnAbout.setOnClickListener(YUtils.gotoOtherActivity(mContext));
	}



}

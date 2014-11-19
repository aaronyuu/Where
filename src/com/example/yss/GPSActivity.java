package com.example.yss;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class GPSActivity extends Activity {

	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		myListener = new MyLocationListener();
		mLocationClient = new LocationClient(getApplicationContext()); // ����LocationClient��
		mLocationClient.registerLocationListener(myListener); // ע���������

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);   //��GPS
		option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		option.setScanSpan(800);// ���÷���λ����ļ��ʱ��,����1000,������λ,С��1000,��λһ��
		option.setIsNeedAddress(true);  //������ַ��Ϣ
		mLocationClient.setLocOption(option);
		mLocationClient.start();
		
//		mLocationClient.requestLocation();
//		System.out.println("aaa");
//		while (mLocationClient != null && mLocationClient.isStarted()) {
//			mLocationClient.requestLocation();
//			mLocationClient.stop();  
//		} 
	}

	/**
	 * ��ʾ�����ַ���
	 * 
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}

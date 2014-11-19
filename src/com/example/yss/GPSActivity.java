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
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);   //打开GPS
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(800);// 设置发起定位请求的间隔时间,大于1000,连续定位,小于1000,定位一次
		option.setIsNeedAddress(true);  //包含地址信息
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
	 * 显示请求字符串
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

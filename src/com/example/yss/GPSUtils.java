package com.example.yss;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;

public final class GPSUtils {
	//文件名固定
	final static String STATICFILENAME = Environment.getExternalStorageDirectory()
			+ "/aa";
	/**
	 * 检查文件是否存在,不存在就创建, 检查文件是否是文件,不是文件,删除后创建文件.
	 * @param fileName
	 */
	public static void checkFile(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!file.isFile()) {
			file.delete();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean setGPSInfo(double lo,double la,String address) {
	
		checkFile(STATICFILENAME);
		
		if (lo<73 || lo>136){
			/**
			 * 要在中国范围之内, 返回false,表示出错
			 */
			return false;
		}
		
		if (la<3 || la>54){
			return false;
		}
		
		if (address.length()<1){
			return false;
		}
		
//		final String tag = "tag";
		try {
//			Log.i(tag, STATICFILENAME);
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(STATICFILENAME));
			// 先保存纬度,再保存经度
			dos.writeDouble(la); // 纬度
			dos.writeDouble(lo); // 经度
			dos.write(address.getBytes());
//			dos.writeDouble(39.913708d); // 纬度
//			dos.writeDouble(116.436664d); // 经度
//			dos.write("北京市东城区北京站东街3号楼".getBytes());
			dos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static String getGPSInfoFromFile() {
		/**
		 * 从文件获取经纬度
		 */
		String s = "";
		s += "经度(double):" + YssGPS.getLo() + "\n";
		s += "经度(String):" + YssGPS.getLoByString() + "\n";
		s += "纬度(double):" + YssGPS.getLa() + "\n";
		s += "纬度:(String)" + YssGPS.getLaByString() + "\n";
		s += "地址:" + YssGPS.getAdd() + "\n";
		return s;
	}
}

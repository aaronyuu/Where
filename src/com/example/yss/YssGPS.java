package com.example.yss;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;

/**
 * 该类用于CRM中读取, 修改为如果为读取不到数据,则采用系统自动定位的位置 2014年11月19日
 */
public class YssGPS {

	final static String FILENAME = Environment.getExternalStorageDirectory()
			+ "/aa";

	static double mLo;
	static double mLa;
	static String mAddr;

	/**
	 * 检查文件是否存在,不存在就创建, 检查文件是否是文件,不是文件,删除后创建文件.
	 * 
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	static final double getLa(double la) {
		checkFile(FILENAME);
		double d = -1;
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(
					FILENAME));
			d = dis.readDouble();
			dis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (d<3 || d>54) {
			d = la;
		}

		return d;
	}

	static final double getLo(double lo) {

		double d = -1;
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(
					FILENAME));
			d = dis.readDouble();
			d = dis.readDouble();
			dis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (d<73 || d>136) {
			d = lo;
		}
		return d;
	}

	static final String getAddr(String addr) {
		String str = "";
		try {
			FileInputStream fis = new FileInputStream(FILENAME);
			byte[] buffer = new byte[1024];
			int strLength = fis.read(buffer);
			str = new String(buffer, 16, strLength - 16);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (str.length() < 1) {
			str = addr;
		}
		return str;
	}

	static final String getLaByString(String la) {
		return String.valueOf(getLa(Double.valueOf(la)));
	}

	static final String getLoByString(String lo) {
		return String.valueOf(getLo(Double.valueOf(lo)));
	}
	
	
	

	public static boolean setGPSInfo(double lo,double la,String address) {
	
		checkFile(FILENAME);
		
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
		
		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(FILENAME));
			// 先保存纬度,再保存经度
			dos.writeDouble(la); // 纬度
			dos.writeDouble(lo); // 经度
			dos.write(address.getBytes());
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
		s += "经度(double):" + YssGPS.getLo(0) + "\n";
		s += "经度(String):" + YssGPS.getLoByString(0+"") + "\n";
		s += "纬度(double):" + YssGPS.getLa(0) + "\n";
		s += "纬度:(String)" + YssGPS.getLaByString(0+"") + "\n";
		s += "地址:" + YssGPS.getAddr("未设置地址!!签到软件取自己定位的地址.") + "\n";
		return s;
	}
	
	
}

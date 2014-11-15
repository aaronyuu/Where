package com.example.yss;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.os.Environment;

/**
 * 该类用于CRM中读取,暂不修改
 * 
 * @author Administrator
 * 
 */
public class YssGPS {

	final static String srcFileName = Environment.getExternalStorageDirectory()
			+ "/aa";

	public YssGPS() {


	}

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
	
	static final double getLa() {
		double d = -1;
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(
					srcFileName));
			d = dis.readDouble();
			dis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (d<=0){
			d=39.900974d;
		}
		return d;
	}

	static final double getLo() {

		double d = -1;
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(
					srcFileName));
			d = dis.readDouble();
			d = dis.readDouble();
			dis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (d<=0){
			d=116.445742d;
		}
		return d;
	}

	static final String getAdd() {
		String str = "";
		try {
			FileInputStream fis = new FileInputStream(srcFileName);
			byte[] buffer = new byte[1024];
			int strLength = fis.read(buffer);
			str = new String(buffer, 16, strLength - 16);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (str.length()<1){
			str="北京市东城区白桥大街15号";
		}
		return str;
	}

	static final String getLaByString() {
		return String.valueOf(getLa());
	}

	static final String getLoByString() {
		return String.valueOf(getLo());
	}
}

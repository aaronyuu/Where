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
 * ��������CRM�ж�ȡ, �޸�Ϊ���Ϊ��ȡ��������,�����ϵͳ�Զ���λ��λ�� 2014��11��19��
 */
public class YssGPS {

	final static String FILENAME = Environment.getExternalStorageDirectory()
			+ "/aa";

	static double mLo;
	static double mLa;
	static String mAddr;

	/**
	 * ����ļ��Ƿ����,�����ھʹ���, ����ļ��Ƿ����ļ�,�����ļ�,ɾ���󴴽��ļ�.
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
			 * Ҫ���й���Χ֮��, ����false,��ʾ����
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
			// �ȱ���γ��,�ٱ��澭��
			dos.writeDouble(la); // γ��
			dos.writeDouble(lo); // ����
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
		 * ���ļ���ȡ��γ��
		 */
		String s = "";
		s += "����(double):" + YssGPS.getLo(0) + "\n";
		s += "����(String):" + YssGPS.getLoByString(0+"") + "\n";
		s += "γ��(double):" + YssGPS.getLa(0) + "\n";
		s += "γ��:(String)" + YssGPS.getLaByString(0+"") + "\n";
		s += "��ַ:" + YssGPS.getAddr("δ���õ�ַ!!ǩ�����ȡ�Լ���λ�ĵ�ַ.") + "\n";
		return s;
	}
	
	
}

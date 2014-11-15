package com.example.yss;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;

public final class GPSUtils {
	//�ļ����̶�
	final static String STATICFILENAME = Environment.getExternalStorageDirectory()
			+ "/aa";
	/**
	 * ����ļ��Ƿ����,�����ھʹ���, ����ļ��Ƿ����ļ�,�����ļ�,ɾ���󴴽��ļ�.
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
		
//		final String tag = "tag";
		try {
//			Log.i(tag, STATICFILENAME);
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(STATICFILENAME));
			// �ȱ���γ��,�ٱ��澭��
			dos.writeDouble(la); // γ��
			dos.writeDouble(lo); // ����
			dos.write(address.getBytes());
//			dos.writeDouble(39.913708d); // γ��
//			dos.writeDouble(116.436664d); // ����
//			dos.write("�����ж���������վ����3��¥".getBytes());
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
		s += "����(double):" + YssGPS.getLo() + "\n";
		s += "����(String):" + YssGPS.getLoByString() + "\n";
		s += "γ��(double):" + YssGPS.getLa() + "\n";
		s += "γ��:(String)" + YssGPS.getLaByString() + "\n";
		s += "��ַ:" + YssGPS.getAdd() + "\n";
		return s;
	}
}

package com.example.yss;
/**
 * 位置类
 * @author Administrator
 *
 */
public class GPSInfo {
	public double lo;
	public double la;
	public String addr;
	
	@Override
	public String toString() {
		return "GPSS [经度=" + lo + ", 纬度=" + la + ", 地址=" + addr + "]";
	}
	
	
}

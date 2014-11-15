package com.example.yss;

import android.content.ContentValues;

public class User {
	private String username;
	private String realname;
	private String phonenum;
	private String email;
	private String hometown;
	private String address;
	private int status;

	/**
	 * 创建无参构造函数
	 */
	public User() {
	}

	/**
	 * 创建有参构造函数
	 * 通过一个ContentValues类,给Users类赋值.
	 * @param values
	 */
	public User(ContentValues values) {

		this.username = values.getAsString("username");
		this.realname = values.getAsString("realname");
		this.phonenum = values.getAsString("phonenum");
		this.email = values.getAsString("email");
		this.hometown = values.getAsString("hometown");
		this.address = values.getAsString("address");
		this.status = values.getAsInteger("status");
		// return this;

	}
	
	public ContentValues getContentValues(){
		
		ContentValues values = new ContentValues();

		values.put("username", this.getUsername());
		values.put("realname", this.getRealname());
		values.put("phonenum", this.getPhonenum());
		values.put("email", this.getEmail());
		values.put("hometown", this.getHometown());
		values.put("address", this.getAddress());
		values.put("status", this.getStatus());
		
		return values;
	}

	/**
	 * 通过逗号分隔的字符串返回列名
	 * 
	 * @return
	 */
	public String getColNames() {
		String s = "_id,username,realname,phonenum,email,hometown";
		return s;
	}

	/**
	 * 通过数组返回列名
	 * 
	 * @return
	 */
	public String[] getColNames2() {
		return getColNames().split(",");
	}

	@Override
	public String toString() {
		return "username=" + username + "\nrealname=" + realname
				+ "\nphonenum=" + phonenum + "\nemail=" + email
				+ "\nhometown=" + hometown + "\naddress=" + address ;
	}

	/**
	 * Get/Set
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}

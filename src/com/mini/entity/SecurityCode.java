package com.mini.entity;

/**
 * 书籍的借阅信息
 * @author s
 *
 */
public class SecurityCode {
	private int id;  //book的id	
	private String phone;//book的二维码
	private String code;//借阅人的帐号的id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	

}

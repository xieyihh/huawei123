package com.mini.entity;

/**
 * 用户实体类
 * @author 雷晓冰 2014-11-01
 */
public class User {
	private int id;
	private String name;			//姓名
	private String account;		//账号（一搬为微信号）
	private String salt;				//加密随机数
	private String password;	//密码
	private String number;		//工号
	private String gender;		//性别
	private String phone;			//电话
	private Integer level;					//用户身份~0：普通用户；1：模块管理员；2：超级管理员
	private String authority;		//权限，如association&activity&表示拥有协会和大型活动两个模块的管理员权限
	private String remark;		//备注
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}

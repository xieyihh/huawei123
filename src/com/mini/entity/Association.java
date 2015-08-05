package com.mini.entity;

import java.sql.Date;

/**
 * 协会实体类
 * @author 雷晓冰 2014-11-01
 */
public class Association {
	private int id;
	private String name;				//协会名称
	private String description;	//描述
	private Date date;			//申请时间
	private int state;				//状态：0~正在被申请、1~申请通过
	private int founderId;		//创建人ID
	private String founder; 	//创建人姓名
	private String administrators;		//协会管理员ID：1&&2&&
	private String members;	//协会成员ID：1&&2&&
	private int size;				//协会成员数量
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getFounderId() {
		return founderId;
	}
	public void setFounderId(int founderId) {
		this.founderId = founderId;
	}
	public String getFounder() {
		return founder;
	}
	public void setFounder(String founder) {
		this.founder = founder;
	}
	public String getAdministrators() {
		return administrators;
	}
	public void setAdministrators(String administrators) {
		this.administrators = administrators;
	}
	public String getMembers() {
		return members;
	}
	public void setMembers(String members) {
		this.members = members;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}

package com.mini.entity;

import java.sql.Timestamp;
/**
 * 用户反馈
 * @author 雷晓冰	2014-12-31
 */
public class Feedback {
	private int id;
	private String name;			//姓名
	private String number;		//工号
	private String content;		//反馈内容
	private Timestamp time;	//反馈时间
	private Integer state;
	
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}

package com.mini.form;

import java.sql.Timestamp;
/**
 * 用户反馈
 * @author 雷晓冰	2014-12-31
 */
public class FeedbackForm {
	private String id;
	private String name;			//姓名
	private String number;		//工号
	private String content;		//反馈内容
	private String time;	//反馈时间
	private String state;
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}

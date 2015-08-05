package com.mini.form;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class BookBorrowinfoForm implements Serializable {
	private int id;  //book的id	
	private String onlysign;//book的二维码
	private String borrower;//书的名称
	private Date date;//书的借阅日期
	private String state;//书的状态
	private String name;//书的状态
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOnlysign() {
		return onlysign;
	}
	public void setOnlysign(String onlysign) {
		this.onlysign = onlysign;
	}
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	


}

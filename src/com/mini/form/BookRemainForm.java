package com.mini.form;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
//书籍的剩余数量
public class BookRemainForm implements Serializable {
	private String bookname;//书名
	private String  position;//书籍的所在位置
	private String number;//书籍的剩余数量
	private String DdlCode;//书籍的剩余数量
	
	
	public String getDdlCode() {
		return DdlCode;
	}
	public void setDdlCode(String ddlCode) {
		DdlCode = ddlCode;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	


}

package com.mini.entity;

public class BookRemainingCount {
	private String  count;
	private String position;
	private String bookname;
	private String DdlName;
	private String DdlCode;
	
	

	
	public String getDdlCode() {
		return DdlCode;
	}
	public void setDdlCode(String ddlCode) {
		DdlCode = ddlCode;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getDdlName() {
		return DdlName;
	}
	public void setDdlName(String ddlName) {
		DdlName = ddlName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	
}

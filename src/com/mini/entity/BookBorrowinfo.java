package com.mini.entity;

import java.util.Date;
/**
 * 书籍的借阅信息
 * @author s
 *
 */
public class BookBorrowinfo {
	private int id;  //book的id	
	private String onlysign;//book的二维码
	private String borrower;//借阅人的帐号的id
	private Date date;//书的借阅日期
	private String state;//书的状态
	private Date backdate;	
	private String borrowposition;	
	private String backposition;	

	public String getBorrowposition() {
		return borrowposition;
	}
	public void setBorrowposition(String borrowposition) {
		this.borrowposition = borrowposition;
	}
	public String getBackposition() {
		return backposition;
	}
	public void setBackposition(String backposition) {
		this.backposition = backposition;
	}
	public Date getBackdate() {
		return backdate;
	}
	public void setBackdate(Date backdate) {
		this.backdate = backdate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	




}

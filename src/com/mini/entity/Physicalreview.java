package com.mini.entity;

import java.util.Date;


/**
 * 复查项
 * 
 */
public class Physicalreview {
	
	private int id;/* 体检id */
	private String usernumber;	/* 体检人员工号 */
	private String reviewcontent;			/* 复查内容 */
	private String physicalplan;/*体检计划 */
	private String username;/*体检计划 */
	private String state;
	private Date importdate;
	
	public Date getImportdate() {
		return importdate;
	}
	public void setImportdate(Date importdate) {
		this.importdate = importdate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsernumber() {
		return usernumber;
	}
	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}
	public String getReviewcontent() {
		return reviewcontent;
	}
	public void setReviewcontent(String reviewcontent) {
		this.reviewcontent = reviewcontent;
	}
	public String getPhysicalplan() {
		return physicalplan;
	}
	public void setPhysicalplan(String physicalplan) {
		this.physicalplan = physicalplan;
	}
	

	
}

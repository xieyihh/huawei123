package com.mini.entity;

import java.util.Date;



/**
 * 体检亲属
 * 
 */
public class UserImage {
	
	private int id;/* id */
	private String usernumber;	/* 体检人员工号 */
	private String imagename;	/* 文件名称*/
	private String upflag;	/* 上传标志 */
	
	private String feedbackname;			//姓名
	private String feedbackcontent;		//工号

	private Date feedbacktime;	//反馈时间
	
	public String getFeedbackname() {
		return feedbackname;
	}
	public void setFeedbackname(String feedbackname) {
		this.feedbackname = feedbackname;
	}
	public String getFeedbackcontent() {
		return feedbackcontent;
	}
	public void setFeedbackcontent(String feedbackcontent) {
		this.feedbackcontent = feedbackcontent;
	}
	
	public Date getFeedbacktime() {
		return feedbacktime;
	}
	public void setFeedbacktime(Date feedbacktime) {
		this.feedbacktime = feedbacktime;
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
	public String getImagename() {
		return imagename;
	}
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
	public String getUpflag() {
		return upflag;
	}
	public void setUpflag(String upflag) {
		this.upflag = upflag;
	}
	
	

	
}

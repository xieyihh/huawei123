package com.mini.entity;

import java.util.Date;



/**
 * 带图片的反馈信息
 * 
 */
public class UserImage {
	
	private int id;/* id */
	private String usernumber;	/* 体检人员工号 */
	private String imagename;	/* 文件名称*/
	private String upflag;	/* 上传标志 */
	
	private String feedbackname;			//姓名
	private String feedbackcontent;		//反馈内容

	private Date feedbacktime;	//反馈时间
	private String smsContent;		//短信内容
	private int jifen;		//积分
	
	public int getJifen() {
		return jifen;
	}
	public void setJifen(int jifen) {
		this.jifen = jifen;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
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

package com.mini.entity;



/**
 * 
 * 
 */
public class ActivitySignup {
	
	private int id;/* 体检id */
	private int activityid; /*活动的id*/
	private int userid;	/* 用户的id*/
	private String flag;	/* 报名的选项   */
	private String content;	/* 报名的内容  */
	private String remark;	/* 备注 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getActivityid() {
		return activityid;
	}
	public void setActivityid(int activityid) {
		this.activityid = activityid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	 
	
	
}

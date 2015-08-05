package com.mini.entity;



/**
 * 用户的权限分配
 * 
 */
public class ActivityStatus {
	
	private int id;/* 体检id */

	private int status;	/* 活动开始的内容标志内容  */
	private int activityid; /*活动的id*/
	private String remark;	/* 备注 */
	private String activityname;	/* 备注 */
	
	public String getActivityname() {
		return activityname;
	}
	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getActivityid() {
		return activityid;
	}
	public void setActivityid(int activityid) {
		this.activityid = activityid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}

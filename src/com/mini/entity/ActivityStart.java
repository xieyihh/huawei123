package com.mini.entity;



/**
 * 用户的权限分配
 * 
 */
public class ActivityStart {
	
	private int id;/* 体检id */
	private String flag;	/* 活动开始的内容标志 */
	private String content;	/* 活动开始的内容标志内容  */
	private int activityid; /*活动的id*/
	private String remark;	/* 备注 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

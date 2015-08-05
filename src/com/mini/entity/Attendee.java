package com.mini.entity;
/**
 * 大型活动参与者实体类
 * @author 雷晓冰 2014-11-01
 */
public class Attendee {
	private int id;
	private int activityId;		//对应的活动ID
	private int userId;			//参加者ID
	private String user;			//参加者姓名
	private String property;		//自定义属性值
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
}

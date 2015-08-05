package com.mini.entity;

import java.sql.Date;

/**
 * 大型活动实体类
 * @author 雷晓冰 2014-11-01
 */
public class Activity {
	private int id;
	private String name;			//活动名称
	private String description;	//描述/说明
	private int initiatorId;			//活动发起人ID
	private String initiator;		//活动发起人姓名
	private Date date;				//活动发起日期
	private Date applyDeadline;		//报名截止日期
	private Date startDate;	//起始时间
	private Date endDate;	//结束时间
	private String property;		//自定义属性
	private String attendees;		//活动的参与者ID：1&&2&&
	private String attendeesProperty;		//活动参与者的属性信息：1~身高:178CM;体重:180斤&&2~身高:188CM;体重:185斤&&
	private Integer max;		//参与人数上限
	private Integer size;		//活动参与者数量
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getApplyDeadline() {
		return applyDeadline;
	}
	public void setApplyDeadline(Date applyDeadline) {
		this.applyDeadline = applyDeadline;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public int getInitiatorId() {
		return initiatorId;
	}
	public void setInitiatorId(int initiatorId) {
		this.initiatorId = initiatorId;
	}
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	public String getAttendees() {
		return attendees;
	}
	public void setAttendees(String attendees) {
		this.attendees = attendees;
	}
	public String getAttendeesProperty() {
		return attendeesProperty;
	}
	public void setAttendeesProperty(String attendeesProperty) {
		this.attendeesProperty = attendeesProperty;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
}

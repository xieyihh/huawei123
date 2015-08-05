package com.mini.entity;

import java.sql.Date;

/**
 * 协会活动实体类
 * @author 雷晓冰 2014-11-01
 */
public class AssociationActivity {
	private int id;
	private String name;			//活动名称
	private String description;	//描述/说明
	private Integer initiatorId;			//活动发起人ID
	private String initiator;		//活动发起人姓名
	private Date date;				//活动发起日期
	private Date applyDeadline;		//报名截止日期
	private Date startDate;	//起始时间
	private Date endDate;	//结束时间
	private Integer associationId;	//所属协会（协会活动面向协会成员，大型活动面向所有用户，此项为空）
	private String association;
	private String attendees;		//活动的参与者ID：1&&2&&
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
	public Integer getInitiatorId() {
		return initiatorId;
	}
	public void setInitiatorId(Integer initiatorId) {
		this.initiatorId = initiatorId;
	}
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
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
	public Integer getAssociationId() {
		return associationId;
	}
	public void setAssociationId(Integer associationId) {
		this.associationId = associationId;
	}
	public String getAssociation() {
		return association;
	}
	public void setAssociation(String association) {
		this.association = association;
	}
	public String getAttendees() {
		return attendees;
	}
	public void setAttendees(String attendees) {
		this.attendees = attendees;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
}

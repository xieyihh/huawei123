package com.mini.entity;

import java.sql.Date;

/**
 * 体检亲属
 * 
 */
public class Physicalrelatives {
	
	private int id;/* 体检id */
	private String usernumber;	/* 体检人员工号 */
	private String idnumber;	/* 身份证号 */
	private String relationship;			/* 亲属关系 */
	private String phonenumber;		/* 电话 */
	private String physicalplan;/*体检计划 */
	private String state;/*数据状态 */
	private String relativeName;/*数据状态 */
	
	public String getRelativeName() {
		return relativeName;
	}
	public void setRelativeName(String relativeName) {
		this.relativeName = relativeName;
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
	public String getUsernumber() {
		return usernumber;
	}
	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}
	public String getIdnumber() {
		return idnumber;
	}
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getPhysicalplan() {
		return physicalplan;
	}
	public void setPhysicalplan(String physicalplan) {
		this.physicalplan = physicalplan;
	}
	

	
}

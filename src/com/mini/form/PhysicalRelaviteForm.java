package com.mini.form;

import java.io.File;




public class PhysicalRelaviteForm {
	/**
	 * 体检信息查询
	 */
	private String id;	
	private String idnumber;	/* 身份证号 */
	private String relationship;			/* 亲属关系 */
	private String phonenumber;		/* 电话 */
	private String relativeName;		/* 姓名 */
	private String physicalplan;/*体检计划 */
	public String getPhysicalplan() {
		return physicalplan;
	}
	public void setPhysicalplan(String physicalplan) {
		this.physicalplan = physicalplan;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRelativeName() {
		return relativeName;
	}
	public void setRelativeName(String relativeName) {
		this.relativeName = relativeName;
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
	
	
}

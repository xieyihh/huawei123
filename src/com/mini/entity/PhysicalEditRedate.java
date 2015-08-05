package com.mini.entity;

import java.util.Date;



/**
 * 用户体检时间修改
 * 
 */
public class PhysicalEditRedate {
	
	private int id;
	private String usernumber;	
	private String username;	

	private Date editRedate;	
	private Date importdate;
	private String physicalplan;
	private String flag;//0代表没有申请，1代表申请中，2代表申请同意，3代表申请拒绝
	private String physicalposition;
	
	public String getPhysicalposition() {
		return physicalposition;
	}
	public void setPhysicalposition(String physicalposition) {
		this.physicalposition = physicalposition;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getEditRedate() {
		return editRedate;
	}
	public void setEditRedate(Date editRedate) {
		this.editRedate = editRedate;
	}
	public Date getImportdate() {
		return importdate;
	}
	public void setImportdate(Date importdate) {
		this.importdate = importdate;
	}
	
	public String getPhysicalplan() {
		return physicalplan;
	}
	public void setPhysicalplan(String physicalplan) {
		this.physicalplan = physicalplan;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
}

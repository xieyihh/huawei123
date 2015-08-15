package com.mini.entity;

import java.util.Date;


/**
 * 
 */
public class PhysicalNoinfor {
	
	private int id;/* 体检id */
	private String usernumber;	/* 体检人员工号 */
	private String username;	/* 体检人员姓名 */
	private String physicalposition;		/* 体检地点 */
	private Date physicaldate;		//*正式体检时间 */
	private String physicalplan;/*体检批次 */
	private String  state;	/*导入时间 *///2代表已删除
	private String  remark;	/*导入时间 *///2代表已删除
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
	public String getPhysicalposition() {
		return physicalposition;
	}
	public void setPhysicalposition(String physicalposition) {
		this.physicalposition = physicalposition;
	}
	public Date getPhysicaldate() {
		return physicaldate;
	}
	public void setPhysicaldate(Date physicaldate) {
		this.physicaldate = physicaldate;
	}
	public String getPhysicalplan() {
		return physicalplan;
	}
	public void setPhysicalplan(String physicalplan) {
		this.physicalplan = physicalplan;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	
}

package com.mini.entity;

import java.util.Date;


/**
 * 
 */
public class Physicalexam {
	
	private int id;/* 体检id */
	private String usernumber;	/* 体检人员工号 */
	private String username;	/* 体检人员姓名 */
	private String physicalposition;		/* 体检地点 */
	private Date physicalreservedate;				/* 体检预约时间 */
	private Date physicaldate;		//*正式体检时间 */
	private String physicalstate;/*体检状态*/
	private String physicalplan;/*体检批次 */
	private String hasRelatives;/*是否有亲属 */
	private Date physicalimportdate;	/*导入时间 */
	private String  state;	/*导入时间 */
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getHasRelatives() {
		return hasRelatives;
	}
	public void setHasRelatives(String hasRelatives) {
		this.hasRelatives = hasRelatives;
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
	
	public String getPhysicalposition() {
		return physicalposition;
	}
	public void setPhysicalposition(String physicalposition) {
		this.physicalposition = physicalposition;
	}
	public Date getPhysicalreservedate() {
		return physicalreservedate;
	}
	public void setPhysicalreservedate(Date physicalreservedate) {
		this.physicalreservedate = physicalreservedate;
	}
	public Date getPhysicaldate() {
		return physicaldate;
	}
	public void setPhysicaldate(Date physicaldate) {
		this.physicaldate = physicaldate;
	}
	public String getPhysicalstate() {
		return physicalstate;
	}
	public void setPhysicalstate(String physicalstate) {
		this.physicalstate = physicalstate;
	}
	public String getPhysicalplan() {
		return physicalplan;
	}
	public void setPhysicalplan(String physicalplan) {
		this.physicalplan = physicalplan;
	}
	public Date getPhysicalimportdate() {
		return physicalimportdate;
	}
	public void setPhysicalimportdate(Date physicalimportdate) {
		this.physicalimportdate = physicalimportdate;
	}

	
}

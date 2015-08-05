package com.mini.entity;



/**
 * 用户的权限分配
 * 
 */
public class UserRolePopedom {
	
	private int id;/* 体检id */
	private String popedomcode;	/* 体检人员工号 */
	private String userlevel;	/* 身份证号 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPopedomcode() {
		return popedomcode;
	}
	public void setPopedomcode(String popedomcode) {
		this.popedomcode = popedomcode;
	}
	public String getUserlevel() {
		return userlevel;
	}
	public void setUserlevel(String userlevel) {
		this.userlevel = userlevel;
	}
	
	
	
}

package com.mini.entity;

import java.util.Date;
/**
 * 数据字典
 * @author s
 *
 */
public class Bookdictionary {

	private int secid;  //数据字典的id
	private String Keyword;//数据字典的分类
	private int DdlCode;//数据字典的分类项对应的代码
	private String DdlName;//数据字典的分类项对应的名称
	public int getSecid() {
		return secid;
	}
	public void setSecid(int secid) {
		this.secid = secid;
	}
	public String getKeyword() {
		return Keyword;
	}
	public void setKeyword(String keyword) {
		Keyword = keyword;
	}
	public int getDdlCode() {
		return DdlCode;
	}
	public void setDdlCode(int ddlCode) {
		DdlCode = ddlCode;
	}
	public String getDdlName() {
		return DdlName;
	}
	public void setDdlName(String ddlName) {
		DdlName = ddlName;
	}
	
	




}

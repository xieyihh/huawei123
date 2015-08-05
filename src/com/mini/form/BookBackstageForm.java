package com.mini.form;

import java.io.Serializable;

public class BookBackstageForm implements Serializable {
	
	private String secid;  //book的id	
	private String keyword;//book的二维码
	private String ddlCode;//借阅人的微信帐号
	private String ddlName;
	private String keywordname;
	private String username;
	private String number;
	private String isbn10;
	private String onlysign;
	private String userid;
	/**
	 * 保存数据字典时的状态标识
	 * 值=new:新建一种数据类型，并添加数据项，进行保存
	 * 值=add:在原有的数据类型的基础上进行修改和编辑，编辑对应的数据项，进行保存
	 */
	private String typeflag;
	//保存数据字典的数据项的名称
	private String [] itemname;
	
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getIsbn10() {
		return isbn10;
	}
	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}
	
	public String getOnlysign() {
		return onlysign;
	}
	public void setOnlysign(String onlysign) {
		this.onlysign = onlysign;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String[] getItemname() {
		return itemname;
	}
	public void setItemname(String[] itemname) {
		this.itemname = itemname;
	}
	public String getKeywordname() {
		return keywordname;
	}
	public void setKeywordname(String keywordname) {
		this.keywordname = keywordname;
	}
	public String getTypeflag() {
		return typeflag;
	}
	public void setTypeflag(String typeflag) {
		this.typeflag = typeflag;
	}
	public String getSecid() {
		return secid;
	}
	public void setSecid(String secid) {
		this.secid = secid;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getDdlCode() {
		return ddlCode;
	}
	public void setDdlCode(String ddlCode) {
		this.ddlCode = ddlCode;
	}
	public String getDdlName() {
		return ddlName;
	}
	public void setDdlName(String ddlName) {
		this.ddlName = ddlName;
	}

	
	

}

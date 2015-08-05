package com.mini.form;

import java.io.Serializable;

public class BooklastInforForm implements Serializable {
	private String infor;
	private String lablevalue;
	private String author;
	private String image;
	private String summary;
	private String radioname;
	
	
	public String getRadioname() {
		return radioname;
	}
	public void setRadioname(String radioname) {
		this.radioname = radioname;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getInfor() {
		return infor;
	}
	public void setInfor(String infor) {
		this.infor = infor;
	}
	public String getLablevalue() {
		return lablevalue;
	}
	public void setLablevalue(String lablevalue) {
		this.lablevalue = lablevalue;
	}
	

}

package com.mini.form;

import java.io.File;
import java.io.Serializable;

public class TestForm implements Serializable{
	private String textID;
	private String textName;
	private File textDate;
	public String getTextID() {
		return textID;
	}
	public void setTextID(String textID) {
		this.textID = textID;
	}
	public String getTextName() {
		return textName;
	}
	public void setTextName(String textName) {
		this.textName = textName;
	}
	public File getTextDate() {
		return textDate;
	}
	public void setTextDate(File textDate) {
		this.textDate = textDate;
	}
	
}

package com.mini.form;

import java.io.File;
import java.sql.Timestamp;

public class FileUploadlForm {
    private File[] file;              //文件  
    private String[] fileFileName;    //文件名   
    private String[] filePath;        //文件路径
	private String usernumber;	/* 体检人员工号 */
    private String feedbackname;			//姓名
	private String feedbackcontent;		//反馈内容
	private String imagename;    //文件名   
    private String imagedata;        //文件路径
    
	
	public String getImagename() {
		return imagename;
	}
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
	public String getImagedata() {
		return imagedata;
	}
	public void setImagedata(String imagedata) {
		this.imagedata = imagedata;
	}
	public String getUsernumber() {
		return usernumber;
	}
	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}
	public String getFeedbackname() {
		return feedbackname;
	}
	public void setFeedbackname(String feedbackname) {
		this.feedbackname = feedbackname;
	}
	public String getFeedbackcontent() {
		return feedbackcontent;
	}
	public void setFeedbackcontent(String feedbackcontent) {
		this.feedbackcontent = feedbackcontent;
	}
	public File[] getFile() {
		return file;
	}
	public void setFile(File[] file) {
		this.file = file;
	}
	public String[] getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String[] getFilePath() {
		return filePath;
	}
	public void setFilePath(String[] filePath) {
		this.filePath = filePath;
	}
    
	
	

}

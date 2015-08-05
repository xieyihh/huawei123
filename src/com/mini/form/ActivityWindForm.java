package com.mini.form;

import java.io.File;



public class ActivityWindForm {
	private String activityName ;
	private String[] activityContent ;
	private String activityTitle ;
	private String[] imagename ;
	private String[] imagedata;
	 
	private String[] itemName;
	private String[] itemFlag;
	private String[] text;
	private File[] file;
	private String[] fileFileName;
	private String activityStatus;
	private String userstatus;
	private String totalResults;//总记录数
	private String currentPage;//当前页数
	private String pageSize;//每页的记录数
	private String activityid;
	
	public String getActivityid() {
		return activityid;
	}
	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}
	public String getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(String totalResults) {
		this.totalResults = totalResults;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getUserstatus() {
		return userstatus;
	}
	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}
	public String getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}
	public String[] getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String[] getText() {
		return text;
	}
	public void setText(String[] text) {
		this.text = text;
	}
	public File[] getFile() {
		return file;
	}
	public void setFile(File[] file) {
		this.file = file;
	}
	public String[] getItemName() {
		return itemName;
	}
	public void setItemName(String[] itemName) {
		this.itemName = itemName;
	}
	public String[] getItemFlag() {
		return itemFlag;
	}
	public void setItemFlag(String[] itemFlag) {
		this.itemFlag = itemFlag;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String[] getActivityContent() {
		return activityContent;
	}
	public void setActivityContent(String[] activityContent) {
		this.activityContent = activityContent;
	}
	public String getActivityTitle() {
		return activityTitle;
	}
	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}
	public String[] getImagename() {
		return imagename;
	}
	public void setImagename(String[] imagename) {
		this.imagename = imagename;
	}
	public String[] getImagedata() {
		return imagedata;
	}
	public void setImagedata(String[] imagedata) {
		this.imagedata = imagedata;
	}
		
	
	
}

package com.mini.form;



public class ActivityInitForm {
	private String activityName ;
	private String id ;
	 
	private String[] itemName;
	private String[] itemFlag;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
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
	
	
}

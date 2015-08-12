package com.mini.form;

/**
 * 用户反馈带图片
 */
public class FeedbackImageForm {
	private String id;/* id */
	private String usernumber;	/* 体检人员工号 */
	private String[] imagename;	/* 文件名称*/
	private String upflag;	/* 上传标志 */
	
	private String feedbackname;			//姓名
	private String feedbackcontent;		//工号

	private String feedbacktime;	//反馈时间
	private String totalResults;//总记录数
	private String currentPage;//当前页数
	private String pageSize;//每页的记录数
	private String phonenumber;
	private String smsContent;
	private String jifen;
	
	public String getJifen() {
		return jifen;
	}

	public void setJifen(String jifen) {
		this.jifen = jifen;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsernumber() {
		return usernumber;
	}

	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}

	
	public String[] getImagename() {
		return imagename;
	}

	public void setImagename(String[] imagename) {
		this.imagename = imagename;
	}

	public String getUpflag() {
		return upflag;
	}

	public void setUpflag(String upflag) {
		this.upflag = upflag;
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

	public String getFeedbacktime() {
		return feedbacktime;
	}

	public void setFeedbacktime(String feedbacktime) {
		this.feedbacktime = feedbacktime;
	}
	
	
	
	
	
	
}

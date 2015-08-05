package com.mini.form;

import java.io.File;




public class PhysicalexamForm {
	/**
	 * 体检信息查询
	 */
	private String id;
	private File file;
	private String fileFileName;
	private String fileContentType; // 文件的内容类型 
	private String totalResults;//总记录数
	private String currentPage;//当前页数
	private String pageSize;//每页的记录数
	private String usernumber;	/* 体检人员工号 */
	private String username;	/* 体检人员姓名 */
	private String physicalposition;		/* 体检地点 */
	
	
	private String physicalreservedate;				/* 体检预约时间 */
	private String physicaldate;		//*正式体检时间 */
	private String physicalplan;/*体检计划 */
	
	private String physicalimportdate;	/*导入时间 */
	
	private String hasRelatives;/*是否有亲属 */
	private String physicalstate;/*体检状态*/
	private String reviewcontent;/*复查内容*/
	private String idnumber;	/* 身份证号 */
	private String relationship;			/* 亲属关系 */
	private String phonenumber;		/* 电话 */
	private String relativeName;		/* 电话 */
	private String physicalcontent;		/* 一些体检内容 */
	private String nophysicalcontentinfor;		/* 无体检信息提示 */
	private String physicaldateedit;		/* 体检日期修改 */
	private String ExpectphysicalDate;		/* 体检日期修改 */
	private String ExpectState;		/* 申请体检日期的状态 */
	private String flag;		/* 申请体检日期的状态 */
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getExpectState() {
		return ExpectState;
	}
	public void setExpectState(String expectState) {
		ExpectState = expectState;
	}
	public String getExpectphysicalDate() {
		return ExpectphysicalDate;
	}
	public void setExpectphysicalDate(String expectphysicalDate) {
		ExpectphysicalDate = expectphysicalDate;
	}
	public String getNophysicalcontentinfor() {
		return nophysicalcontentinfor;
	}
	public void setNophysicalcontentinfor(String nophysicalcontentinfor) {
		this.nophysicalcontentinfor = nophysicalcontentinfor;
	}
	public String getPhysicaldateedit() {
		return physicaldateedit;
	}
	public void setPhysicaldateedit(String physicaldateedit) {
		this.physicaldateedit = physicaldateedit;
	}
	public String getPhysicalcontent() {
		return physicalcontent;
	}
	public void setPhysicalcontent(String physicalcontent) {
		this.physicalcontent = physicalcontent;
	}
	public String getRelativeName() {
		return relativeName;
	}
	public void setRelativeName(String relativeName) {
		this.relativeName = relativeName;
	}
	public String getIdnumber() {
		return idnumber;
	}
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getReviewcontent() {
		return reviewcontent;
	}
	public void setReviewcontent(String reviewcontent) {
		this.reviewcontent = reviewcontent;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhysicalstate() {
		return physicalstate;
	}
	public void setPhysicalstate(String physicalstate) {
		this.physicalstate = physicalstate;
	}
	
	
	public String getHasRelatives() {
		return hasRelatives;
	}
	public void setHasRelatives(String hasRelatives) {
		this.hasRelatives = hasRelatives;
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

	public String getPhysicalplan() {
		return physicalplan;
	}
	public void setPhysicalplan(String physicalplan) {
		this.physicalplan = physicalplan;
	}
	
	public String getPhysicalreservedate() {
		return physicalreservedate;
	}
	public void setPhysicalreservedate(String physicalreservedate) {
		this.physicalreservedate = physicalreservedate;
	}
	public String getPhysicaldate() {
		return physicaldate;
	}
	public void setPhysicaldate(String physicaldate) {
		this.physicaldate = physicaldate;
	}
	public String getPhysicalimportdate() {
		return physicalimportdate;
	}
	public void setPhysicalimportdate(String physicalimportdate) {
		this.physicalimportdate = physicalimportdate;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
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
	
}

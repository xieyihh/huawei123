package com.mini.form;

import java.util.Date;

public class TwoBookInfoForm {
	private String isbn1;//book的isbn10
	private String name1;//书的名称	
	private String image1;//book的图片

	private String bookname;
	private String lablevalue;
	private String infolable;
	private String isbn10;
	private String onlysign;
	
	private String starttime;//开始时间
	private String endtime;//结束时间
	private String classfi;//书籍分类
	private String totalResults;//总记录数
	private String currentPage;//当前页数
	private String pageSize;//每页的记录数
	
	
	
	
	
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
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getClassfi() {
		return classfi;
	}
	public void setClassfi(String classfi) {
		this.classfi = classfi;
	}
	public String getOnlysign() {
		return onlysign;
	}
	public void setOnlysign(String onlysign) {
		this.onlysign = onlysign;
	}
	public String getIsbn10() {
		return isbn10;
	}
	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}
	public String getLablevalue() {
		return lablevalue;
	}
	public void setLablevalue(String lablevalue) {
		this.lablevalue = lablevalue;
	}
	
	public String getInfolable() {
		return infolable;
	}
	public void setInfolable(String infolable) {
		this.infolable = infolable;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getIsbn1() {
		return isbn1;
	}
	public void setIsbn1(String isbn1) {
		this.isbn1 = isbn1;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	
	

}

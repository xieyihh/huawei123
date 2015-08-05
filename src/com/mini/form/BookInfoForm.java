package com.mini.form;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class BookInfoForm implements Serializable {
	private String id;
	private String isbn10;//book的isbn10
	private String bookName;//书的名称	
	private String position;//book的所属仓库
	private Date importdate;//添加时间
	private String  source;//book的来源
	private int number;  //书的数量
	private double price;//书的定价
	private String onlysign;
	private String image;
	private String auto;
	private String keyword;
	private String summary;
	private String infolable;
	private String borrowdate;
	private String borrowposition;
	private String backdate;
	private File file;
	private String fileFileName;
	private String fileContentType; // 文件的内容类型 

	private String classifi;
	

	


	public String getClassifi() {
		return classifi;
	}
	public void setClassifi(String classifi) {
		this.classifi = classifi;
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
	public String getBackdate() {
		return backdate;
	}
	public void setBackdate(String backdate) {
		this.backdate = backdate;
	}
	public String getBorrowdate() {
		return borrowdate;
	}
	public void setBorrowdate(String borrowdate) {
		this.borrowdate = borrowdate;
	}
	public String getBorrowposition() {
		return borrowposition;
	}
	public void setBorrowposition(String borrowposition) {
		this.borrowposition = borrowposition;
	}
	public String getInfolable() {
		return infolable;
	}
	public void setInfolable(String infolable) {
		this.infolable = infolable;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getAuto() {
		return auto;
	}
	public void setAuto(String auto) {
		this.auto = auto;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
//	public String getIsbn1() {
//		return isbn1;
//	}
//	public void setIsbn1(String isbn1) {
//		this.isbn1 = isbn1;
//	}
//	public String getIsbn2() {
//		return isbn2;
//	}
//	public void setIsbn2(String isbn2) {
//		this.isbn2 = isbn2;
//	}
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
	
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Date getImportdate() {
		return importdate;
	}
	public void setImportdate(Date importdate) {
		this.importdate = importdate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	

}

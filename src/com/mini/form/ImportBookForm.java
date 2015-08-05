package com.mini.form;

import java.io.File;
import java.io.Serializable;

public class ImportBookForm implements Serializable {
	private File file;
	private String fileFileName;
	private String fileContentType; // 文件的内容类型 
	private String onlysign;
	private String bookName;
	private String isbn10;
	private String resultjsp;
	private String position;
	private String source;
	private String id;
	private String classifi;
	private String image;
	private String summary;
	private String author;
	private String totalResults;//总记录数
	private String currentPage;//当前页数
	private String number;//书籍的数量
	private String importdate;
	private String downstate;
	private String Isisbn10;
	private String bookid;
	private String pageSize;
	
	
	

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	public String getIsisbn10() {
		return Isisbn10;
	}

	public void setIsisbn10(String isisbn10) {
		Isisbn10 = isisbn10;
	}

	public String getDownstate() {
		return downstate;
	}

	public void setDownstate(String downstate) {
		this.downstate = downstate;
	}

	public String getImportdate() {
		return importdate;
	}

	public void setImportdate(String importdate) {
		this.importdate = importdate;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public String getClassifi() {
		return classifi;
	}

	public void setClassifi(String classifi) {
		this.classifi = classifi;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getResultjsp() {
		return resultjsp;
	}

	public void setResultjsp(String resultjsp) {
		this.resultjsp = resultjsp;
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

	public String getOnlysign() {
		return onlysign;
	}

	public void setOnlysign(String onlysign) {
		this.onlysign = onlysign;
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}


}

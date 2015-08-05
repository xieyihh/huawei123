package com.mini.entity;

import java.util.Date;
/**
 * 从网上下载的豆瓣书籍信息
 * @author s
 *
 */
public class Bookinfo {
	private int id;  //book的id	
	private String isbn10;//book的isbn10
	private String name;//书的名称
	private String price;
	private String image;
	private String auto;
	private String keyword;
	private String summary;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIsbn10() {
		return isbn10;
	}
	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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
	




}

package com.mini.entity;

import java.util.Date;
/**
 * 推荐书籍
 * @author s
 *
 */
public class BookNice {

	private int id;  //id
	private String isbn10;//推荐书籍的Isbn10	
	private String state;//推荐书籍的状态
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	



}

package com.mini.form;

import java.util.Date;

public class AllBookInfoForm {
	private int id;  //book的id	
	private String isbn10;//book的isbn10
	private String name;//书的名称
	
	private String position;//book的所属仓库
	private String importdate;//添加时间
	private String  source;//book的来源
	private int number;  //书的数量
	private double price;//书的定价
	private String onlysign;//唯一标志
	private String classifi;//书籍分类
	private String image;//书籍分类

	
	
	public String getClassifi() {
		return classifi;
	}
	public void setClassifi(String classifi) {
		this.classifi = classifi;
	}
	public String getOnlysign() {
		return onlysign;
	}
	public void setOnlysign(String onlysign) {
		this.onlysign = onlysign;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsbn10() {
		return isbn10;
	}
	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getImportdate() {
		return importdate;
	}
	public void setImportdate(String importdate) {
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

package com.mini.entity;

import java.util.Date;
/**
 * 导入的书籍信息
 * @author s
 *
 */
public class ImportBookInfo {
	private int id;  //book的id	
	private String isbn10;//book的isbn10
	private String name;//书的名称
	
	private String position;//book的所属仓库
	private Date importdate;//添加时间
	private String  source;//book的来源
	private String number;  //书的编号
	private double price;//书的定价
	private String onlysign;//唯一标志
	private String state;//是否删除状态标志
	private String classifi;//分类标志
	
	public String getClassifi() {
		return classifi;
	}
	public void setClassifi(String classifi) {
		this.classifi = classifi;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	

	


}

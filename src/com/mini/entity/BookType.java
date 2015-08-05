package com.mini.entity;



/**
 * 书籍进行分类
 * 
 */
public class BookType {
	// 获取到的凭证
	private String id;
	//获取父类
	private String parent;
	//当前类的分类标准
	private String  title;
	//当前类的名称
	private String  name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
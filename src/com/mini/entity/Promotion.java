package com.mini.entity;

import java.sql.Date;

/**
 * 促销信息实体类
 * @author 雷晓冰	2014-11-28
 */
public class Promotion {
	private int id;
	private String title;		//促销信息标题
	private String detail;	//详情
	private Date date;		//发布日期
	private Integer publisherId;		//发布人ID
	private String publisher;	//发布人
	private String photoUrl;		//图片路径
	private Integer favorAmount;	//赞的数量
	private String favorId;				//点赞的用户的人ID
	private Integer state;		//状态~0：未被审核；1：审核通过
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public Integer getFavorAmount() {
		return favorAmount;
	}
	public void setFavorAmount(Integer favorAmount) {
		this.favorAmount = favorAmount;
	}
	public String getFavorId() {
		return favorId;
	}
	public void setFavorId(String favorId) {
		this.favorId = favorId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}

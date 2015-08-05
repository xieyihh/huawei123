package com.mini.entity;

import java.sql.Timestamp;

/**
 * 讨论交流模块的话题实体类
 * @author 雷晓冰	2014-11-23
 */
public class Topic {
	private int id;	
	private String title;		//标题
	private String detail;		//话题详细信息
	private Timestamp foundTime;		//话题创建时间
	private Integer founderId;		//创建人ID
	private String founderName;	//创建人姓名
	private Integer favorAmount;	//赞的数量
	private String favorId;				//点赞的用户的人ID
	private Integer commentAmount;	//评论数量
	private Timestamp updateTime;		//更新时间~最后一条评论的时间，若为空则为创建时间
	
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
	public Timestamp getFoundTime() {
		return foundTime;
	}
	public void setFoundTime(Timestamp foundTime) {
		this.foundTime = foundTime;
	}
	public Integer getFounderId() {
		return founderId;
	}
	public void setFounderId(Integer founderId) {
		this.founderId = founderId;
	}
	public String getFounderName() {
		return founderName;
	}
	public void setFounderName(String founderName) {
		this.founderName = founderName;
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
	public Integer getCommentAmount() {
		return commentAmount;
	}
	public void setCommentAmount(Integer commentAmount) {
		this.commentAmount = commentAmount;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}

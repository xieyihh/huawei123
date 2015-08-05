package com.mini.entity;

import java.sql.Timestamp;

/**
 * 讨论交流模块的评论实体类
 * @author 雷晓冰	2014-11-23
 */
public class Comment {
	private int id;
	private Integer topicId;		//所属话题
	private String detail;	//评论详情
	private Timestamp time;		//评论时间
	private Integer userId;	//用户ID
	private String userName;	//用户姓名
	private Integer favorAmount;	//赞的数量
	private String favorId;				//点赞的用户的人ID
	private String replyUser;		//回复的用户（评论针对的用户）
	private String replyDetail;		//回复的用户内容
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getTopicId() {
		return topicId;
	}
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getReplyUser() {
		return replyUser;
	}
	public void setReplyUser(String replyUser) {
		this.replyUser = replyUser;
	}
	public String getReplyDetail() {
		return replyDetail;
	}
	public void setReplyDetail(String replyDetail) {
		this.replyDetail = replyDetail;
	}
}

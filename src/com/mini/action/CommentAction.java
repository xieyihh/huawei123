package com.mini.action;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;

import com.mini.entity.Comment;
import com.mini.entity.User;
import com.mini.service.CommentService;
import com.mini.util.PageModel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 处理评论相关请求的Action
 * @author 雷晓冰	2014-11-24
 */
public class CommentAction extends BaseAction implements ModelDriven<Comment> {
	private static final long serialVersionUID = -7908587560363057980L;
	private Comment comment = new Comment();
	private CommentService service;
	private int pageNum;				//页号
	private int pageSize;				//数量
	public CommentService getService() {
		return service;
	}
	public void setService(CommentService service) {
		this.service = service;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	@Override
	public Comment getModel() {
		return this.comment;
	}
	/**
	 * 添加一条评论
	 */
	public void add() {
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		this.comment.setUserId(user.getId());
		this.comment.setUserName(user.getName());
		this.comment.setTime(time);
		this.comment.setFavorAmount(0);
		this.comment.setFavorId("");
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag = service.add(this.comment);
		if(flag) {
			msg = "评论添加成功";
		} else {
			msg = "评论添加失败，请稍后重试";
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.comment = new Comment();
		writeJson(map);
	}
	/**
	 * 删除一条评论
	 */
	public void delete() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag = service.delete(this.comment.getId(), this.comment.getTopicId());
		if(flag) {
			msg = "评论删除成功";
		} else {
			msg = "评论删除失败，请稍后重试";
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.comment = new Comment();
		writeJson(map);
	}
	/**
	 * 查找/获取评论
	 */
	public void find() {
		PageModel<Comment> model = service.findByConditions(this.comment, pageNum, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", model.getTotalRecords());
		map.put("list", model.getList());
		this.comment = new Comment();
		writeJson(map);
	}
}

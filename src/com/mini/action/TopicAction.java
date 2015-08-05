package com.mini.action;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;

import com.mini.entity.Topic;
import com.mini.entity.User;
import com.mini.service.TopicService;
import com.mini.util.PageModel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 处理话题相关请求的Action类
 * @author 雷晓冰	2014-11-24
 */
public class TopicAction extends BaseAction implements ModelDriven<Topic> {
	private static final long serialVersionUID = 8727404029195161549L;
	private Topic topic = new Topic();
	private TopicService service;
	private int pageNum;				//页号
	private int pageSize;				//数量
	
	public TopicService getService() {
		return service;
	}
	public void setService(TopicService service) {
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
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	@Override
	public Topic getModel() {
		return this.topic;
	}
	/**
	 * 创建话题
	 */
	public void add() {
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		this.topic.setFounderId(user.getId());
		this.topic.setFounderName(user.getName());
		this.topic.setFoundTime(time);
		this.topic.setUpdateTime(time);
		this.topic.setFavorAmount(0);
		this.topic.setFavorId("");
		this.topic.setCommentAmount(0);
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag = service.add(this.topic);
		if(flag) {
			msg = "话题创建成功";
		} else {
			msg = "话题创建失败，请稍后重试";
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.topic = new Topic();
		writeJson(map);
	}
	/**
	 * 删除一个话题
	 */
	public void delete() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag = service.delete(this.topic.getId());
		if(flag) {
			msg = "话题删除成功";
		} else {
			msg = "话题删除失败，请稍后重试";
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.topic = new Topic();
		writeJson(map);
	}
	/**
	 * 查找话题
	 */
	public void find() {
		PageModel<Topic> model = service.findByConditions(this.topic, pageNum, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", model.getTotalRecords());
		map.put("list", model.getList());
		this.topic = new Topic();
		writeJson(map);
	}
	/**
	 * 根据ID查看话题详细信息
	 */
	public void findById() {
		Topic topic = service.findById(this.topic.getId());
		this.topic = new Topic();
		writeJson(topic);
	}
	/**
	 * 给一个话题点赞
	 */
	public void favor() {
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		boolean flag = false;
		Topic topic = service.findById(this.topic.getId());
		if(topic.getFavorId().contains(user.getId() + "&&")) {
			flag = false;
			msg = "已经赞过，不能重复点赞";
		} else {
			String favorId = topic.getFavorId() + user.getId() + "&&";
			flag = service.updateFavor(this.topic.getId(), 1, favorId);
			if(flag) {
				msg = "点赞成功";
			} else {
				msg = "点赞失败，请稍后重试";
			}
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.topic = new Topic();
		writeJson(map);
	}

}

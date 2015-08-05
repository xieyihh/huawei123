package com.mini.action;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;

import com.mini.entity.AssociationActivity;
import com.mini.entity.User;
import com.mini.service.AssociationActivityService;
import com.mini.service.UserService;
import com.mini.util.IdListUtil;
import com.mini.util.PageModel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 处理协会活动相关请求的Action类
 * @author 雷晓冰 2014-11-02
 */
public class AssociationActivityAction extends BaseAction implements ModelDriven<AssociationActivity> {
	private static final long serialVersionUID = -5309369727676746508L;
	private AssociationActivity activity = new AssociationActivity();
	private AssociationActivityService service;
	private UserService userService;
	private int pageNum;				//页号
	private int pageSize;				//行数
	
	public AssociationActivityService getService() {
		return service;
	}
	public void setService(AssociationActivityService service) {
		this.service = service;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
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
	public void setActivity(AssociationActivity activity) {
		this.activity = activity;
	}
	@Override
	public AssociationActivity getModel() {
		return activity;
	}
	/**
	 * 发起活动
	 */
	public void add() {
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		this.activity.setInitiatorId(user.getId());
		this.activity.setInitiator(user.getName());
		this.activity.setDate(new Date(System.currentTimeMillis()));
		this.activity.setAttendees(user.getId() + "&&");
		this.activity.setSize(1);
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag = service.add(this.activity);
		if(flag) {
			msg = "协会发布成功";
		} else {
			msg = "协会发布失败，请稍后重试";
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.activity = new AssociationActivity();
		writeJson(map);
	}
	/**
	 * 获取协会活动信息
	 */
	public void find() {
		PageModel<AssociationActivity> model = service.findByConditions(this.activity, pageNum, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", model.getTotalRecords());
		map.put("list", model.getList());
		this.activity = new AssociationActivity();
		writeJson(map);
	}
	/**
	 * 查看协会活动详细信息
	 */
	public void viewDetail() {
		this.activity = service.findById(this.activity.getId());
		writeJson(this.activity);
		this.activity = new AssociationActivity();
	}
	/**
	 * 获取参加协会活动的人员姓名
	 */
	public void getAttendees() {
		List<Integer> attendees = IdListUtil.string2List(this.activity.getAttendees());
		Map<String, String> map = userService.findMap(attendees);
		this.activity = new AssociationActivity();
		writeJson(map);
	}
	/**
	 * 协会活动报名
	 */
	public void apply() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		boolean flag;
		this.activity = service.findById(this.activity.getId());
		if(this.activity == null) {
			msg = "协会活动不存在，操作失败";
			flag = false;
		} else {
			//将用户ID加入活动参加者ID中
			String attendees = this.activity.getAttendees() + user.getId() + "&&";
			flag = service.updateAttendee(this.activity.getId(), attendees, 1);
			if(flag) {
				msg = "活动报名成功";
			} else {
				msg = "活动报名失败，请稍后重试";
			}
		}
		this.activity = new AssociationActivity();
		map.put("success", flag);
		map.put("msg", msg);
		writeJson(map);
	}
	/**
	 * 退出协会活动
	 */
	public void quit() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		boolean flag;
		this.activity = service.findById(this.activity.getId());
		if(this.activity == null) {
			msg = "协会活动不存在，操作失败";
			flag = false;
		} else {
			//删除活动参加者中包含的用户ID
			String attendees = this.activity.getAttendees().replaceAll(user.getId() + "&&", "");
			flag = service.updateAttendee(this.activity.getId(), attendees, -1);
			if(flag) {
				msg = "退出成功";
			} else {
				msg = "无法退出，请稍后重试";
			}
		}
		this.activity = new AssociationActivity();
		map.put("success", flag);
		map.put("msg", msg);
		writeJson(map);
	}
}

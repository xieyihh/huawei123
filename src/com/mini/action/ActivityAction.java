package com.mini.action;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;

import com.mini.entity.Activity;
import com.mini.entity.User;
import com.mini.service.ActivityService;
import com.mini.service.UserService;
import com.mini.util.IdListUtil;
import com.mini.util.PageModel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 处理大型活动相关请求的Action类
 * @author 雷晓冰 2014-11-02
 */
public class ActivityAction extends BaseAction implements ModelDriven<Activity> {
	private static final long serialVersionUID = 5231379954087826098L;
	private Activity activity = new Activity();
	private ActivityService service;
	private UserService userService;
	private String userProperty;		//用户所填写的大型活动自定义属性值
	private int pageNum;				//页号
	private int pageSize;				//行数
	
	public ActivityService getService() {
		return service;
	}
	public void setService(ActivityService service) {
		this.service = service;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getUserProperty() {
		return userProperty;
	}
	public void setUserProperty(String userProperty) {
		this.userProperty = userProperty;
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
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	@Override
	public Activity getModel() {
		return activity;
	}
	/**
	 * 发起大型活动
	 */
	public void add() {
		//获取用户的信息
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag;
		if(!user.getAuthority().contains("activity") && user.getLevel() != 2) {
			flag = false;
			msg = "您不是大型活动管理员，无法发起活动";
		} else {
			this.activity.setInitiatorId(user.getId());
			this.activity.setInitiator(user.getName());
			this.activity.setDate(new Date(System.currentTimeMillis()));
			this.activity.setAttendees("");
			this.activity.setAttendeesProperty("");
			this.activity.setSize(0);
			flag = service.add(this.activity);
			if(flag) {
				msg = "大型活动发布成功";
			} else {
				msg = "大型活动发布失败，请稍后重试";
			}
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.activity = new Activity();
		writeJson(map);
	}
	/**
	 *	获取大型活动信息
	 */
	public void find() {
		PageModel<Activity> model = service.findByConditions(this.activity, pageNum, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", model.getTotalRecords());
		map.put("list", model.getList());
		this.activity = new Activity();
		writeJson(map);
	}
	/**
	 * 查看大型活动详细信息
	 */
	public void viewDetail() {
		this.activity = service.findById(this.activity.getId());
		writeJson(this.activity);
		this.activity = new Activity();
	}
	/**
	 * 获取参加大型活动的人员姓名
	 */
	public void getAttendees() {
		List<Integer> attendees = IdListUtil.string2List(this.activity.getAttendees());
		Map<String, String> map = userService.findMap(attendees);
		this.activity = new Activity();
		writeJson(map);
	}
	/**
	 * 大型活动报名
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
			msg = "大型活动不存在，操作失败";
			flag = false;
		} else {
			//将用户ID加入活动参加者ID中
			String attendees = this.activity.getAttendees() + user.getId() + "&&";
			String attendeesProperty = this.activity.getAttendeesProperty()  + userProperty;
			flag = service.updateAttendee(this.activity.getId(), attendees, attendeesProperty, 1);
			if(flag) {
				msg = "活动报名成功";
			} else {
				msg = "活动报名失败，请稍后重试";
			}
		}
		this.activity = new Activity();
		map.put("success", flag);
		map.put("msg", msg);
		writeJson(map);
	}
	/**
	 * 退出大型活动
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
			msg = "大型活动不存在，操作失败";
			flag = false;
		} else {
			//删除活动参加者中包含的用户ID
			String attendees = this.activity.getAttendees().replaceAll(user.getId() + "&&", "");
			String attendeesProperty = this.activity.getAttendeesProperty().replaceAll(user.getId() + "~[^&]+&&", "");
			flag = service.updateAttendee(this.activity.getId(), attendees, attendeesProperty, -1);
			if(flag) {
				msg = "退出成功";
			} else {
				msg = "无法退出，请稍后重试";
			}
		}
		this.activity = new Activity();
		map.put("success", flag);
		map.put("msg", msg);
		writeJson(map);
	}
	/**
	 * 修改报名信息
	 */
	public void updateApplyInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		boolean flag;
		this.activity = service.findById(this.activity.getId());
		if(this.activity == null) {
			msg = "大型活动不存在，操作失败";
			flag = false;
		} else {
			//删除活动参加者中包含的用户ID
			String attendees = this.activity.getAttendees();
			String attendeesProperty = this.activity.getAttendeesProperty().replaceAll(user.getId() + "~[^&]+&&", userProperty);
			flag = service.updateAttendee(this.activity.getId(), attendees, attendeesProperty, 0);
			if(flag) {
				msg = "报名信息修改成功";
				map.put("attendeesProperty", attendeesProperty);
			} else {
				msg = "报名信息修改失败，请稍后重试";
			}
		}
		this.activity = new Activity();
		map.put("success", flag);
		map.put("msg", msg);
		writeJson(map);
	}
}

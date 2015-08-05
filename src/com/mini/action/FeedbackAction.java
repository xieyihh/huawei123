package com.mini.action;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.mini.entity.Feedback;
import com.mini.service.FeedbackService;
import com.mini.util.PageModel;
import com.opensymphony.xwork2.ModelDriven;

public class FeedbackAction extends BaseAction implements ModelDriven<Feedback> {
	private static final long serialVersionUID = -6872667406307155523L;
	private Feedback feedback = new Feedback();
	private FeedbackService service;
	private int pageNum;				//页号
	private int pageSize;				//数量
	
	public FeedbackService getService() {
		return service;
	}
	public void setService(FeedbackService service) {
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
	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}
	@Override
	public Feedback getModel() {
		return this.feedback;
	}
	/**
	 * 添加反馈信息
	 */
	public void addFeedback() {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		this.feedback.setTime(time);
		this.feedback.setState(0);
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag = service.add(this.feedback);
		if(flag) {
			msg = "反馈信息提交成功，等待管理员处理";
		} else {
			msg = "反馈信息提交失败，请稍后重试";
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.feedback = new Feedback();
		writeJson(map);
	}
	/**
	 * 删除反馈信息
	 */
	public void delete() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag = service.delete(this.feedback.getId());
		if(flag) {
			msg = "反馈信息删除成功";
		} else {
			msg = "反馈信息删除失败，请稍后重试";
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.feedback = new Feedback();
		writeJson(map);
	}
	/**
	 * 修改状态
	 */
	public void updateState() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag = service.updateState(this.feedback.getId(), this.feedback.getState());
		if(flag) {
			msg = "状态修改成功";
		} else {
			msg = "状态修改失败，请稍后重试";
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.feedback = new Feedback();
		writeJson(map);
	}
	/**
	 * 获取反馈信息
	 */
	public void find() {
		PageModel<Feedback> model = service.findByConditions(this.feedback, pageNum, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", model.getTotalRecords());
		map.put("list", model.getList());
		this.feedback = new Feedback();
		writeJson(map);
	}

}

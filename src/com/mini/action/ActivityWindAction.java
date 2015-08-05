package com.mini.action;


import java.io.IOException;

import org.apache.struts2.dispatcher.SessionMap;

import net.sf.json.JSONObject;

import com.mini.entity.User;
import com.mini.form.ActivityWindForm;
import com.mini.service.ActivityService;
import com.mini.service.ActivityWindService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class ActivityWindAction  extends BaseAction implements ModelDriven<ActivityWindForm> {
	/**
	 * 	
	 */
	private static final long serialVersionUID = 6277421088034123303L;
	private ActivityWindForm activityWindForm = new ActivityWindForm();
	private ActivityWindService service;
	
	public ActivityWindService getService() {
		return service;
	}
	public void setService(ActivityWindService service) {
		this.service = service;
	}
	@Override
	public ActivityWindForm getModel() {
		// TODO Auto-generated method stub
		return activityWindForm;
	}
	/**
	 * 获取数据字典中的活动项
	 */
	public void getActivityDictionary(){
		JSONObject result=service.getActivityDictionary(activityWindForm);
		returnObject(result);
	}

	/**
	 * 保存活动的发布信息
	 */
	public void saveActivityStart(){
	
		try {
			String result = service.saveActivityStart(activityWindForm);
			if(result.equals("success")){
				returnObject("success");
			}else{
				resultError("202", "error");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			resultError("201", "exception");
		}
		
	}
	/**
	 * 获取活动开始的信息
	 */
	public void getActivityStart(){
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");		
		JSONObject result=service.getActivityStart(activityWindForm);
		if(result==null){
			resultError("301", "success");
		}else{
			returnObject(result);
		}		
		
	}
	public void getActivityArray(){		
		JSONObject result=service.getActivityDictionary(activityWindForm);
		returnObject(result);
	}
	/**
	 * 保存活动的初始化信息
	 */
	public void saveActivityInit(){
		
		String result = service.saveActivityInit(activityWindForm);
		if(result.equals("success")){
			returnObject("success");
		}else{
			resultError("202", "error");
		}
		
	}
	/**
	 * 获取活动的初始化信息
	 */
	public void getActivityInit(){
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		if(user==null){
			resultError("301", "PleaseUser");
		}
		
		JSONObject result=service.getActivityInit(activityWindForm,user);
		if(result==null){
			resultError("301", "success");
		}else{
			returnObject(result);
		}
		
	}
	/**
	 * 保存活动的报名信息
	 */
	public String saveActivitySignup(){
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		if(user==null){
			getRequest().setAttribute("errormsg", "请登录");
			return "error";
		}
			
		try {
			String result = service.saveActivitySignup(activityWindForm,user);
			if(result.equals("success")){				
				return "success";
			}else{
				getRequest().setAttribute("errormsg", "信息错误");
				return "error";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			getRequest().setAttribute("errormsg", "信息错误");
			return "error";
		}
			
	}
	public void getActivityStatusDictionary(){
		
		JSONObject result=service.getActivityStatusDictionary(activityWindForm);
		if(result==null){
			resultError("301", "success");
		}else{
			returnObject(result);
		}
		
	}
	public void getActivityStatus(){
		
		JSONObject result=service.getActivityStatus(activityWindForm);
		if(result==null){
			resultError("301", "success");
		}else{
			returnObject(result);
		}
		
	}
	public void getallActivityStatus(){
		
		JSONObject result=service.getallActivityStatus(activityWindForm);
		if(result==null){
			resultError("301", "success");
		}else{
			returnObject(result);
		}
		
	}
	public void updateActivityStatus(){
		
		String result=service.updateActivityStatus(activityWindForm);
		if(result==null){
			resultError("301", "success");
		}else{
			returnObject(result);
		}
		
	}
	/**
	 * 获取用户的报名情况
	 */
	public void getUserActivityStatus(){
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		if(user==null){
			resultError("301", "Pleaselogin");			
		}
		String results=service.getUserActivityStatus(activityWindForm,user);
		JSONObject result = new JSONObject();
		if(results.equals("yes")){
			result.put("code", "202");		
			result.put("message", "success");
			result.put("context", "1");
			writeJson(result);
		}else{
			if(results.equals("no")){
				result.put("code", "202");		
				result.put("message", "success");
				result.put("context", "0");
				writeJson(result);
			}else{
				result.put("code", "304");		
				result.put("message", "error");
				result.put("context", "fail");
				writeJson(result);
			}
		}
		
		
		
	}
	

}

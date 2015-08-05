package com.mini.action;

import net.sf.json.JSONObject;

import org.apache.struts2.dispatcher.SessionMap;

import com.mini.entity.User;
import com.mini.form.UserlevelForm;
import com.mini.service.UserlevelService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;


/**
 * 用户权限的有关请求
 * 
 */
public class UserlevelAction extends BaseAction implements ModelDriven<UserlevelForm> {
	private UserlevelForm userlevelForm=new UserlevelForm();
	private UserlevelService service;
	public UserlevelForm getModel() {
		// TODO Auto-generated method stub
		return userlevelForm;
	}
	public UserlevelService getService() {
		return service;
	}
	public void setService(UserlevelService service) {
		this.service = service;
	}
	/**
	 * 获取各种管理员的信息
	 */
	public void getuserlevel(){
		
		JSONObject result=service.getuserlevel();
		returnObject(result);
	}
	/**
	 * 保存管理员等级的权限
	 */
	public void saveuserrolepopedom(){
		
		String result=service.saveuserrolepopedom(userlevelForm);
		if(result.equals("success")){
			returnObject(result);
		}else{
			resultError("102", "saveerror");
		}
		
	}
	/**
	 * 获取管理的等级权限信息
	 */
	public void getuserrolepopedom(){
		
		JSONObject result=service.getuserrolepopedom(userlevelForm);
		returnObject(result);
	}
	/**
	 *获取用户的信息
	 */
	public void getuserAuthority(){
		
		JSONObject result=service.getuserinfo(userlevelForm);
		returnObject(result);
	}
	/**
	 * 保存用户的等级
	 */
	public void savelevel(){
		
		String result=service.savelevel(userlevelForm);
		if(result.equals("success")){
			returnObject(result);
		}else{
			resultError("105", "error");
		}
		
	}
	/**
	 * 获取管理的等级权限信息
	 */
	public void getuserrolepopedomBylogin(){
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		JSONObject result=service.getuserrolepopedomBylogin(userlevelForm,user);
		returnObject(result);
	}
	


}

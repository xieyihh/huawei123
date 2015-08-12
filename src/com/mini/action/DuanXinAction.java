package com.mini.action;

import net.sf.json.JSONObject;

import org.apache.struts2.dispatcher.SessionMap;

import com.mini.entity.User;
import com.mini.form.DuanXinForm;
import com.mini.service.SmsService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;


@SuppressWarnings("serial")
public class DuanXinAction extends BaseAction implements ModelDriven<DuanXinForm>{

	private DuanXinForm duanXinForm=new DuanXinForm();
	private SmsService service;
	@Override
	public DuanXinForm getModel() {
		// TODO Auto-generated method stub
		return duanXinForm;
	}
	
	public DuanXinForm getDuanXinForm() {
		return duanXinForm;
	}

	public void setDuanXinForm(DuanXinForm duanXinForm) {
		this.duanXinForm = duanXinForm;
	}

	public SmsService getService() {
		return service;
	}

	public void setService(SmsService service) {
		this.service = service;
	}

	/**
	 * 发送验证码给手机端
	 * @throws Exception 
	 */
	public void Sendsecuritycode() throws Exception{
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		String result=service.savesecuritycode(duanXinForm,user);

		if(!result.equals("success")){
			//用户不存在
			resultError("100","noUser");
		}
		returnObject("success");
	}
	/**
	 * 判断验证码是否正确
	 */
	public void Judgecode(){
		String result=service.getJudgesecuritycode(duanXinForm);
		if(result.equals("error")){
			//用户不存在
			resultError("100","error");
		}
		JSONObject json=new JSONObject();
		json.put("username", result);
		returnObject(json);
		
	}
	/**
	 * 修改密码
	 */
	public void Changepassword(){ 
//		ActionContext context = ActionContext.getContext();
//		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		String randomStr=(String) ActionContext.getContext().getSession().get("randomStr");

		 String randCode=duanXinForm.getRandCode();
		 System.out.print(randCode+";"+randomStr);
		 if(randCode.trim().equalsIgnoreCase(randomStr)){
			 String result=service.updatepassword(duanXinForm);
			 if(!result.equals("success")){
			//用户不存在
				 resultError("100","error");
			 }
			 returnObject("success");
		 }else{
			 resultError("100","RandCodeerror");
		 }
		
	}
		

}

package com.mini.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.mini.entity.Feedback;
import com.mini.form.FeedbackForm;
import com.mini.form.FeedbackImageForm;
import com.mini.service.FeedbackService;
import com.mini.service.SmsService;
import com.mini.util.ExcelFileGenerator;
import com.mini.util.PageModel;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 在管理后台显示反馈的信息
 * @author s
 *
 */
public class FeedbackImageAction extends BaseAction implements ModelDriven<FeedbackImageForm> {
	private static final long serialVersionUID = -6872667406307155523L;
	private FeedbackImageForm feedbackImageForm  = new FeedbackImageForm();
	private FeedbackService service;
	private SmsService smsService;
	
	public FeedbackImageForm getFeedbackImageForm() {
		return feedbackImageForm;
	}
	public void setFeedbackImageForm(FeedbackImageForm feedbackImageForm) {
		this.feedbackImageForm = feedbackImageForm;
	}
	
	public SmsService getSmsService() {
		return smsService;
	}
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}
	public FeedbackService getService() {
		return service;
	}
	public void setService(FeedbackService service) {
		this.service = service;
	}
	
	public void setFeedback(FeedbackImageForm feedback) {
		this.feedbackImageForm = feedback;
	}
	@Override
	public FeedbackImageForm getModel() {
		return this.feedbackImageForm;
	}
	/**
	 * 获取用户反馈的一些信息带图片
	 */
	public void getFeedbackImage(){
		JSONObject result=service.getFeedbackImage(feedbackImageForm);
		returnObject(result);
	}
	/**
	 * 对用户的反馈发送短信
	 */
	public void feedBackSendSms(){
		String result=smsService.feedBackSendSms(feedbackImageForm);
		
		if(result.equals("success")){
			returnObject(result);
		}else{
			if(result.equals("overlength")){
				resultError("201", "overlength");
			}else{
				resultError("202", "error");
			}
		}
		
	}
	/**
	 * 对反馈建议进行打分
	 */
	public void addJifen(){
		String result=service.addJifen(feedbackImageForm);
		if(result.equals("success")){
			returnObject(result);
		}else{
			resultError("404", "error");
		}
		
	}
	
	
	
}

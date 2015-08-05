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
	
	
	
}

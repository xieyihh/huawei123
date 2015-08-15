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
import com.mini.util.CsvExport;
import com.mini.util.ExcelFileGenerator;
import com.mini.util.InitString;
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
	public String feedbakcjsp(){
		return "success";
	}
	public void getFeedbackImage(){
		JSONObject result=service.getFeedbackImage(feedbackImageForm);
		returnObject(result);
	}
	/**
	 * 导出带图片的反馈信息
	 */
	public void exportFeedbackImage(){
		
		ArrayList<String> filedData = service.exportFeedbackImage(feedbackImageForm);	
		if(filedData==null){
			filedData=new ArrayList<String>();
		}
		HttpServletResponse response=getResponse();
		OutputStream out;
		try {
			out = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment;filename=\""+ new String(("反馈信息.csv").getBytes("GBK"),"ISO-8859-1") + "\"");    

			//设置导出Excel报表的导出形式
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			//设置导出Excel报表的导出形式
			response.setContentType("text/plain;charset=utf-8");
			CsvExport csvout=new CsvExport(InitString.FEED_BACKTITLE, filedData);
			csvout.createCsv(out);
			out.flush();
			//关闭输出流
			if(out!=null){
				out.close();
			}
		} catch (IOException e) {
			
			resultError("404", "error");
		}		
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
	/**
	 * 获取目前的建议排行
	 */
	public void getFeedBackRanking(){
		JSONObject result=service.getFeedBackRanking(feedbackImageForm);
		returnObject(result);
		
	}
	
	
	
}

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

import com.mini.entity.Feedback;
import com.mini.form.FeedbackForm;
import com.mini.service.FeedbackService;
import com.mini.util.ExcelFileGenerator;
import com.mini.util.PageModel;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 在管理后台显示反馈的信息
 * @author s
 *
 */
public class FeedbackShowAction extends BaseAction implements ModelDriven<FeedbackForm> {
	private static final long serialVersionUID = -6872667406307155523L;
	private FeedbackForm feedbackForm  = new FeedbackForm();
	private FeedbackService service;

	
	public FeedbackService getService() {
		return service;
	}
	public void setService(FeedbackService service) {
		this.service = service;
	}
	
	public void setFeedback(FeedbackForm feedback) {
		this.feedbackForm = feedback;
	}
	@Override
	public FeedbackForm getModel() {
		return this.feedbackForm;
	}
	/**
	 * 管理后台显示反馈信息
	 * @return
	 */
	public String feedbackhome(){
		List<FeedbackForm> returnlist =service.findallfeedback();
		getRequest().setAttribute("feedbacklist", returnlist);
		return "success";
		
	}
	/**
	 * 将反馈信息导出excel
	 * @return
	 * @throws Exception 
	 */
	public void feedbackexport() throws Exception{

		//获取导出的表头和数据
		//获取表头,存放到ArrayList filedName对象中(登录名	用户姓名	性别	联系电话	是否在职)
		ArrayList filedName = service.getfeedbackExcelFiledNameList(); 
		ArrayList filedData = service.getfeedbackExcelFiledDataList();	
		HttpServletResponse response=getResponse();
		OutputStream out = response.getOutputStream();
		//重置输出流
		response.reset();
		response.setHeader("Content-disposition", "attachment;filename=\""+ new String(("反馈信息.xls").getBytes("GBK"),"ISO-8859-1") + "\"");    

		//设置导出Excel报表的导出形式
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");

		ExcelFileGenerator generator = new ExcelFileGenerator(filedName,filedData);
		generator.expordExcel(out);
		System.setOut(new PrintStream(out));
		out.flush();
		//关闭输出流
		if(out!=null){
			out.close();
		}
		
	}
	
	
}

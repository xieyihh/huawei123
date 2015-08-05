package com.mini.action;


import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.dispatcher.SessionMap;

import com.mini.entity.ImportBookInfo;
import com.mini.entity.User;
import com.mini.form.AllBookInfoForm;
import com.mini.form.ImportBookForm;
import com.mini.form.PhysicalexamForm;
import com.mini.form.TestForm;
import com.mini.service.ImportBookService;
import com.mini.util.CsvExport;
import com.mini.util.ExcelFileGenerator;
import com.mini.util.InitString;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class ImportbookAction extends BaseAction implements ModelDriven<ImportBookForm>{

	private ImportBookForm importBookForm=new ImportBookForm();
	private ImportBookService service;
	/**
	 * 书籍信息导入模版
	 * @return
	 * @throws Exception
	 */
	public void downloadimportbooktemplates() throws Exception{			
		ArrayList<String> filedData = new ArrayList<String>();				
		String line="9787115377241,足球运动系统训练,3楼  ,4,捐赠,文化";
		filedData.add(line);
		HttpServletResponse response=getResponse();
		OutputStream out = response.getOutputStream();
		//重置输出流
		response.reset();
		response.setHeader("Content-disposition", "attachment;filename=\""+ new String(("书籍信息导入模版.csv").getBytes("GBK"),"ISO-8859-1") + "\"");   
		response.setContentType("text/plain; charset=utf-8;");
		CsvExport csvout=new CsvExport(InitString.Book_importhead, filedData);
		csvout.createCsv(out);
		System.setOut(new PrintStream(out));
		out.flush();
		//关闭输出流
		if(out!=null){
			out.close();
		}
		
	}
	/**
	 * 导入书籍的excel信息
	 * @return
	 * @throws Exception
	 */
	public String importbook() throws Exception{
		File excel=importBookForm.getFile();
		if(excel==null){
			return "error";
		};

		//保存excel的信息
		ArrayList<String> errline=new ArrayList<String>();
		String result=service.savebookinfor(excel,errline);		
		if(result.equals("firstnotmatch")){
			getRequest().setAttribute("errormsg", "第一行格式不匹配");
			return "error";
		}
		if(errline.size()!=0){
			HttpServletResponse response=getResponse();
			OutputStream out = response.getOutputStream();
			//重置输出流
			response.reset();
			response.setHeader("Content-disposition", "attachment;filename=\""+ new String(("图书导入失败.csv").getBytes("GBK"),"ISO-8859-1") + "\"");    
			//设置导出Excel报表的导出形式
			response.setContentType("text/plain; charset=utf-8");
			CsvExport csvout=new CsvExport(InitString.Book_importhead, errline);
			csvout.createCsv(out);
			System.setOut(new PrintStream(out));
			out.flush();
			//关闭输出流
			if(out!=null){
				out.close();
			}			
		}
		//将保存的信息进行显示
//		List<AllBookInfoForm> booklist=service.findbookinfolist(getRequest());
//		getRequest().setAttribute("bookList", booklist);		
		return "success";
	}
	public String importbooktype() throws Exception{
		File excel=importBookForm.getFile();
		//保存excel的信息
		String result=service.savebooktypeinfor(excel);
		//将保存的信息进行显示		
		return "success";
	}
	@Override
	public ImportBookForm getModel() {
		
		return importBookForm;
	}
	
	/**
	 * 根据查询条件导出书籍信息的csv
	 * @return
	 * @throws Exception
	 */
	public void Excelexport() throws Exception{
		

		ArrayList<String> filedData = service.getExcelFiledDataList(importBookForm,getRequest());	
		if(filedData==null){
			filedData=new ArrayList<String>();
		}
		HttpServletResponse response=getResponse();
		OutputStream out = response.getOutputStream();
		//重置输出流
		response.reset();
		response.setHeader("Content-disposition", "attachment;filename=\""+ new String(("书籍查询信息.csv").getBytes("GBK"),"ISO-8859-1") + "\"");    

		//设置导出Excel报表的导出形式
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		//设置导出Excel报表的导出形式
		response.setContentType("text/plain;charset=utf-8");
		CsvExport csvout=new CsvExport(InitString.Book_exporthead, filedData);
		csvout.createCsv(out);
		out.flush();
		//关闭输出流
		if(out!=null){
			out.close();
		}
		
	}
	/**
	 * 显示导入的书籍信息
	 * @return
	 * @throws Exception
	 */
	public void getallbookinfo() throws Exception{
		//有条件进行查询
		JSONObject booklist1=service.getbookinfoBybookname(importBookForm,getRequest());		
		returnObject(booklist1);
	}
	
	public String showbookinfo() {
		List<AllBookInfoForm> booklist=service.findbookinfolist(getRequest());
		getRequest().setAttribute("bookList", booklist);		
		
		return "success";
	}

	
	public String borrowbook() {
	//借阅书籍
	String isbn10=importBookForm.getIsbn10();
	
	
	
	return "success";
}
	
	/**
	 * 个人获取书籍信息
	 * @return
	 */
	public String persongetbookinfo() {
		List<AllBookInfoForm> booklist=null;
		if(importBookForm.getBookName()==null){
			booklist=null;
		}else{
//			booklist=service.searchBybookname(importBookForm,getRequest());
		}
		getRequest().setAttribute("bookList", booklist);	
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		return "success";
	}
	
	
	/**
	 * 更新书籍的信息
	 * @return
	 */
	public String getbookinfo(){
//		String result=service.getbookinfo("s");
		
		return "success";
	}

	public ImportBookService getService() {
		return service;
	}

	public void setService(ImportBookService service) {
		this.service = service;
	}
	/**
	 * 查询要借阅的书籍信息
	 * @return
	 */
	public String  pesrsonborrowbook() {
		String isbn=importBookForm.getIsbn10();
		
		return "";
	}
	/**
	 * 确认借阅书籍
	 * @return
	 */
	public String  clickborrowbook() {
		//借阅书籍
		String onlysign=importBookForm.getOnlysign();
		//判断这个onlysign是否存在，判断onlysign是否已经借阅
		String result=service.getbookinfoByonlysign(onlysign);
		if(result.equals("has_borrow")){
			return "hasborrow";
		}
		if(!result.equals("success")){
			
			return "error";
		}
		//onlysign存在则将书籍的信息存储至图书借阅信息表中
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		if(user==null||String.valueOf(user.getId())==""){
			return "login";
		}
//		user.getNumber()
		String userid=String.valueOf(user.getId());
		result=service.saveborrowbookinfo(userid,onlysign,"1");
		
		return "success";
		
	
	}
	
	

}

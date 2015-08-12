package com.mini.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.SessionMap;

import net.sf.json.JSONObject;

import com.mini.entity.User;
import com.mini.form.PhysicalexamForm;
import com.mini.service.PhysicalexamService;
import com.mini.service.SmsService;
import com.mini.util.CsvExport;
import com.mini.util.ExcelFileGenerator;
import com.mini.util.InitString;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;


/**
 * 体检相关的Action
 * 
 */
@SuppressWarnings("serial")
public class PhysicalexamAction extends BaseAction implements ModelDriven<PhysicalexamForm> {
	private PhysicalexamForm physicalexamForm=new PhysicalexamForm();
	private PhysicalexamService service;
	
	
	public PhysicalexamForm getPhysicalexamForm() {
		return physicalexamForm;
	}
	public void setPhysicalexamForm(PhysicalexamForm physicalexamForm) {
		this.physicalexamForm = physicalexamForm;
	}
	
	@Override
	public PhysicalexamForm getModel() {
		return this.physicalexamForm;
	}
	public PhysicalexamService getService() {
		return service;
	}
	public void setService(PhysicalexamService service) {
		this.service = service;
	}
	/**
	 * 体检信息查询
	 * @throws ParseException 
	 */
	public void phsicalexamsearch() throws ParseException{
		JSONObject result=service.searchphysicalexam(physicalexamForm);
		returnObject(result);
	}
	public String physical(){
		return "success";
	}
	/**
	 * 导入体检信息
	 * @throws Exception 
	 * @throws FileNotFoundException 
	 */
	public String importphysicalexam() throws FileNotFoundException, Exception{
		File excel=physicalexamForm.getFile();
		if(excel==null){
		};
		//保存excel的信息
		ArrayList<String> errline=new ArrayList<String>();
		String result=service.savephysicalexam(excel,physicalexamForm,errline);		
		if(result.equals("firstnotmatch")){
			getRequest().setAttribute("errormsg", "第一行格式不匹配");
			return "error";
		}
		if(errline.size()!=0){
			HttpServletResponse response=getResponse();
			OutputStream out = response.getOutputStream();
			//重置输出流
			response.reset();
			response.setHeader("Content-disposition", "attachment;filename=\""+ new String(("体检信息导入失败信息.csv").getBytes("GBK"),"ISO-8859-1") + "\"");    
			//设置导出Excel报表的导出形式
			response.setContentType("text/plain; charset=utf-8");
			CsvExport csvout=new CsvExport(InitString.PHYSICAL_information_head, errline);

			csvout.createCsv(out);
			System.setOut(new PrintStream(out));
			out.flush();
			//关闭输出流
			if(out!=null){
				out.close();
			}			
		}else{
			
		}
		return "success";
		
	}
	/**
	 * 获取体检批次
	 * @return
	 */
	public void getphysicalplan(){		
		JSONObject result=service.getphysicalplan();
		returnObject(result);
	}
	/**
	 * 修改预约体检时间
	 * @return
	 */
	public void savephysicalreservedate(){		
		String result=service.savephysicalreservedate(physicalexamForm);
		if(result.equals("success")){
			returnObject(result);
		}
		
	}
	/**
	 * 删除用户体检信息
	 * @return
	 */
	public void deletephysicalreservedate(){		
		String result=service.deletephysicalreservedate(physicalexamForm);
		if(result.equals("success")){
			returnObject(result);
		}
		
	}
	/**
	 * 医院输入工号，返回员工姓名，体检批次、工号
	 * @throws ParseException 
	 */
	public void getUsernameByusernumber() throws ParseException{
		JSONObject result=service.getUsernameByusernumber(physicalexamForm);
		if(result==null){
			resultError("100", "用户不存在");
		}else{
			returnObject(result);
		}
	}
	/**
	 * 医院输入工号，返回员工姓名，体检批次、工号
	 * @throws Exception 
	 */
	public void getUsernameByusernumberlike() throws Exception{
		JSONObject result=service.getUsernameByusernumberlike(physicalexamForm);
		if(result==null){
			resultError("100", "用户不存在");
		}else{
			returnObject(result);
		}
	}
	/**
	 * 修改用户的正式体检时间
	 */
	public void savePhysicaldate(){
		String result=service.savePhysicaldate(physicalexamForm);
		if(result.equals("success")){
			returnObject("保存成功");
		}else{
			returnObject(result);
		}
	}
	/**
	 * 修改用户的正式体检时间
	 */
	public String savePhysicaldateByimport(){		
		
		File excel=physicalexamForm.getFile();
		ArrayList<String> errline=new ArrayList<String>();
		if(!physicalexamForm.getFileContentType().matches("application/vnd.ms-excel")){
			getRequest().setAttribute("errormsg", "请采用正确的excel格式");
		}
		String result;
		try {
			result = service.savePhysicaldateByimport(physicalexamForm,errline);
			if(result.equals("firstnotmatch")){
				getRequest().setAttribute("errormsg", "第一行格式不匹配");
				return "error";
			}
			
			return "success";
			
		} catch (Exception e) {
		
			getRequest().setAttribute("errormsg", "excel表中数据有错误");
			return "error";
		}
		
	}
	/**
	 * 修改用户的复查项
	 */
	public void savePhysicalreview(){
		String result=service.savePhysicalreview(physicalexamForm);
		if(result.equals("success")){
			returnObject("保存成功");
		}else{
			returnObject(result);
		}
	}
	public void savePhysicalreviewBymanager(){
		String result=service.savePhysicalreviewBymanager(physicalexamForm);
		if(result.equals("success")){
			returnObject("保存成功");
		}else{
			returnObject(result);
		}
	}
	/**
	 * 体检复查信息查询
	 */
	public void phsicalreviewsearch(){
		JSONObject result=service.searchphysicalreview(physicalexamForm);
		returnObject(result);
	}
	/**
	 * 删除体检信息复查项
	 */
	public void deletephsicalreview(){
		String result=service.deletephsicalreview(physicalexamForm);
		if(result.equals("success")){
			returnObject(result);
		}
	}
	/**
	 * 保存体检的初始化信息
	 */
	public void savephsicalinit(){
		String result=service.savephsicalinit(physicalexamForm);
		if(result.equals("success")){
			returnObject(result);
		}
	}
	/**
	 * 查询体检的初始化信息
	 */
	public void searchphsicalinit(){
		JSONObject result=service.searchphsicalinit(physicalexamForm);
		returnObject(result);
	}
	/**
	 * 保存管理员的修改体检时间要求
	 */
	public void saveeditphysicaldateinit(){
		String result=service.saveeditphysicaldateinit(physicalexamForm);
		if(result.equals("success")){
			returnObject(result);
		}
	}

	/**
	 * 获取管理员的修改体检时间要求
	 */
	public void geteditphysicalinit(){
		JSONObject result=service.geteditphysicalinit(physicalexamForm);
		returnObject(result);
	}
	/**
	 * 获取用户的体检信息
	 * @throws ParseException 
	 */
	public void getuserphsicalinfo() throws ParseException{
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		
		JSONObject result=service.getuserphsicalinfo(physicalexamForm,user);
		if(result==null){
			returnObject("noImformation");
		}else{
//			returnObject(result);
			writeJson(result);
		}
		
	}
	/**
	 * 获取用户的家属列表
	 */
	public void getuserphsicalrelatives(){
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		JSONObject result=service.getuserphsicalrelatives(physicalexamForm,user);
		returnObject(result);
	}
	public void getuserphsicalrelativesOnback(){
		
		JSONObject result=null;
		try {
			result = service.getuserphsicalrelativesOnback(physicalexamForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnObject(result);
	}
	/**
	 * 保存用户的家属列表
	 */
	public void saveuserphsicalrelatives(){
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		String result=service.saveuserphsicalrelatives(physicalexamForm,user);
		if(result.equals("success")){
			returnObject(result);
		}
	}
	/**
	 * 删除用户的家属列表
	 */
	public void deleteuserphsicalrelatives(){
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		String result=service.deleteuserphsicalrelatives(physicalexamForm,user);
		if(result.equals("success")){
			returnObject(result);
		}
	}
//	
	/**
	 * 体检信息导入模板下载
	 * @return
	 * @throws Exception
	 */
	public void downloadphysicaltemplates() throws Exception{			
		ArrayList<String> filedData =service.getExcelFiledDataListnull(physicalexamForm,getRequest());
		HttpServletResponse response=getResponse();
		OutputStream out = response.getOutputStream();
		//重置输出流
		response.reset();
		response.setHeader("Content-disposition", "attachment;filename=\""+ new String(("体检信息导入模版.csv").getBytes("GBK"),"ISO-8859-1") + "\"");   
		response.setContentType("text/plain; charset=utf-8;");
		CsvExport csvout=new CsvExport(InitString.PHYSICAL_information_head, filedData);
		//out.write(new byte[]{(byte)0xEF, (byte)0xBB, (byte)0xBF});
		csvout.createCsv(out);
		System.setOut(new PrintStream(out));
		out.flush();
		//关闭输出流
		if(out!=null){
			out.close();
		}
		
	}
	/**
	 * 批量导入体检时间的模版
	 * @return
	 * @throws Exception
	 */
	public void downloadphysicaldatetemplates() throws Exception{				
		ArrayList<String> filedData =service.getExcelFiledDataListdate(physicalexamForm,getRequest());
		HttpServletResponse response=getResponse();
		OutputStream out = response.getOutputStream();
		//重置输出流
		response.reset();
		response.setHeader("Content-disposition", "attachment;filename=\""+ new String(("体检完成导入模版.csv").getBytes("GBK"),"ISO-8859-1") + "\"");    
		response.setContentType("text/plain;charset=UTF-8");
		CsvExport csvout=new CsvExport(InitString.PHYSICAL_Hospital_import, filedData);

		csvout.createCsv(out);
		System.setOut(new PrintStream(out));
		out.flush();
		//关闭输出流
		if(out!=null){
			out.close();
		}
		
	}
	/**
	 * 体检信息导出为excel模式
	 * @return
	 * @throws Exception
	 */
	public void exportphysicalinfo() throws Exception{
		String titles="工号(必须为8位，不足8为在前面补0),姓名,体检地点,安排体检时间,实际体检日期,体检状态,体检批次,是否有家属体检";	
		ArrayList<String> filedData =service.getExcelFiledDataList(physicalexamForm,getRequest());
		if(filedData==null){
			filedData=new ArrayList<String>();
		}
		HttpServletResponse response=getResponse();
		OutputStream out = response.getOutputStream();
		//重置输出流
		response.reset();
		response.setHeader("Content-disposition", "attachment;filename=\""+ new String(("体检信息.csv").getBytes("GBK"),"ISO-8859-1") + "\"");    

		//设置导出Excel报表的导出形式
		response.setContentType("text/plain;charset=utf-8");
		CsvExport csvout=new CsvExport(titles, filedData);

		csvout.createCsv(out);
		 
		System.setOut(new PrintStream(out));
		out.flush();
		//关闭输出流
		if(out!=null){
			out.close();
		}
		
	}
	/**
	 * 家属体检信息导出
	 * @return
	 * @throws Exception
	 */
	public void exportphysicalrelativesinfo() throws Exception{
		String titles="工号,员工姓名,体检批次,家属姓名,身份证号,电话,关系";	
		
		ArrayList<String> filedData =service.getExcelFiledDataListrelatives(physicalexamForm,getRequest());

		if(filedData==null){
			filedData=new ArrayList<String>();
		}
		HttpServletResponse response=getResponse();
		OutputStream out = response.getOutputStream();
		//重置输出流
		response.reset();
		response.setHeader("Content-disposition", "attachment;filename=\""+ new String(("家属信息.csv").getBytes("GBK"),"ISO-8859-1") + "\"");    

		//设置导出Excel报表的导出形式
		response.setContentType("text/plain;charset=UTF-8");
		CsvExport csvout=new CsvExport(titles, filedData);

		csvout.createCsv(out);

		System.setOut(new PrintStream(out));
		out.flush();
		//关闭输出流
		if(out!=null){
			out.close();
		}
		
	}
	/**
	 * 复查信息导出
	 * @return
	 * @throws Exception
	 */
	public void exportphysicalreviewinfo() throws Exception{
		String titles="工号,员工姓名,体检批次,复查内容";		
		ArrayList<String> filedData =service.getExcelFiledDataListreview(physicalexamForm,getRequest());
		if(filedData==null){
			filedData=new ArrayList<String>();
		}
		HttpServletResponse response=getResponse();
		OutputStream out = response.getOutputStream();
		//重置输出流
		response.reset();
		response.setHeader("Content-disposition", "attachment;filename=\""+ new String(("复查信息.csv").getBytes("GBK"),"ISO-8859-1") + "\"");    
		//设置导出Excel报表的导出形式
		response.setContentType("text/plain;charset=UTF-8");
		CsvExport csvout=new CsvExport(titles, filedData);

		csvout.createCsv(out);
		System.setOut(new PrintStream(out));
		out.flush();
		//关闭输出流
		if(out!=null){
			out.close();
		}
		
	}
	/**
	 * 申请修改体检时间
	 * @throws Exception
	 */
	public void applyExpectphysicalDate() {
		String result;
		try {
			result = service.applyExpectphysicalDate(physicalexamForm);			
			returnObject(result);			
		} catch (Exception e) {
						
			resultError("102", "error");
		}
		
		
	}
	/**
	 * 申请体检信息查询
	 * @throws ParseException 
	 */
	public void applyExpectphysicalDatesearch() throws ParseException{
		JSONObject result=service.applyExpectphysicalDatesearch(physicalexamForm);
		returnObject(result);
	}
	/**
	 * 对用户体检的申请时间进行是与否进行判断
	 * @throws ParseException 
	 */
	public void applyPhysicalDate() throws ParseException{
		String result=service.applyPhysicalDate(physicalexamForm);
		if(result.equals("success")){
			returnObject("success");
		}else{
			resultError("104", "error");
		}
		
	}
	
}

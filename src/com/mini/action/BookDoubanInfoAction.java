package com.mini.action;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.dispatcher.SessionMap;

import com.mini.entity.BookBorrowinfo;
import com.mini.entity.Bookinfo;
import com.mini.entity.ImportBookInfo;
import com.mini.entity.User;
import com.mini.form.BookBorrowinfoForm;
import com.mini.form.BookInfoForm;
import com.mini.form.BookSystemDDlForm;
import com.mini.form.ImportBookForm;
import com.mini.service.BookDoubanInforService;
import com.mini.service.ImportBookService;
import com.mini.util.ExcelFileGenerator;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class BookDoubanInfoAction extends BaseAction implements ModelDriven<BookInfoForm>{

	private BookInfoForm bookinfoform=new BookInfoForm();
	private ImportBookService service;
	private BookDoubanInforService doubanservice;

	public BookDoubanInforService getDoubanservice() {
		return doubanservice;
	}
	public void setDoubanservice(BookDoubanInforService doubanservice) {
		this.doubanservice = doubanservice;
	}
	public ImportBookService getService() {
		return service;
	}
	public void setService(ImportBookService service) {
		this.service = service;
	}
	@Override
	public BookInfoForm getModel() {
		// TODO Auto-generated method stub
		return bookinfoform;
	}
	public String test() throws Exception{
		
		return "success";
	}
	/**
	 * 获取书籍的豆瓣信息进行显示
	 * @return
	 * @throws Exception 
	 */
	public String getdoubaninfor() throws Exception{
		String isbn=bookinfoform.getIsbn10();
//		String onlysign=getRequest().getParameter("onlysign");
		Bookinfo bookdoubaninfo=service.finddoubanbookinfo(isbn).get(0);
		if(bookdoubaninfo.getSummary().length()>100){
			bookdoubaninfo.setSummary(bookdoubaninfo.getSummary().substring(0, 99)+"...");
		}else{
			bookdoubaninfo.setSummary(bookdoubaninfo.getSummary());
		}
		getRequest().setAttribute("bookList", bookdoubaninfo);
		getRequest().setAttribute("message", "已下载");
		//根据isbn查询onlysign的值
		List<String> signList=service.findOnlysignByisbn(isbn);
		getRequest().setAttribute("signList", signList);
		
		return "success";
	}
	public String borrowbookByisbn() throws Exception{
		String isbn=bookinfoform.getIsbn10();
		//根据isbn查询onlysign的值
		List<String> signList=service.findOnlysignByisbn(isbn);
		getRequest().setAttribute("signList", signList);
		return "success";
	}
	/**
	 * 根据isbn下载书籍的信息
	 * @return
	 * @throws Exception 
	 */
	public String downloadbookinfo() throws Exception{
		String isbn=bookinfoform.getIsbn10();
		String result=service.savedoubanbookinfo(isbn);	
		if(result.equals("exit")){
			return "exit";
		}
		if(result.equals("nobook")){
			return "nobook";
		}	
		Bookinfo bookdoubaninfo=service.finddoubanbookinfo(isbn).get(0);
		getRequest().setAttribute("bookList", bookdoubaninfo);
		getRequest().setAttribute("message", "已下载");
		//根据isbn查询onlysign的值
		List<String> signList=service.findOnlysignByisbn(isbn);
		getRequest().setAttribute("signList", signList);
		writeJson(signList);
		return "success";
	}
	/**
	 * 跳转到书籍信息编辑
	 * @return
	 * @throws Exception
	 */
	public void getoneimportbookinfo() throws Exception{
		
		String onlysign=bookinfoform.getOnlysign();
		List<ImportBookForm> list=service.findimportbookinfolistByonlysign(onlysign);	
		ImportBookForm importBookForm=list.get(0);
		
		List<BookSystemDDlForm> formList = service.BookSystemDDlPOListToVOList("所在位置");
		List<BookSystemDDlForm> classifiList = service.BookSystemDDlPOListToVOList("书籍分类");
		JSONObject result = new JSONObject();
		result.put("bookform", importBookForm);
		result.put("positionList", formList);
		result.put("classifiList", classifiList);
		returnObject(result);
//		getRequest().setAttribute("bookInfoForm", list.get(0));
//		ActionContext.getContext().getValueStack().push(importBookForm);
//		this.initSystemDDl();
//		return "success";
	}
	/**
	 * 初始化数据字典的数组
	 */
	private void initSystemDDl() {
	
		List<BookSystemDDlForm> formList = service.BookSystemDDlPOListToVOList("所在位置");
		getRequest().setAttribute("positionList", formList);
		List<BookSystemDDlForm> classifiList = service.BookSystemDDlPOListToVOList("书籍分类");
		getRequest().setAttribute("classifiList", classifiList);
		
	}
	/**
	 * 修改书籍的导入信息并保存
	 * @return
	 * @throws Exception
	 */
	public String saveimportbookinfo() throws Exception{		
		String result=service.updateimportbookinfo(bookinfoform,getRequest());	
	
//		returnObject("保存成功");
		return "success";
	}
	/**
	 * 还书
	 * @return
	 * @throws Exception
	 */
	public String backbook() throws Exception{		
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		//获取当前user所借的书籍
		List<BookBorrowinfoForm>  backboolist=service.getbackbookByuser(String.valueOf(user.getId()));
		getRequest().setAttribute("backbookList", backboolist);
		return "success";
	}
	/**
	 * 进入还书界面，返回还书成功信息，要更新书籍所在位置的信息
	 * @return
	 * @throws Exception
	 */
	public String clickbackbook() throws Exception{		
		String onlysign=bookinfoform.getOnlysign();
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		if(user==null||String.valueOf(user.getId())==""){
			return "login";
		}
		//获取地点对应的infolab
		String infolable=bookinfoform.getInfolable();
		if(infolable==null){
			 getRequest().setAttribute("errormsg", "请选择还书地点");
			return "chooseplace";
		}
		//获取当前user所借的书籍
		String result=service.updatebackbook(onlysign,String.valueOf(user.getId()),infolable);
		
		List<ImportBookInfo> list=service.getJudgeByonlysign(onlysign);
		getRequest().setAttribute("bookname", list.get(0).getName());
		if(result.equals("notborrow")){
			return "notborrow";
		}
		//返回一些还书成功的信息
		//输出书籍的名字，还书时间，图书捐赠人
		   SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		   //获取当前时间
		   Calendar rightNow = Calendar.getInstance();		 
		   getRequest().setAttribute("bookname", list.get(0).getName());
		   Date dt1=rightNow.getTime();
		   String reStr = sdf.format(dt1);
		   getRequest().setAttribute("backdate", reStr);
		  //图书捐赠人
		  getRequest().setAttribute("donor", list.get(0).getSource());
		  List<Bookinfo> doubanlist=service.finddoubanbookinfo(list.get(0).getIsbn10());
		  getRequest().setAttribute("image", doubanlist.get(0).getImage());	  
		return "success";
	}
	/**
	 * 进行借书
	 * @return
	 * @throws Exception
	 */
	public String scanbook() throws Exception{	
		String onlysign=bookinfoform.getOnlysign();
		//获取地点对应的infolab
		String position=bookinfoform.getInfolable();
		if(position==null){
			getRequest().setAttribute("errormsg", "请选择地点");
			return "error";
		}
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		if(user==null||String.valueOf(user.getId())==""){
			return "login";
		}
		//查找 onlysign对应的书籍   ,首先要判断书籍是否存在
		List<ImportBookInfo> list=service.getJudgeByonlysign(onlysign);

		if(list==null||list.size()==0){
			getRequest().setAttribute("errormsg", "书籍不存在");
			return "error";
		}
		String result=service.getJudgeborrowByonlysign(onlysign);
		if(result.equals("error")){
			getRequest().setAttribute("errormsg","该书籍处于被借阅状态\n请退出借阅其他书籍");
			return "error";
		}
		ImportBookInfo importBookInfo=list.get(0);
		//判断选择地点是否正确
		if(!importBookInfo.getPosition().equals(position)){
			getRequest().setAttribute("errormsg", "请选择正确的借书地点");
			return "error";
		}
		//查询已经借阅书籍的数量
		int allnumber=service.getborrownumberBy(user.getId());
		if(allnumber>2){
			getRequest().setAttribute("errormsg", "最多只能借阅三本书籍");
			return "error";
		}
		//书籍已经存在
		List<BookBorrowinfo>  backboolist=service.getbackbookByonlysign(onlysign);
		if(backboolist.size()==0){
			//说明要进行借阅
			service.saveborrowbookinfo(String.valueOf(user.getId()),onlysign,position);
			getRequest().setAttribute("bookname", list.get(0).getName());
			//输出书籍的名字，还书时间，图书捐赠人
		   SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		   Calendar rightNow = Calendar.getInstance();
		   rightNow.add(Calendar.MONTH,2);//日期加2个月
		   getRequest().setAttribute("bookname", list.get(0).getName());
		   Date dt1=rightNow.getTime();
		   String reStr = sdf.format(dt1);
		   getRequest().setAttribute("backdate", reStr);
		  //图书捐赠人
		  getRequest().setAttribute("donor", list.get(0).getSource());
		  List<Bookinfo> doubanlist=service.finddoubanbookinfo(list.get(0).getIsbn10());
		  getRequest().setAttribute("image", doubanlist.get(0).getImage());	  
			return "borrowsuccess";
			
		}
		getRequest().setAttribute("bookname", list.get(0).getName());
		getRequest().setAttribute("errormsg","书籍已经借阅\n请退出借阅其他书籍");
		return "hasborrow";

		
	}
	/**
	 * 扫描书籍的二维码
	 * @return
	 * @throws Exception
	 */
	public String scan() throws Exception{	
		String onlysign=bookinfoform.getOnlysign();
		//查找书籍的信息
//		String onlysign=getRequest().getParameter("onlysign");
		BookInfoForm bookdoubaninfo=service.finddoubanbookinfoByonlysign(onlysign);
		getRequest().setAttribute("bookList", bookdoubaninfo);

		return "success";
	}
	/**
	 * 删除书籍的信息,并修改书籍的数量,只需要将book_import_info中的state变为2，代表已删除，改变book_number中的数量
	 * @return
	 * @throws Exception
	 */
	public void deletebooknumber() throws Exception{	
		
		String onlysign=bookinfoform.getOnlysign();
		String isbn=bookinfoform.getIsbn10();
		//查找书籍的信息
//		String onlysign=getRequest().getParameter("onlysign");
		String result=service.deletebooknumber(onlysign,isbn);
		returnObject(result);
		
	}
	/**
	 * 下载全部书籍的豆瓣信息
	 * @return
	 * @throws Exception
	 */
//	public String downallbookinfo() throws Exception{	
	public void downallbookinfo() throws Exception{	
		List<BookInfoForm> booklist=service.savedownallbookinfo();
		if(booklist.size()!=0){
			getRequest().setAttribute("bookList",booklist);	
			returnObject("未完全下载");
//			return "fail";
		}
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		getRequest().setAttribute("resultjsp", "downallsuccess");	
		returnObject("全部下载");
//		return "success";
	}
	/**
	 * 导入excel
	 * @return
	 * @throws Exception
	 */
	public String excelAdd() throws Exception{
	
		return "success";
	}

	
	
	
	
	

}

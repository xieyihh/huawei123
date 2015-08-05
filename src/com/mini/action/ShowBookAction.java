package com.mini.action;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.SessionMap;

import com.mini.entity.Bookinfo;
import com.mini.entity.ImportBookInfo;
import com.mini.entity.User;
import com.mini.form.AllBookInfoForm;
import com.mini.form.BookInfoForm;
import com.mini.form.BookRankingForm;
import com.mini.form.BookRemainForm;
import com.mini.form.BooklastInforForm;
import com.mini.form.ImportBookForm;
import com.mini.form.TwoBookInfoForm;
import com.mini.service.BookBackstageService;
import com.mini.service.BookDoubanInforService;
import com.mini.service.ImportBookService;
import com.mini.service.PhoneBookService;
import com.mini.util.ExcelFileGenerator;
import com.mini.util.PageInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class ShowBookAction extends BaseAction implements ModelDriven<TwoBookInfoForm>{

	private TwoBookInfoForm twoBookInfoForm=new TwoBookInfoForm();
	private ImportBookService service;
	private BookDoubanInforService doubanservice;
	private BookBackstageService bookservice;
	private final String book_position="所在位置";
	private final String book_classfi="书籍分类";
	
	private PhoneBookService phoneService;
	
	public PhoneBookService getPhoneService() {
		return phoneService;
	}
	public void setPhoneService(PhoneBookService phoneService) {
		this.phoneService = phoneService;
	}

/**
	 * 手机页面的首页显示
	 * @return
	 */
	public void  phonebooknice(){
		JSONObject booklist=phoneService.searchphonebooknice();
		returnObject(booklist);

	}
	public String  bookhome(){
		return "success";

	}
	/**
	 * 手机页面的查询显示
	 * @return
	 */
	public void  phonebooksearch(){
	
	JSONObject result=phoneService.searchbookOnphone(twoBookInfoForm,getRequest());
	returnObject(result);
		
	
	}

	
	public BookBackstageService getBookservice() {
		return bookservice;
	}
	public void setBookservice(BookBackstageService bookservice) {
		this.bookservice = bookservice;
	}
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
	public TwoBookInfoForm getModel() {
		// TODO Auto-generated method stub
		return twoBookInfoForm;
	}
	public String showbookinfoindex(){
//		System.out.println("YES");
		List<TwoBookInfoForm> booklist=null;
		if(twoBookInfoForm.getBookname()==null||twoBookInfoForm.getBookname().equals("")){
			booklist=service.personsearchBybookname(getRequest());
		}else{
			booklist=service.personsearchBybookname(twoBookInfoForm.getBookname(),getRequest());
		}
		getRequest().setAttribute("bookList", booklist);
		getRequest().setAttribute("bookname", twoBookInfoForm.getBookname());
		String initflag=getRequest().getParameter("initflag");
		if(initflag!=null && initflag.equals("1")){
			return "list";			
		}

		return "success";
	}
	/**
	 * 进入显示查询的首届面,显示推荐的书籍和查询按钮
	 * @return
	 */
	public String showbookinfohome(){
			
		List<BookInfoForm> booklist=service.searchbooknice();
		getRequest().setAttribute("bookList", booklist);

		return "success";
	}
	public String showbookinfolist(){

		if(twoBookInfoForm.getBookname()==null||twoBookInfoForm.getBookname().equals("")||twoBookInfoForm.getBookname().equals("查询书籍")){
//			List<BookInfoForm>  booklist=service.searchbooknice(getRequest());
//			getRequest().setAttribute("bookList", booklist);
			List<BookInfoForm> booklist=service.searchbooknice();
			getRequest().setAttribute("bookList", booklist);
		}else{
//			booklist=service.searchbooknice(twoBookInfoForm.getBookname(),getRequest());
			List<BookInfoForm> booklist=service.searchbooknice(twoBookInfoForm.getBookname(),getRequest());
			getRequest().setAttribute("bookList", booklist);
		}	
		

		return "success";
	}
	public String bookclassifihome(){


		return "success";
	}
	public String bookclassifilist(){


		return "success";
	}
	/**
	 * 跳转到查询书籍在某处剩余信息页面，主要包括书店的主要名称，书的图片，书的作者，书籍所在位置的剩余信息，根据书籍的isbn来进行获取,因为传进来的值是onlysign,要首先获取isbn10
	 * @return
	 */
	public String bookinformation(){
		List<BooklastInforForm> booklist=new ArrayList<BooklastInforForm>();
		BooklastInforForm booklastInforForm=null;
		String lalbvalue=twoBookInfoForm.getInfolable();
		//获取要查询 的书籍的Isbn10
		String 	isbn10=twoBookInfoForm.getIsbn10();
		//获取书籍的豆瓣信息
		List<Bookinfo> doubanlist=service.finddoubanbookinfo(isbn10);
		if(doubanlist==null||doubanlist.size()==0){
			return "error";
			
		}
		getRequest().setAttribute("bookname",doubanlist.get(0).getName());
		getRequest().setAttribute("author",doubanlist.get(0).getAuto());
		getRequest().setAttribute("bookimage",doubanlist.get(0).getImage());
		getRequest().setAttribute("booksummary",doubanlist.get(0).getSummary());
		
		//获取书籍所在位置的剩余信息
		//根据isbn查询剩余数量
		List<BookRemainForm> userlist=service.findbookremainmountByisbn(isbn10);	
		//进行查询，若没有的地方则设置为0本
		String[] ddlname_position=service.findddlname(book_position);
		//若都没有
		if(userlist==null||userlist.size()==0){
			for(int i=0;i<ddlname_position.length;i++){
				booklastInforForm=new BooklastInforForm();	
				booklastInforForm.setInfor(ddlname_position[i]+":0 本");
				booklastInforForm.setLablevalue(String.valueOf(i+1));
				booklist.add(booklastInforForm);
			}
			getRequest().setAttribute("bookList", booklist);
			return "success";
		}
		//说明有的地方没有
		String infor=null;
		if(userlist.size()<ddlname_position.length){
			
			for(int i=0;i<ddlname_position.length;i++){
				//对得到的进行检查,没有书籍的变为0本
				for(int j=0;j<userlist.size();j++){
					BookRemainForm bookRemainForm=userlist.get(j);
					booklastInforForm=new BooklastInforForm();	
					if(ddlname_position[i].equals(bookRemainForm.getPosition())){
						infor=bookRemainForm.getPosition()+":"+bookRemainForm.getNumber()+"本";
						booklastInforForm.setInfor(infor);
						booklastInforForm.setLablevalue(bookRemainForm.getDdlCode());
						booklist.add(booklastInforForm);
						break;
					}
					//说明不存在书籍为0本
					if(j==(userlist.size()-1)){
						booklastInforForm.setInfor(ddlname_position[i]+":0 本");
						booklastInforForm.setLablevalue(String.valueOf(i+1));
						booklist.add(booklastInforForm);
					}
										
				}			
				
				
			}
			getRequest().setAttribute("bookList", booklist);
			return "success";
		}
		for(int i=0;i<userlist.size();i++){
			booklastInforForm=new BooklastInforForm();		
			BookRemainForm bookRemainForm=userlist.get(i);
			infor=bookRemainForm.getPosition()+":"+bookRemainForm.getNumber()+"本";
			booklastInforForm.setInfor(infor);
			booklastInforForm.setLablevalue(bookRemainForm.getDdlCode());
			booklist.add(booklastInforForm);			
		}
		getRequest().setAttribute("bookList", booklist);
		return "success";
	}
	/**
	 * 跳转到查询书籍在某处剩余信息页面，主要包括书店的主要名称，书的图片，书的作者，书籍所在位置的剩余信息，根据书籍的isbn来进行获取,因为传进来的值是onlysign,要首先获取isbn10
	 * @return
	 */
	public String scan(){
		List<BooklastInforForm> booklist=new ArrayList<BooklastInforForm>();
		BooklastInforForm booklastInforForm=null;
		//获取要查询 的书籍的Isbn10
//		String 	isbn10=twoBookInfoForm.getIsbn10();
		String onlysign=twoBookInfoForm.getOnlysign();
		List<ImportBookInfo> list=service.getJudgeByonlysign(onlysign);
		if(list.size()==0){
			
		}
		String 	isbn10=list.get(0).getIsbn10();
		//获取书籍的豆瓣信息
		List<Bookinfo> doubanlist=service.finddoubanbookinfo(isbn10);
		if(doubanlist==null||doubanlist.size()==0){
			getRequest().setAttribute("errormsg","没有发现豆瓣信息");
			return "error";			
		}
		getRequest().setAttribute("bookname",list.get(0).getName());
		getRequest().setAttribute("author",doubanlist.get(0).getAuto());
		getRequest().setAttribute("bookimage",doubanlist.get(0).getImage());
		getRequest().setAttribute("booksummary",doubanlist.get(0).getSummary());
		getRequest().setAttribute("onlysign",onlysign);
		//获取书籍所在位置的剩余信息
		//根据isbn查询剩余数量
		List<BookRemainForm> userlist=service.findbookremainmountByisbn(isbn10);	
		//进行查询，若没有的地方则设置为0本
		String[] ddlname_position=service.findddlname(book_position);
		//若都没有
		if(userlist==null||userlist.size()==0){
			for(int i=0;i<ddlname_position.length;i++){
				booklastInforForm=new BooklastInforForm();	
				booklastInforForm.setInfor(ddlname_position[i]+":0 本");
				booklastInforForm.setLablevalue(String.valueOf(i+1));
				booklastInforForm.setRadioname("radio"+String.valueOf(i+1));
				booklist.add(booklastInforForm);
			}
			getRequest().setAttribute("bookList", booklist);
			return "success";
		}
		//说明有的地方没有
		String infor=null;
		if(userlist.size()<ddlname_position.length){			
			for(int i=0;i<ddlname_position.length;i++){
				//对得到的进行检查,没有书籍的变为0本
				
				for(int j=0;j<userlist.size();j++){
					BookRemainForm bookRemainForm=userlist.get(j);
					booklastInforForm=new BooklastInforForm();	
					if(ddlname_position[i].trim().equals(bookRemainForm.getPosition().trim())){
						//说明ddlname_position[i]的数量不为0
						infor=bookRemainForm.getPosition()+":"+bookRemainForm.getNumber()+"本";
						booklastInforForm.setInfor(infor);
						booklastInforForm.setLablevalue(bookRemainForm.getDdlCode());
						booklastInforForm.setRadioname("radio"+bookRemainForm.getDdlCode());
						booklist.add(booklastInforForm);
						break;
					}
					if(j==(userlist.size()-1)){
						//说明ddlname_position[i]的数量为0
						booklastInforForm.setInfor(ddlname_position[i]+":0 本");
						booklastInforForm.setLablevalue(String.valueOf(i+1));
						booklastInforForm.setRadioname("radio"+String.valueOf(i+1));
						booklist.add(booklastInforForm);
					}
				}
							
				
			}
			getRequest().setAttribute("bookList", booklist);
			return "success";
		}
		for(int i=0;i<userlist.size();i++){
			booklastInforForm=new BooklastInforForm();			
			BookRemainForm bookRemainForm=userlist.get(i);
			infor=bookRemainForm.getPosition()+":"+bookRemainForm.getNumber()+"本";
			booklastInforForm.setInfor(infor);
			booklastInforForm.setLablevalue(bookRemainForm.getDdlCode());
			booklastInforForm.setRadioname("radio"+String.valueOf(i+1));
			booklist.add(booklastInforForm);			
		}
		getRequest().setAttribute("bookList", booklist);
		return "success";
	}
	/**
	 * 个人借阅历史查询
	 * @return
	 */
	public String borrowhistory(){
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		if(user==null||String.valueOf(user.getId())==""){
			return "login";
		}
		 long startTime=System.currentTimeMillis();
		List<BookInfoForm> boolist=service.findborrowhistory(user.getId());
		getRequest().setAttribute("bookList", boolist);		
		return "success";
	
	}
	/**
	 * 显示主题
	 * @return
	 */
	public String topmenu(){
		
		return "success";
	
	}
	/**
	 * 借阅排行
	 * @return
	 */
	public String borrowranking(){		
	
		List<Bookinfo> boolist=service.getborrowRanking();
		getRequest().setAttribute("bookList", boolist);		
		return "success";
	
	}
	/**
	 * 根据日期进行借阅排行查询,根据年月日进行借阅排行查询,添加分页
	 * @return
	 */
	public void dateborrowranking(){		
		String starttime=twoBookInfoForm.getStarttime();
		String endtime=twoBookInfoForm.getEndtime();
		String classfi=twoBookInfoForm.getClassfi();
		if(StringUtils.isBlank(classfi)||classfi.equals("0")){
			classfi="  ";
		}
		if(StringUtils.isBlank(starttime)||StringUtils.isBlank(endtime)){
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			starttime=bartDateFormat.format(new Date()) ;
			Calendar calendar = Calendar.getInstance();  
			calendar.setTime(new Date());  
			calendar.add(Calendar.DAY_OF_MONTH, 1);  
			endtime=bartDateFormat.format(calendar.getTime());
			starttime="2015-04-01";
			endtime=bartDateFormat.format(new Date());
		}
		JSONObject booklist=service.getdateborrowranking(starttime,endtime,classfi,twoBookInfoForm);
		
		
		returnObject(booklist);	
	
	
	}
	/**
	 * 书籍分类获取
	 * @return
	 */
	public void classifi(){
		
		String[] result=service.getclassifiarray();
		returnObject(result);
	
	}
	/**
	 * 后台登录
	 * @return
	 */
	public void backlogin(){
		
		//获取user，如果user有权限则显示
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		JSONObject result=new JSONObject();
		result.put("name", user.getName());
		returnObject(result);
		
	}
	/**
	 * 后台首页登录
	 * @return
	 */
	public String  backhome(){
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		//获取user，如果user有权限则显示
		//说明具有权限则可以登录
		if(user.getLevel()>=0){
			return "success";
		}
		return "fail";
	
	}

	

}

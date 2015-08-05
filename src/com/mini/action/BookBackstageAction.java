package com.mini.action;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.dispatcher.SessionMap;

import com.mini.entity.BookBorrowinfo;
import com.mini.entity.BookNumber;
import com.mini.entity.Bookinfo;
import com.mini.entity.ImportBookInfo;
import com.mini.entity.User;
import com.mini.form.AllBookInfoForm;
import com.mini.form.BookBackstageForm;
import com.mini.form.BookBorrowinfoForm;
import com.mini.form.BookHistoryForm;
import com.mini.form.BookInfoForm;
import com.mini.form.BookRemainForm;
import com.mini.form.ImportBookForm;
import com.mini.form.UserHistoryForm;
import com.mini.service.BookBackstageService;
import com.mini.service.BookDoubanInforService;
import com.mini.service.ImportBookService;
import com.mini.util.ExcelFileGenerator;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class BookBackstageAction extends BaseAction implements ModelDriven<BookBackstageForm>{

	private BookBackstageForm backstageform=new BookBackstageForm();
	private BookBackstageService service;

	public BookBackstageService getService() {
		return service;
	}
	public void setService(BookBackstageService service) {
		this.service = service;
	}
	@Override
	public BookBackstageForm getModel() {
		// TODO Auto-generated method stub
		return backstageform;
	}
	public String menuleft(){
		//获取数据类型

		return "success";
	}
	/**
	 * 查询数据，去掉重复值
	 * @return
	 */
	public String list(){
		List<BookBackstageForm> list=service.findKeyWord();
		HttpServletRequest request=getRequest();
		request.setAttribute("systemList", list);
	
		return "success";
	}
	/**
	 * 编辑数据项
	 * @return
	 */
	public String bookDDledit(){
		//获取数据类型
		String keyword=backstageform.getKeyword();
		List<BookBackstageForm> list=service.findsystemddlnameBykeyword(keyword);
		getRequest().setAttribute("systemList", list);
		return "success";
	}
	/**
	 * 保存数据字典
	 * @return
	 */
	public String bookDDlsave(){
		//获取数据类型
		service.saveDdl(backstageform);
		return "success";
	}
	/**
	 * 用户个人借阅查询界面
	 * @return
	 */
	public String userborrowhistory(){
		//获取所有用户的界面
		List<UserHistoryForm> userlist=null;
		if(backstageform.getUsername()==null||backstageform.getUsername().equals("")){
			userlist=service.finduserhistory(getRequest());
		}else{
			userlist=service.finduserhistory(backstageform.getUsername(),getRequest());
			
		}
//		List<UserHistoryForm> userlist=service.finduserhistory(getRequest());
		getRequest().setAttribute("userList", userlist);
		//添加分页功能
		String initflag=getRequest().getParameter("initflag");
		if(initflag!=null && initflag.equals("1")){
			return "list";			
		}
		return "success";
	}
	/**
	 * 个人借阅历史查询
	 * @return
	 */
	public String personborrowhistory(){
		//个人借阅历史查询
		String userid=backstageform.getUserid();//获取用户的userid
		//根据Userid查询借阅历史
		List<UserHistoryForm> userlist=service.findpersonborrowhistoryByuserid(userid);
		getRequest().setAttribute("userList", userlist);
		//查询借阅历史
		return "success";
	}
	/**
	 * 书籍的借阅历史
	 * @return
	 */
	public String bookborrowhistory(){
		//个人借阅历史查询
//		String userid=backstageform.getNumber();//获取用户的userid
		String onlysign=backstageform.getOnlysign();//获取书籍的二维码
		//根据Userid查询借阅历史
		List<BookHistoryForm> booklist=service.findbookborrowhistoryByonlysign(onlysign);
		getRequest().setAttribute("bookList", booklist);
		//查询借阅历史
		return "success";
	}
	/**
	 * 书籍在哪个位置的剩余数量
	 * @return
	 */
	public String bookremainingamount(){
		//查找所有的位置，并把所有位置的数量显示至页面
		String isbn=backstageform.getIsbn10();
		//根据isbn查询剩余数量
		List<BookRemainForm> userlist=service.findbookremainmountByisbn(isbn);
		getRequest().setAttribute("userList", userlist);
		//查询借阅历史
		return "success";
	}
	/**
	 * 显示数据字典项
	 * @return
	 */
	public void dictionaryshow(){
		
		JSONObject result=new JSONObject();
		List<BookBackstageForm> keywordlist=service.findKeyWord();
		JSONObject jsonobject=null;
		List<BookBackstageForm> keywordlist1=new ArrayList<BookBackstageForm>();
		BookBackstageForm bookBackstageForm=null;
		for(int i=0;i<keywordlist.size();i++){
			jsonobject=new JSONObject();
			bookBackstageForm=new BookBackstageForm();
			//获取数据项名称
			String keyword=keywordlist.get(i).getKeyword();
			String[] list=service.findddlname(keyword);
			bookBackstageForm.setKeyword(keyword);
			bookBackstageForm.setItemname(list);
			keywordlist1.add(bookBackstageForm);
			
			
		}
		returnObject(keywordlist1);
	}
	/**
	 * 保存数据字典功能
	 */
	public void dictionarySave(){
		HttpServletRequest request=getRequest();
		String[] itemname = request.getParameterValues("itemname[]"); 
		String keyword=backstageform.getKeyword();
		service.savedictionary(keyword,itemname);
		returnObject("保存成功");
	}
	

}

package com.mini.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.mini.dao.CommonDao;
import com.mini.entity.BookBorrowinfo;
import com.mini.entity.BookNumber;
import com.mini.entity.BookRemainingCount;
import com.mini.entity.Bookdictionary;
import com.mini.entity.Bookinfo;
import com.mini.entity.ImportBookInfo;
import com.mini.entity.User;
import com.mini.form.AllBookInfoForm;
import com.mini.form.BookBackstageForm;
import com.mini.form.BookHistoryForm;
import com.mini.form.BookRemainForm;
import com.mini.form.UserHistoryForm;
import com.mini.util.PageInfo;

public class BookBackstageService {
	private final String book_position="所在位置";
	private final String book_classfi="书籍分类";
	private CommonDao dao;

	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	/**
	 * 查询数据字典的关键字
	 * @return
	 */
	public List<BookBackstageForm> findKeyWord() {
		List<BookBackstageForm> formlist=new ArrayList<BookBackstageForm>();
		BookBackstageForm bookBackstageForm=null;
		String hql = "select distinct o.Keyword from Bookdictionary o";
		List<Object> list=dao.find(hql);
		for(int i=0;i<list.size();i++){
			Object keyword=list.get(i);
			bookBackstageForm=new BookBackstageForm();
			bookBackstageForm.setKeyword(String.valueOf(keyword));
			formlist.add(bookBackstageForm);
		}
		
		return formlist;
	}
	/**
	 * 根据keyword获取数据类型
	 * @param keyword
	 * @return
	 */
	public List<BookBackstageForm> findsystemddlnameBykeyword(String keyword) {
		List<BookBackstageForm> formlist=new ArrayList<BookBackstageForm>();
		BookBackstageForm bookBackstageForm=null;
		String hqlWhere = " and o.Keyword = ?";
		Object [] params = {keyword};
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.DdlCode", "asc");
		List<Bookdictionary> list = dao.findCollectionByConditionNoPage(Bookdictionary.class,hqlWhere, params, orderby);
		for(int i=0;i<list.size();i++){
			Bookdictionary bookdictionary=list.get(i);
			bookBackstageForm=new BookBackstageForm();
			bookBackstageForm.setDdlCode(String.valueOf(bookdictionary.getDdlCode()));
			bookBackstageForm.setDdlName(String.valueOf(bookdictionary.getDdlName()));
			bookBackstageForm.setKeyword(String.valueOf(bookdictionary.getKeyword()));
			bookBackstageForm.setSecid(String.valueOf(bookdictionary.getSecid()));
			formlist.add(bookBackstageForm);
		}
		return formlist;

	}
	/**
	 * 保存数据字典
	 * @param backstageform
	 */
	public void saveDdl(BookBackstageForm backstageform) {
		//获取页面传递的表单值
		//获取数据类型
		String keyword = backstageform.getKeywordname();
		//获取判断是新增数据类型还是在原有的数据类型中编辑的标识
		String typeflag = backstageform.getTypeflag();
		//获取需要保存的数据项的名称
		String [] itemname = backstageform.getItemname();
		//如果是新增数据类型的操作，此时typeflag==new
		if(typeflag!=null && typeflag.equals("new")){
			//保存数据字典
			this.saveSystemDDlWithParams(keyword,itemname);
		}
		//否则是表示在原有的数据类型中进行修改和编辑，此时typeflag==add
		else{
			List<Bookdictionary> list = findSystemDDlListByCondition(keyword);
			dao.deleteObjectByCollection(list);
			//保存数据字典
			this.saveSystemDDlWithParams(keyword,itemname);
		}
		
	}
	/**
	 * 保存数据字典
	 * @param backstageform
	 */
	public void savedictionary(String keyword, String[] itemname) {
		//判断数据类型是否存在
		String typeflag =null;
		String hql = "select distinct o.Keyword from Bookdictionary o";
		List<Object> daolist=dao.find(hql);		
		for(int i=0;i<daolist.size();i++){
			String newkeyword=daolist.get(i).toString();
			if(newkeyword.equals(keyword)){
				typeflag="add";
				break;
			}
			if(i==daolist.size()){
				//书名不存在匹配项
				typeflag="new";
			}			
		}
		//获取页面传递的表单值
		//获取数据类型
		
		//获取判断是新增数据类型还是在原有的数据类型中编辑的标识
		//获取需要保存的数据项的名称
	
		//如果是新增数据类型的操作，此时typeflag==new
		if(typeflag!=null && typeflag.equals("new")){
			//保存数据字典
			this.saveSystemDDlWithParams(keyword,itemname);
		}
		//否则是表示在原有的数据类型中进行修改和编辑，此时typeflag==add
		else{
			List<Bookdictionary> list = findSystemDDlListByCondition(keyword);
			dao.deleteObjectByCollection(list);
			//保存数据字典
			this.saveSystemDDlWithParams(keyword,itemname);
		}
	
		
	}
	private List<Bookdictionary> findSystemDDlListByCondition(String keyword) {
		String hqlWhere = " and o.Keyword = ?";
		Object [] params = {keyword};
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.DdlCode", "asc");
		List<Bookdictionary> list = dao.findCollectionByConditionNoPage(Bookdictionary.class,hqlWhere, params, orderby);
		return list;
	}
	private void saveSystemDDlWithParams(String keyword, String[] itemname) {
		Bookdictionary bookdictionary=new Bookdictionary();
		List<Bookdictionary> list = new ArrayList<Bookdictionary>();
		for(int i=0;itemname!=null && i<itemname.length;i++){
			Bookdictionary elecSystemDDl = new Bookdictionary();
			elecSystemDDl.setKeyword(keyword);
			elecSystemDDl.setDdlName(itemname[i]);
			elecSystemDDl.setDdlCode(new Integer(i+1));
			list.add(elecSystemDDl);
			//elecSystemDDlDao.save(elecSystemDDl);
		}
		dao.saveObjectByCollection(list);
	}
	/**
	 * 查找用户
	 * @param request
	 * @return
	 */
	public List<UserHistoryForm> finduserhistory(HttpServletRequest request) {
		List<String> paramsList = new ArrayList<String>();
		String hqlWhere = "";
		Object [] params = paramsList.toArray();
		//组织排序		
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.name", "desc");
		PageInfo pageinfo=new PageInfo(request);
		List<User> list=dao.findCollectionByConditionWithPage(User.class,hqlWhere, params, orderby,pageinfo);		
		request.setAttribute("page", pageinfo.getPageBean());
		List<UserHistoryForm> newlist=UserhistoryPOTOVo(list);
		return newlist;
		
	}	
	
	public List<UserHistoryForm> finduserhistory(String username,
			HttpServletRequest request) {
		List<String> paramsList = new ArrayList<String>();
		String hqlWhere = "and o.name like ? ";
		paramsList.add('%' + username + '%');
		Object [] params = paramsList.toArray();
		//组织排序		
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.name", "desc");
		//添加分页
		PageInfo pageinfo=new PageInfo(request);
		List<User> list=dao.findCollectionByConditionWithPage(User.class,hqlWhere, params, orderby,pageinfo);
		request.setAttribute("page", pageinfo.getPageBean());
		List<UserHistoryForm> newlist=UserhistoryPOTOVo(list);
		
		return newlist;
	}
	private List<UserHistoryForm> UserhistoryPOTOVo(List<User> list) {
		List<UserHistoryForm> newlist=new ArrayList<UserHistoryForm>();
		UserHistoryForm userHistoryForm=null;
		for(int i=0;i<list.size();i++){
			User user=list.get(i);			
			userHistoryForm=new UserHistoryForm();
			userHistoryForm.setUsername(user.getName());
			userHistoryForm.setNumber(user.getNumber());
			userHistoryForm.setPhone(user.getPhone());
			userHistoryForm.setUserid(String.valueOf(user.getId()));
			newlist.add(userHistoryForm);			
		}
		return newlist;
	}
	/**
	 * 根据useid查询借阅历史
	 * @param userid
	 * @return
	 */
	public List<UserHistoryForm> findpersonborrowhistoryByuserid(String userid) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String hql = "From BookBorrowinfo s Where s.borrower = ?";
		//查找信息
		List<BookBorrowinfo> list=dao.find(hql,userid);
		List<UserHistoryForm> newlist=new ArrayList<UserHistoryForm>();
		UserHistoryForm userHistoryForm=null;
		for(int i=0;i<list.size();i++){
			BookBorrowinfo bookBorrowinfo=list.get(i);
			userHistoryForm=new UserHistoryForm();
			if(bookBorrowinfo.getDate()!=null){
				userHistoryForm.setBorrowdate(bartDateFormat.format(bookBorrowinfo.getDate()));
			}			
			hql = "From ImportBookInfo s Where s.onlysign = ?";
			List<ImportBookInfo> bookinforlist=dao.find(hql,bookBorrowinfo.getOnlysign());
			if(bookinforlist!=null&&bookinforlist.size()!=0){
				userHistoryForm.setBookname(bookinforlist.get(0).getName());
			}
			if(bookBorrowinfo.getBackdate()!=null){
				userHistoryForm.setBackdate(bartDateFormat.format(bookBorrowinfo.getBackdate()));
			}
			newlist.add(userHistoryForm);			
		}
		return newlist;
	}
	/**
	 * 根据isbn查询剩余数量,返回结果为所在位置和所在位置的剩余数量
	 * @param isbn
	 * @return
	 */
	public List<BookRemainForm> findbookremainmountByisbn(String isbn) {
//		select count(*) from (select *  from book_import_infor where isbn10='9787108017079' and state='0' ) b,book_borrow_infor s where b.onlysign=s.onlysign
//		String hql = "from (select *  from ImportBookInfo  a where "
//				+ "a.isbn10= "+isbn
//				+ "and a.state='0' ) b,BookBorrowinfo s where b.onlysign=s.onlysign";
		List<BookRemainingCount>  bookRemainingCount=dao.countnativesql(isbn);
		List<BookRemainForm> resultlist=FindRemainMountPOToVO(bookRemainingCount,isbn);
		
//		System.out.println(bookRemainingCount);
		return resultlist;
	}
	private List<BookRemainForm> FindRemainMountPOToVO(
			List<BookRemainingCount> list, String isbn) {
		List<BookRemainForm> resultlist=new ArrayList<BookRemainForm>();
		BookRemainForm bookRemainForm=null;
		for(int i=0;i<list.size();i++){
			BookRemainingCount bookRemainingCount=list.get(i);
			bookRemainForm=new BookRemainForm();
			bookRemainForm.setBookname(bookRemainingCount.getBookname());
			bookRemainForm.setNumber(bookRemainingCount.getCount());
			bookRemainForm.setPosition(bookRemainingCount.getDdlName());
			resultlist.add(bookRemainForm);
		}		
		return resultlist;
	}
	/**
	 * 根据书籍的onlysign获取书籍的借阅历史
	 * @param onlysign
	 * @return
	 */
	
	public List<BookHistoryForm> findbookborrowhistoryByonlysign(String onlysign) {
		String hql = "From BookBorrowinfo s Where s.onlysign = ?";
		//查找信息
		List<BookBorrowinfo> list=dao.find(hql,onlysign);
		if(list==null||list.size()==0){
			return null;
		}
		BookHistoryForm bookHistoryForm=null;
		String[] ddlname_position=this.findddlname(book_position);
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		List<BookHistoryForm> returnlist=new ArrayList<BookHistoryForm>();
		for(int i=0;i<list.size();i++){
			BookBorrowinfo bookBorrowinfo=list.get(i);
			bookHistoryForm=new BookHistoryForm();
			//查找书籍的名称
			hql="From ImportBookInfo s Where s.onlysign = ? ";
			List<ImportBookInfo> bookinfolist=dao.find(hql,onlysign);
			if(bookinfolist==null||bookinfolist.size()==0){
				return null;
			}
			bookHistoryForm.setBookname(bookinfolist.get(0).getName());
			bookHistoryForm.setOnlysign(onlysign);
			bookHistoryForm.setBorrowposition(ddlname_position[Integer.valueOf(bookBorrowinfo.getBorrowposition())-1]);
			if(bookBorrowinfo.getBackposition()!=null){
				bookHistoryForm.setBackposition(ddlname_position[Integer.valueOf(bookBorrowinfo.getBackposition())-1]);
			}
			bookHistoryForm.setBorrowdate(bartDateFormat.format(bookBorrowinfo.getDate()));
			if(bookBorrowinfo.getBackdate()!=null){
				bookHistoryForm.setBackdate(bartDateFormat.format(bookBorrowinfo.getBackdate()));
			}
			//根据userid查询借阅者的姓名
			hql = "From User u Where u.id = ?";			
			List<User> userList = dao.find(hql, Integer.valueOf(bookBorrowinfo.getBorrower()));
			bookHistoryForm.setBorrower(userList.get(0).getName());
			returnlist.add(bookHistoryForm);
		}
		return returnlist;
	}
	/**
	 * 根据查询条件对应的dllnamem,cuowude 
	 * @param keyword
	 * @return
	 */
	public String[] findddlname(String keyword) {
	
		List<String> paramsList = new ArrayList<String>();
		String hqlWhere = "and o.Keyword = ? ";
		paramsList.add(keyword);
		Object [] params = paramsList.toArray();	//组织排序		
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.DdlCode", "asc");
		List<Bookdictionary> list=dao.findCollectionByConditionNoPage(Bookdictionary.class,hqlWhere, params, orderby);

		String[] result=new String[list.size()];
		for(int i=0;i<list.size();i++){
			Bookdictionary bookdictionary=list.get(i);
			result[i]=bookdictionary.getDdlName();
//			System.out.println(result[i]);
		}

		return result;
	}
	
	
	


}

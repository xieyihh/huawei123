package com.mini.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.PropertyProjection;
import org.hibernate.criterion.Restrictions;































import com.alibaba.fastjson.JSON;
import com.mini.action.BaseAction;
import com.mini.dao.CommonDao;
import com.mini.entity.Activity;
import com.mini.entity.Association;
import com.mini.entity.BookBorrowinfo;
import com.mini.entity.BookNice;
import com.mini.entity.BookNumber;
import com.mini.entity.BookRemainingCount;
import com.mini.entity.BookType;
import com.mini.entity.Bookdictionary;
import com.mini.entity.Bookinfo;
import com.mini.entity.ImportBookInfo;
import com.mini.form.AllBookInfoForm;
import com.mini.form.BookBorrowinfoForm;
import com.mini.form.BookInfoForm;
import com.mini.form.BookRankingForm;
import com.mini.form.BookRemainForm;
import com.mini.form.BookSystemDDlForm;
import com.mini.form.ImportBookForm;
import com.mini.form.TwoBookInfoForm;
import com.mini.util.PageInfo;
import com.mini.util.PagerTool;


public class PhoneBookService  {
	private CommonDao dao;
	private final int pageSize=4;
	private boolean hasPrevious;
	private boolean hasNext;
	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	public void test() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 手机页面的查询结果显示
	 * @param twoBookInfoForm
	 * @param request
	 * @return
	 */
	public JSONObject searchbookOnphone(
			TwoBookInfoForm bookform, HttpServletRequest request) {
		String bookname=bookform.getBookname();
		PagerTool pagerTool=new PagerTool();
		List<String> paramsList = new ArrayList<String>();
		int totalRows=0;
		//当前页数
		int pageNum=1;
		if(StringUtils.isNotBlank(bookform.getCurrentPage())){
			pageNum=Integer.valueOf(bookform.getCurrentPage());
		}
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);
		String hqlWhere = " ";
		if(StringUtils.isNotBlank(bookname)){
			hqlWhere += " and o.name like ?";
			paramsList.add("%" + bookname + "%");
			
		}	
		//组织排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.isbn10", "desc");
	
		Object [] params = paramsList.toArray();
		//得到查询结果
		List<ImportBookInfo> returlist=dao.findPage(ImportBookInfo.class, hqlWhere, params, orderby, pagerTool);		
		//页面数据重新初始化
		totalRows=pagerTool.getTotalRows();
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);
		BookInfoForm bookInfoForm=null;
		Bookinfo bookinfo=null;
		List<BookInfoForm> newlist=new ArrayList<BookInfoForm>();
		for(int i=0;i<returlist.size();i++){	
			String isbn10=returlist.get(i).getIsbn10();
			List<Bookinfo> bookinfolist=finddoubanbookinfo(isbn10);
			if(bookinfolist!=null){
				bookInfoForm=BookInfoPoToVo(bookinfolist.get(0));
				newlist.add(bookInfoForm);
			}
		}
		JSONObject result = new JSONObject();
		result.put("totalRows", totalRows);
		result.put("totalPages", pagerTool.getTotalPages());
		result.put("booklist", newlist);
		return result;
	}
	/**
	 * 将BookInfoPoToVo的PO对象变为VO对象
	 * @param bookinfo
	 * @return
	 */
	private BookInfoForm BookInfoPoToVo(Bookinfo bookinfo) {

		BookInfoForm bookInfoForm=new BookInfoForm();
		bookInfoForm.setBookName(bookinfo.getName());
		bookInfoForm.setSummary(bookinfo.getSummary());
		bookInfoForm.setIsbn10(bookinfo.getIsbn10());
		bookInfoForm.setAuto(bookinfo.getAuto());
		bookInfoForm.setImage(bookinfo.getImage());

		return bookInfoForm;
	}
	/**
	 * 根据书籍的Isbn获取书籍的豆瓣信息
	 * @param isbn
	 * @return
	 */
	public List<Bookinfo> finddoubanbookinfo(String isbn) {
		// TODO Auto-generated method stub
		String hql = "From Bookinfo s Where s.isbn10 = ?";
		//查找信息
		
		List<Bookinfo> list=dao.find(hql,isbn);
		if(list!=null&&list.size()!=0){
			return list;
		}
		return null;
		

	}
	/**
	 * 
	 * @return
	 */
	public JSONObject searchphonebooknice() {
		List<BookNice> list=dao.find(BookNice.class);
		List<BookInfoForm> resultlist=new ArrayList<BookInfoForm>();
		for(int i=0;i<list.size();i++){
			BookNice  bookNice=list.get(i);
			
			BookInfoForm bookInfoForm=this.finddoubanbookinfoByIsbn(bookNice.getIsbn10(),null);
			resultlist.add(bookInfoForm);
		}
		JSONObject result = new JSONObject();

		result.put("booklist", resultlist);
		return result;
		
		
	}
	/**
	 * 根据isbn10查找书籍信息
	 * @param isbn10
	 * @param bookname 
	 * @return
	 */
	private BookInfoForm finddoubanbookinfoByIsbn(String isbn10, String bookname) {	
		List<Bookinfo> list1=finddoubanbookinfo(isbn10);
		BookInfoForm bookInfoForm=new BookInfoForm();
		if(list1==null||list1.size()==0){
			//s说明在豆瓣中没有书籍
			bookInfoForm.setBookName(bookname);
			bookInfoForm.setIsbn10(isbn10);
			return bookInfoForm;
		}
		Bookinfo bookinfo=finddoubanbookinfo(isbn10).get(0);
		
		
		if(bookname!=null){
			bookInfoForm.setBookName(bookname);
		}else{
			bookInfoForm.setBookName(bookinfo.getName());
		}
		
		bookInfoForm.setImage(bookinfo.getImage());
		if(bookinfo.getSummary().length()>50){
			bookInfoForm.setSummary(bookinfo.getSummary().substring(0, 50)+"...");
		}else{
			bookInfoForm.setSummary(bookinfo.getSummary());
		}
		bookInfoForm.setAuto(bookinfo.getAuto());
		bookInfoForm.setIsbn10(isbn10);
		return bookInfoForm;
	}

	
	
	
}

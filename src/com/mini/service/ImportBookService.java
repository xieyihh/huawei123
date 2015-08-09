package com.mini.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.mini.util.InitString;
import com.mini.util.PageInfo;
import com.mini.util.PagerTool;


public class ImportBookService  {
	private CommonDao dao;
	//private String apikey = "09dbb227aa8249cc1d931676ff86dd64";  
//	private String apikey = "08e1839b5727e44e1aa48ad20577acea"; 
	private String apikey = "0508bb90854a01450f1a32b18fde03cf";
	private final String orgin_state="0";
	private final String book_position="所在位置";
	private final String book_classfi="书籍分类";
	private int pageSize=9;
	private boolean hasPrevious;
	private boolean hasNext;
//	private String onlysignweb="http://localhost/mini/scan.action?onlysign=";
	private String onlysignweb="http://119.29.36.111/mini/scan.action?onlysign=";

//	private final String filepath="g:\\项目\\实习\\代码\\wuaiwujia\\WebContent\\Bookimage\\";
//	private  String filepath=.getSession().getServletContext().getRealPath("/"); ;
	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	public void test(){
//		System.out.println("YES SERVICE");
	}
	public String savebookinfor(File file,ArrayList<String> errline) throws Exception  {
		BufferedReader filedata = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = "";
		if((line=filedata.readLine())==null){
			return "firstnotmatch";	
		}
		if(!(line.trim().equals(InitString.Book_importhead)||line.trim().equals(InitString.Book_importhead+","))){
			return "firstnotmatch";
		}
		String[] ddlname_position=this.findddlname(book_position);
		String[] ddlname_classfi=this.findddlname(book_classfi);
		ImportBookInfo  bookimportinfo=null;
		while ((line = filedata.readLine()) != null) { 
			//读取数据,进行分割
        	String[] data=line.split(",");
        	String isbn=data[0].trim();
        	if(StringUtils.isBlank(isbn)){
        		//数据信息有错误
        		errline.add(line);
        		continue;
        	}
        	String name=data[1].trim();
        	if(StringUtils.isBlank(name)){
        		//数据信息有错误
        		errline.add(line);
        		continue;
        	}
        	
        	String position=data[2].trim();
        	if(StringUtils.isBlank(position)){
        		//数据信息有错误
        		errline.add(line);
        		continue;
        	}
        	String number=data[3].trim();
        	if(StringUtils.isBlank(number)){
        		//数据信息有错误
        		errline.add(line);
        		continue;
        	}
        	String source=data[4].trim();
        	if(StringUtils.isBlank(source)){
        		//数据信息有错误
        		errline.add(line);
        		continue;
        	}
        	String positionnumber=this.setBookPosition(ddlname_position,position);
			if(positionnumber.equals("error")){
				errline.add(line);
				continue;
			}	
			String classifi=data[5].trim();
        	if(StringUtils.isBlank(classifi)){
        		//数据信息有错误
        		errline.add(line);
        		continue;
        	}
        	String classfinumber=this.setBookPosition(ddlname_classfi,classifi);
			if(classfinumber.equals("error")){
				errline.add(line);
				continue;
			}
			//进行数据更新       	
        	int res=dao.count(BookNumber.class,"isbn",isbn);
        	int intnumber=Integer.valueOf(number);
			if(res!=0){
				//说明书籍已经导入,则更新书籍的数量信息				
				String hql ="Update BookNumber a Set a.number=a.number+? where a.isbn=?";						
				dao.updateByQuery(hql, intnumber,isbn);				
			}else{
				BookNumber booknewnumber=new BookNumber();
				booknewnumber.setIsbn(isbn);
				booknewnumber.setNumber(intnumber);
				dao.save(booknewnumber);
			}			
			for(int j=0;j<intnumber;j++){				
				//生成唯一的uuid
				String onlysign=UUID.randomUUID().toString();;
				bookimportinfo=new ImportBookInfo();
				bookimportinfo.setIsbn10(isbn);
				bookimportinfo.setName(name);					
				bookimportinfo.setImportdate(new Date());;
				bookimportinfo.setSource(source);
				bookimportinfo.setOnlysign(onlysign);
				bookimportinfo.setState(orgin_state);
				//设置书籍所在的位置,进行匹配，判断在数据字典中是否存在,存在则存储数据字典的选项								
				bookimportinfo.setPosition(positionnumber);;				
				//设置书籍的分类，进行匹配，判断在数据字典中是否存在,存在则存储数据字典的选项
				bookimportinfo.setClassifi(classfinumber);;;
				dao.save(bookimportinfo);			
				
			}			
			
		}	
		return "success";
		
	}
	private String setfixnumber(int number){
		String snumber=String.valueOf(number);
		String result=snumber;
		if(snumber.length()<6){
			for(int i=snumber.length();i<6;i++){
				result="0"+result;
			}
		}
		return result;
		
	}
	private String setBookPosition(String[] col,
			String value) {
		for(int i=0;i<col.length;i++){
			if(col[i].trim().equals(value)){
				return String.valueOf(i+1);
			}
		}
		return "error";
	}
	//
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
			result[i]=bookdictionary.getDdlName().trim();
		}

		return result;
	}

	/**
	 * 查找所有的信息进行显示，进行数据字典匹配
	 * @param request 
	 * @return
	 */
	public List<AllBookInfoForm> findbookinfolist(HttpServletRequest request) {
		
		List<String> paramsList = new ArrayList<String>();
		String hqlWhere = "";
		//组织排序		
		//添加分页
		PageInfo pageinfo=new PageInfo(request);		
		hqlWhere="and o.state=?";
		paramsList.add("0");		
		Object [] params = paramsList.toArray();
		//组织排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.isbn10", "desc");		
		List<ImportBookInfo> list=dao.findCollectionByConditionWithPage(ImportBookInfo.class,hqlWhere, params, orderby,pageinfo);
		request.setAttribute("page", pageinfo.getPageBean());
		List<AllBookInfoForm> newlist=this.ImportBookInfoPoToVo(list);			
		return newlist;
	}
	
	private List<AllBookInfoForm> ImportBookInfoPoToVo(List<ImportBookInfo> list) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		List<AllBookInfoForm> newlist=new ArrayList<AllBookInfoForm>();
		AllBookInfoForm allBookInfoForm=null;
		for(int i=0;i<list.size();i++){
			ImportBookInfo importBookInfo=list.get(i);			
			allBookInfoForm=new AllBookInfoForm();
			if(importBookInfo.getImportdate()!=null){
				allBookInfoForm.setImportdate(bartDateFormat.format(importBookInfo.getImportdate()));
			}
			allBookInfoForm.setIsbn10(importBookInfo.getIsbn10());
			allBookInfoForm.setName(importBookInfo.getName());
			String hql="From BookNumber a Where a.isbn = ?";
			BookNumber bookNumber=(BookNumber) dao.get(hql, importBookInfo.getIsbn10());
			allBookInfoForm.setNumber(bookNumber.getNumber());
			//设置书籍的位置，从数据字典中查询
			String[] ddlname_position=this.findddlname(book_position);
			if(importBookInfo.getPosition()==null||importBookInfo.getPosition().equals("")){
				allBookInfoForm.setPosition(ddlname_position[0]);
			}else{
				allBookInfoForm.setPosition(ddlname_position[Integer.valueOf(importBookInfo.getPosition())-1]);
			}				
			//设置书籍的分类信息，从数据字典中查询
			allBookInfoForm.setOnlysign(importBookInfo.getOnlysign());
			String[] ddlname_classfi=this.findddlname(book_classfi);
			if(importBookInfo.getClassifi()==null||importBookInfo.getClassifi().equals("")){
				allBookInfoForm.setClassifi(ddlname_classfi[0]);
			}else{
				allBookInfoForm.setClassifi(ddlname_classfi[Integer.valueOf(importBookInfo.getClassifi())-1]);
			}			
			allBookInfoForm.setSource(importBookInfo.getSource());
			newlist.add(allBookInfoForm);			
		}		
		return newlist;
	}
	/**
	 * 下载书籍的豆瓣信息到数据库
	 * @param isbn
	 * @throws Exception 
	 */
	public String  savedoubanbookinfo(String isbn) throws Exception {
		String hql = "From Bookinfo s Where s.isbn10 = ?";
		//查找信息
		List<Bookinfo> list=dao.find(hql,isbn);
		if(list!=null&&list.size()!=0){
			return "exit";
		}		
		String isbnUrl = "http://api.douban.com/v2/book/isbn/:"+isbn; 
		String requestUrl = isbnUrl  + "?apikey=" + apikey; 
//		System.out.println(requestUrl);
		HttpClient client = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(requestUrl);  
		StringBuilder entityStringBuilder=new StringBuilder();
		 org.json.JSONObject resultJsonObject=null;
		 // 客户端执行get请求 返回响应实体  					
		try {
			HttpResponse response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			InputStream is=entity.getContent();
//			System.out.println(is);
			BufferedReader bufferedReader=new BufferedReader
                    (new InputStreamReader(entity.getContent(), "UTF-8"), 8*1024);
            String line=null;
            while ((line=bufferedReader.readLine())!=null) {
                entityStringBuilder.append(line+"/n");
            }
            resultJsonObject=new  org.json.JSONObject(entityStringBuilder.toString());
            System.out.println(resultJsonObject);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(resultJsonObject.has("msg")){		
			if(resultJsonObject.getString("msg").equals("book_not_found")){
				//未找到书籍信息
				return "nobook";
			}			
		}
		String summary=resultJsonObject.getString("summary");
		String author=resultJsonObject.getString("author").split("\"")[1];
		String name=resultJsonObject.getString("title");
		String image=resultJsonObject.getString("image");
		String price=resultJsonObject.getString("price");
		String out=summary+author+name+image+price;
		Bookinfo bookinfo=new Bookinfo();
		bookinfo.setAuto(author);
		bookinfo.setImage(image);
		bookinfo.setSummary(summary);
		bookinfo.setName(name);
		bookinfo.setPrice(price);
		bookinfo.setIsbn10(isbn);
		dao.save(bookinfo);	
		return "sucess";

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
		return list;

	}

	public String getbookinfoByonlysign(String onlysign) {
		String hql = "From ImportBookInfo s Where s.onlysign = ?";
		//查找信息
		List<ImportBookInfo> list=dao.find(hql,onlysign);
		if(list==null||list.size()==0){
			return "not exit";
		}
		hql = "From BookBorrowinfo s Where s.onlysign = ?";
		List<BookBorrowinfo> bookBorrowinfolist=dao.find(hql,onlysign);
		if(bookBorrowinfolist==null||bookBorrowinfolist.size()==0){
			return "success";
		}else{
			for(int i=0;i<bookBorrowinfolist.size();i++){
				BookBorrowinfo bookBorrowinfo=bookBorrowinfolist.get(i);
				//说明书籍已经借阅出去
				if(bookBorrowinfo.getState().equals("1")){
					return "has_borrow";
				}
				
			}
			return "success";
			
			
		}
	}
	/**
	 * 保存借阅书籍的信息
	 * @param usernumber
	 * @param onlysign
	 * infolable 代表借书地点对应的ddlcode,
	 * @return
	 */
	public String saveborrowbookinfo(String userid, String onlysign,String infolable) {
		// TODO Auto-generated method stub
		BookBorrowinfo bookborrowinfo=new BookBorrowinfo();
		bookborrowinfo.setBorrower(userid);
		bookborrowinfo.setDate(new Date());
		bookborrowinfo.setOnlysign(onlysign);
		bookborrowinfo.setBorrowposition(infolable);
		//1代表已经借阅出去
		bookborrowinfo.setState("1");
		dao.save(bookborrowinfo);
		return null;
	}
	public List<ImportBookInfo> findimportbookinfolist(String isbn) {
		String hql = "From ImportBookInfo s Where s.isbn10 = ?";
		//查找信息
		List<ImportBookInfo> list=dao.find(hql,isbn);
		
		// TODO Auto-generated method stub
		return list;
	}
	/**
	 * 导入图书分类的信息
	 * @param excel
	 * @return
	 * @throws Exception 
	 * @throws FileNotFoundException 
	 */
	public String savebooktypeinfor(File file) throws FileNotFoundException, Exception {
		POIFSFileSystem fs= new POIFSFileSystem(new FileInputStream(file));
		HSSFWorkbook wb=new HSSFWorkbook(fs);//得到excel
		HSSFSheet sheet = wb.getSheetAt(0);//得到表一	
		// 标题总列数       
        int rowNum=sheet.getLastRowNum();//行数量
        for(int i=1;i<rowNum;i++){
        	HSSFRow row = sheet.getRow(i);        	
    		BookType booktype=new BookType();
    		if(row.getCell(0)!=null){
    			booktype.setParent(row.getCell(0).getStringCellValue().trim());
    		}
    		if(row.getCell(1)!=null){
    			booktype.setTitle(row.getCell(1).getStringCellValue().trim());
    		}
    		if(row.getCell(2)!=null){
    			booktype.setName(row.getCell(2).getStringCellValue().trim());
    			dao.save(booktype);
    		}        		       		
        	
        }
		return null;
	}
	public JSONObject getbookinfoBybookname(ImportBookForm importBookForm, HttpServletRequest request)  {
		String bookname=importBookForm.getBookName();
		String source=importBookForm.getSource();
		String searchisbn10=importBookForm.getIsbn10();
		String Isisbn10=importBookForm.getIsisbn10();
		String position=importBookForm.getPosition();
		List<String> paramsList = new ArrayList<String>();
		//当前页数
		int pageNum=1;
		pageSize=Integer.valueOf(importBookForm.getPageSize());
		if(StringUtils.isNotBlank(importBookForm.getCurrentPage())){
			pageNum=Integer.valueOf(importBookForm.getCurrentPage());
		}
		
		int totalRows=0;
		List<ImportBookInfo> returlist=new ArrayList<ImportBookInfo>();
		List<ImportBookForm> newlist=new ArrayList<ImportBookForm>();
		
		String hqlWhere = " and state='0' ";
		PagerTool pagerTool=new PagerTool();
		
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);

			
			if(StringUtils.isNotBlank(bookname)){
				hqlWhere += " and o.name like ?";
				paramsList.add("%" + bookname + "%");
				
			}
			if(StringUtils.isNotBlank(source)){
				hqlWhere += " and o.source like ?";
				paramsList.add("%" + source + "%");
				
			}
			if(StringUtils.isNotBlank(searchisbn10)){
				hqlWhere += " and o.isbn10 like ?";
				paramsList.add("%" + searchisbn10 + "%");				
			}
			if(StringUtils.isNotBlank(position)){
				if(!position.equals("0")){
					hqlWhere += " and o.position = ?";
					paramsList.add( position );	
				}
							
			}
			
			String grouplist="";
			if(StringUtils.isNotBlank(Isisbn10)&&Isisbn10.equals("0")){
				//根类
				grouplist="isbn10";
				hqlWhere += " group by isbn10";
			}
			//组织排序
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("o.isbn10", "desc");
			Object [] params = paramsList.toArray();
			returlist=dao.findPage(ImportBookInfo.class, hqlWhere, params, orderby, pagerTool);
			totalRows=pagerTool.getTotalRows();
			
//		}
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);
		
		String[] ddlname_position=this.findddlname(book_position);
		ImportBookInfo importBookInfo=null;
		for(int i=0;i<returlist.size();i++){
			//查询书籍的数量
			String old_hql="From BookNumber a Where a.isbn = ?";
			importBookInfo=(ImportBookInfo)returlist.get(i);
			importBookForm=ImportBookPoToVo(importBookInfo);
			String isbn10=returlist.get(i).getIsbn10();
			BookNumber bookNumber=(BookNumber) dao.get(old_hql,isbn10);		
	
			importBookForm.setNumber(String.valueOf(bookNumber.getNumber()));
			int po=Integer.valueOf(returlist.get(i).getPosition());
			String positon=ddlname_position[po-1];
			importBookForm.setPosition(positon);
			String bookinfohql="From Bookinfo s Where s.isbn10 = ? ";
			List<Bookinfo> bookinfolist=dao.find(bookinfohql,isbn10);
			if(bookinfolist==null||bookinfolist.size()==0){
				importBookForm.setDownstate("未下载");
			}else{
				importBookForm.setDownstate("已下载");
			}
			newlist.add(importBookForm);
			
		}
		JSONObject result = new JSONObject();
		result.put("totalRows", totalRows);
		result.put("totalPages", pagerTool.getTotalPages());
		result.put("booklist", newlist);
		result.put("sourcelist",JSONArray.fromObject(ddlname_position));
		return result;
	}
	
	/**
	 * 将ImportBookInfo的PO对象变为VO对象
	 * @param importBookInfo
	 * @return
	 */
	private ImportBookForm ImportBookPoToVo(ImportBookInfo importBookInfo) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		ImportBookForm importBookForm=new ImportBookForm();
		importBookForm.setBookName(importBookInfo.getName());
		importBookForm.setIsbn10(importBookInfo.getIsbn10());
		importBookForm.setId(String.valueOf(importBookInfo.getId()));
		importBookForm.setOnlysign(importBookInfo.getOnlysign());
		importBookForm.setPosition(importBookInfo.getPosition());
		importBookForm.setSource(importBookInfo.getSource());
		importBookForm.setImportdate(bartDateFormat.format(importBookInfo.getImportdate()));		
		importBookForm.setBookid(String.valueOf(importBookInfo.getId()));
		return importBookForm;
	}
	/**
	 * 个人搜索书的信息
	 * @param bookname
	 * @return
	 */
	public List<TwoBookInfoForm> personsearchBybookname(String bookname,HttpServletRequest request) {
		List<String> paramsList = new ArrayList<String>();
		String hqlWhere = "and o.name like ? group by name";
		paramsList.add('%' + bookname + '%');
		Object [] params = paramsList.toArray();//组织排序
		PageInfo pageinfo=new PageInfo(request);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.isbn10", "desc");
		@SuppressWarnings("unchecked")
		List<Bookinfo> list=dao.findCollectionByConditionWithPage(Bookinfo.class,hqlWhere, params, orderby,pageinfo);
		request.setAttribute("page", pageinfo.getPageBean());
		List<TwoBookInfoForm> newlist=new ArrayList<TwoBookInfoForm>();
		if(list.size()==0){
			return null;
		}			
		TwoBookInfoForm twoBookInfoForm=null;
		for(int i=0;i<list.size();i++){
			Bookinfo bookinfo=list.get(i);	
			twoBookInfoForm=new TwoBookInfoForm();
			twoBookInfoForm.setIsbn1(bookinfo.getIsbn10());
			twoBookInfoForm.setImage1(bookinfo.getImage());
			twoBookInfoForm.setName1(bookinfo.getName());		
			newlist.add(twoBookInfoForm);			
		}
		return newlist;	
	
		
		
	}
	/**
	 * 根据isbn获取onlysign的值
	 * @param isbn
	 * @return
	 */
	public List<String> findOnlysignByisbn(String isbn) {
		String hql = "Select onlysign From ImportBookInfo s Where s.isbn10 = ?";
		//查找信息
		List<String> list=dao.find(hql,isbn);
		return list;
	}
	/**
	 * 修改书籍信息并保存
	 * @param bookinfoform
	 * @param request 
	 * @return
	 * @throws Exception 
	 */
	public String updateimportbookinfo(BookInfoForm bookinfoform, HttpServletRequest request) throws Exception {
		//修改书籍的isbn10，书名，图书来源，二维码，位置，数量
		ImportBookInfo importBookInfo=new ImportBookInfo();
		importBookInfo.setId(Integer.valueOf(bookinfoform.getId()));
		String old_hql="From ImportBookInfo a Where a.id = ?";
		ImportBookInfo oldimportBookInfo=(ImportBookInfo) dao.get(old_hql, Integer.valueOf(bookinfoform.getId()));
		String hql=null;
		if(!oldimportBookInfo.getIsbn10().equals(bookinfoform.getIsbn10())){
			//如果isbn进行了更新，则需要改变数量里面的isbn
			//更新导入信息的isbn10
			hql ="Update ImportBookInfo a Set a.isbn10=?,a.name=?,a.source=?,a.onlysign=?,a.position=? where a.isbn10=?";
			dao.updateByQuery(hql, bookinfoform.getIsbn10(),bookinfoform.getBookName(),bookinfoform.getSource(),
					bookinfoform.getOnlysign(),bookinfoform.getPosition(),oldimportBookInfo.getIsbn10());
			//更新导入数量的isbn,首先判断isbn是否存在
			hql="Update BookNumber a Set a.isbn=? where a.isbn=?";		
			int count=dao.count(BookNumber.class, "isbn",oldimportBookInfo.getIsbn10());
			if(count!=0){
				//说明isbn存在
				hql ="Update BookNumber a Set a.isbn=? where a.isbn=?";					
				dao.updateByQuery(hql, bookinfoform.getIsbn10(),oldimportBookInfo.getIsbn10());
			}else{
				BookNumber bookNumber=new BookNumber();
				bookNumber.setIsbn( bookinfoform.getIsbn10());
				bookNumber.setNumber(1);
				dao.save(bookNumber);
			}
			
		}else{
			hql ="Update ImportBookInfo a Set a.isbn10=?,a.name=?,a.source=?,a.onlysign=?,a.position=?,a.classifi=? where a.id=?";
			//此处的postion应该为传进来的value代表值
			dao.updateByQuery(hql, bookinfoform.getIsbn10(),bookinfoform.getBookName(),bookinfoform.getSource(),
					bookinfoform.getOnlysign(),bookinfoform.getPosition(),bookinfoform.getClassifi(),Integer.valueOf(bookinfoform.getId()));
			
			//要判断fil+e是否存在，如果存在则保存Image,路径为Bookimage/isbn10
			if(bookinfoform.getFile()!=null){
				//只能上次Jsp的文件
				boolean filetype=bookinfoform.getFileContentType().matches("image/jpeg");
				String filepath=request.getServletContext().getRealPath("/")+"\\Bookimage\\"; ;
				System.out.println(filepath);
				String file_path=filepath+bookinfoform.getIsbn10()+".jpg";
				 //new一个文件对象用来保存图片，默认保存当前工程根目录  
				File imagefile=bookinfoform.getFile();
				FileInputStream inStream = new FileInputStream(imagefile); 
				File imageFile = new File(file_path);  
				OutputStream os = new FileOutputStream(imageFile);  
				// 1K的数据缓冲  
				byte[] bs = new byte[1024];
				
				// 读取到的数据长度  
				int len; 
				 // 开始读取  
				while ((len = inStream.read(bs)) != -1) {  
				       os.write(bs, 0, len);  
				      }  
				 // 完毕，关闭所有链接  
				os.close();  
				inStream.close();  
				int res=dao.count(BookNumber.class,"isbn10",bookinfoform.getIsbn10().trim());
				String imagepath="Bookimage\\"+bookinfoform.getIsbn10()+ ".jpg";
				if(res==0){
					//说明书籍没有下载
					Bookinfo bookinfo=new Bookinfo();
					bookinfo.setAuto(bookinfoform.getAuto());
					bookinfo.setSummary(bookinfoform.getSummary());
					bookinfo.setIsbn10(bookinfoform.getIsbn10());
					bookinfo.setImage(imagepath);
					dao.save(bookinfo);
					
				}else{
					hql ="Update Bookinfo a Set a.image=?,a.auto=?,a.summary=? where a.isbn10=?";
					
					dao.updateByQuery(hql,imagepath,bookinfoform.getAuto(),bookinfoform.getSummary(),
							bookinfoform.getIsbn10());
				}
				
			}else{
				//说明没有图片，首先要判断
				int res=dao.count(Bookinfo.class,"isbn10",bookinfoform.getIsbn10().trim());
				if(res==0){
					//说明书籍没有下载
					Bookinfo bookinfo=new Bookinfo();
					bookinfo.setAuto(bookinfoform.getAuto());
					bookinfo.setSummary(bookinfoform.getSummary());
					bookinfo.setIsbn10(bookinfoform.getIsbn10());
					dao.save(bookinfo);
					
				}else{
					hql ="Update Bookinfo a Set a.auto=?,a.summary=? where a.isbn10=?";

					dao.updateByQuery(hql,bookinfoform.getAuto(),bookinfoform.getSummary(),
							bookinfoform.getIsbn10());
				}
				
			}
			
		}
				
		//更新书籍的数量，需要更新书籍数量的表
//		hql ="Update BookNumber a Set a.number=? where a.isbn=?";					
//		dao.updateByQuery(hql, bookinfoform.getNumber(),bookinfoform.getIsbn10());		
					
		
		return "success";
	}
	@SuppressWarnings("unchecked")
	public List<ImportBookForm> findimportbookinfolistByonlysign(String onlysign) {
		String hql = "From ImportBookInfo s Where s.onlysign = ?";
		//查找信息
		List<ImportBookInfo> list=dao.find(hql,onlysign);
		String bookinfohql="From Bookinfo s Where s.isbn10 = ? ";
		List<Bookinfo> bookinfolist=dao.find(bookinfohql,list.get(0).getIsbn10());
		List<ImportBookForm> resultlist=new ArrayList<ImportBookForm>();
		ImportBookForm importBookForm=null;
		if(bookinfolist==null||bookinfolist.size()==0){
			//说明豆瓣信息不存在
			ImportBookInfo  importBookInfo=list.get(0);
			importBookForm=new ImportBookForm();
			importBookForm.setBookName(importBookInfo.getName());
			importBookForm.setIsbn10(importBookInfo.getIsbn10());
			importBookForm.setOnlysign(importBookInfo.getOnlysign());
			importBookForm.setPosition(importBookInfo.getPosition());
			importBookForm.setId(String.valueOf(importBookInfo.getId()));
			importBookForm.setSource(importBookInfo.getSource());
			resultlist.add(importBookForm);
			return resultlist;
		}
		
		for(int i=0;i<1;i++){
			ImportBookInfo  importBookInfo=list.get(i);
			Bookinfo Bookinfo=bookinfolist.get(i);
			importBookForm=new ImportBookForm();
			importBookForm.setBookName(importBookInfo.getName());
			importBookForm.setIsbn10(importBookInfo.getIsbn10());
			importBookForm.setOnlysign(importBookInfo.getOnlysign());		
			importBookForm.setPosition(importBookInfo.getPosition());
			importBookForm.setId(String.valueOf(importBookInfo.getId()));
			importBookForm.setSource(importBookInfo.getSource());
			importBookForm.setImage(Bookinfo.getImage());
			importBookForm.setClassifi(importBookInfo.getClassifi());
			importBookForm.setAuthor(Bookinfo.getAuto());
			importBookForm.setSummary(Bookinfo.getSummary());
			resultlist.add(importBookForm);
		}
		// TODO Auto-generated method stub
		return resultlist;
	}
	/**
	 * 通过工号查询所借的书籍
	 * @param number
	 * @return
	 */
	public List<BookBorrowinfoForm> getbackbookByuser(String userid) {
		String hql = "From BookBorrowinfo s Where s.borrower = ? and s.state=1";
		//查找信息
		List<BookBorrowinfo> list=dao.find(hql,userid);
		List<BookBorrowinfoForm> formlist=new ArrayList<BookBorrowinfoForm>();
		if(list.size()!=0){
			for(int i=0;i<list.size();i++){
				BookBorrowinfo bookBorrowinfo=list.get(i);
				BookBorrowinfoForm bookBorrowinfoForm=new BookBorrowinfoForm();
				bookBorrowinfoForm.setBorrower(bookBorrowinfo.getBorrower());
				bookBorrowinfoForm.setOnlysign(bookBorrowinfo.getOnlysign());
				hql="From ImportBookInfo a Where a.onlysign = ?";
				ImportBookInfo importBookInfo=(ImportBookInfo) dao.get(hql, bookBorrowinfo.getOnlysign());
				bookBorrowinfoForm.setName(importBookInfo.getName());
				formlist.add(bookBorrowinfoForm);
			}
		}
		
		return formlist;
	}
	/**
	 * 查找全部书籍
	 * @param request
	 * @return
	 */
	public List<TwoBookInfoForm> personsearchBybookname(
			HttpServletRequest request) {
		List<String> paramsList = new ArrayList<String>();
		String hqlWhere = "";
		Object [] params = paramsList.toArray();//组织排序		
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.isbn10", "desc");
		//添加分页
		PageInfo pageinfo=new PageInfo(request);
		List<Bookinfo> list=dao.findCollectionByConditionWithPage(Bookinfo.class,hqlWhere, params, orderby,pageinfo);
		request.setAttribute("page", pageinfo.getPageBean());		
		List<TwoBookInfoForm> newlist=new ArrayList<TwoBookInfoForm>();
		if(list.size()==0){
			return null;
		}
		TwoBookInfoForm twoBookInfoForm=null;
		for(int i=0;i<list.size();i++){
			Bookinfo bookinfo=list.get(i);	
			twoBookInfoForm=new TwoBookInfoForm();
			twoBookInfoForm.setIsbn1(bookinfo.getIsbn10());
			twoBookInfoForm.setImage1(bookinfo.getImage());
			twoBookInfoForm.setName1(bookinfo.getName());		
			newlist.add(twoBookInfoForm);			
		}
		return newlist;
		
	}
	/**
	 * 还书
	 * @param onlysign //书籍的二维码
	 * @param usernumber  借阅人的工号
	 * @return
	 */
	public String updatebackbook(String onlysign, String userid,String position) {
		String hql="From BookBorrowinfo s Where s.state=1 and s.onlysign=? and s.borrower=?  ";
		//判断
		List<BookBorrowinfo> list=dao.find(hql,onlysign,userid);
		if(list.size()==0){
			//说明这本书籍没有被借阅
			return "notborrow";
		}
//		hql ="Update BookBorrowinfo a Set a.state=? where a.onlysign=? and a.borrower=? and a.backposition=? and a.backdate=？";					
//		dao.updateByQuery(hql,"2",onlysign,userid,infolable,new Date());	
		hql ="Update BookBorrowinfo a Set a.state=? , a.backposition=? , a.backdate=? where a.onlysign=? and a.borrower=? ";					
		dao.updateByQuery(hql,"2",position,new Date(),onlysign,userid);
		hql ="Update ImportBookInfo a Set a.position=?  where a.onlysign=? ";					
		dao.updateByQuery(hql,position,onlysign);
		
		return "success";
	}
	public List<BookBorrowinfo> getbackbookByonlysign(String onlysign) {
		String hql = "From BookBorrowinfo s Where s.state=1 and s.onlysign=?";
		//查找信息
		List<BookBorrowinfo> list=dao.find(hql,onlysign);	
		
		
		return list;
	}
	public List<ImportBookInfo> getJudgeByonlysign(String onlysign) {
		String hql = "From ImportBookInfo s Where s.onlysign = ?";
		//查找信息
		List<ImportBookInfo> list=dao.find(hql,onlysign);
//		if(list==null||list.size()==0){
//			return "not_exit";
//		}
		return list;
	}
	/**
	 * 根据onlysign查找书籍的信息
	 * @param onlysign
	 * @return
	 */
	public BookInfoForm finddoubanbookinfoByonlysign(String onlysign) {
		// TODO Auto-generated method stub
		List<ImportBookInfo> list=getJudgeByonlysign(onlysign);
		if(list.size()==0){
			return null;
		}
		Bookinfo bookinfo=finddoubanbookinfo(list.get(0).getIsbn10()).get(0);
		BookInfoForm bookInfoForm=new BookInfoForm();
		bookInfoForm.setBookName(bookinfo.getName());
		bookInfoForm.setOnlysign(onlysign);
		bookInfoForm.setImage(bookinfo.getImage());
		if(bookinfo.getSummary().length()>100){
			bookInfoForm.setSummary(bookinfo.getSummary().substring(0, 100)+"...");
		}else{
			bookInfoForm.setSummary(bookinfo.getSummary());
		}
		bookInfoForm.setAuto(bookinfo.getAuto());
		return bookInfoForm;
	}
	/**
	 * 删除书籍，并且重定向到查询页面
	 * @param onlysign
	 * @param isbn 
	 * @return
	 */
	public String deletebooknumber(String onlysign, String isbn) {
		JSONObject result = new JSONObject();
		//查询这个onlysign如何已经为2则不需进行数量减,为了防止多次进行访问
		String hql = "From ImportBookInfo s Where s.onlysign = ? and s.state=2";
		//查找信息
		List<ImportBookInfo> list=dao.find(hql,onlysign);
		if(list.size()!=0){
			return "deletesuccess";
		}
		//修改导入书籍的状态为2，代表本书已经删除
		hql ="Update ImportBookInfo a Set a.state=? where a.onlysign=?";					
		dao.updateByQuery(hql,"2",onlysign);
		hql ="Update BookNumber a Set a.number=(a.number-1) where a.isbn=?";		
		dao.updateByQuery(hql,isbn);
		
		return "deletesuccess";
	}
	/**
	 * 下载书籍的全部信息
	 * @return
	 */
	public List<BookInfoForm> savedownallbookinfo() {
		String hql = "from ImportBookInfo o where o.state = ? group by isbn10";
		//查找信息
		List<ImportBookInfo> list=dao.find(hql,"0");	
		List<BookInfoForm> booklist=new ArrayList<BookInfoForm>();
		try {
			BookInfoForm bookInfoForm=null;
			for(int i=0;i<list.size();i++){
				ImportBookInfo importBookInfo=list.get(i);
				bookInfoForm=new BookInfoForm();
				String result=savealldoubanbookinfo(importBookInfo.getIsbn10(),importBookInfo.getName());
				if(!result.equals("success")){
					bookInfoForm.setBookName(importBookInfo.getName());
					bookInfoForm.setIsbn10(importBookInfo.getIsbn10());;
					booklist.add(bookInfoForm);
//					System.out.println(result);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return booklist;
	}
	private String savealldoubanbookinfo(String isbn, String bookname) throws Exception {
		//判断书籍是否已经存在
		String hql = "From Bookinfo s Where s.isbn10 = ?";
		//查找信息
		List<Bookinfo> list=dao.find(hql,isbn);
		if(list!=null&&list.size()!=0){
			return "success";
		}	
		String isbnUrl = "http://api.douban.com/v2/book/isbn/:"+isbn; 
		String requestUrl = isbnUrl  + "?apikey=" + apikey; 
		HttpClient client = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(requestUrl);  
		StringBuilder entityStringBuilder=new StringBuilder();
		org.json.JSONObject resultJsonObject=null;
		 // 客户端执行get请求 返回响应实体  					
		try {
			HttpResponse response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			InputStream is=entity.getContent();
			BufferedReader bufferedReader=new BufferedReader
                    (new InputStreamReader(entity.getContent(), "UTF-8"), 8*1024);
            String line=null;
            while ((line=bufferedReader.readLine())!=null) {
                entityStringBuilder.append(line+"/n");
            }
            resultJsonObject=new   org.json.JSONObject(entityStringBuilder.toString());
            System.out.println(resultJsonObject);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			return "error";
//			e.printStackTrace();
		}
		if(resultJsonObject.has("msg")){		
			if(resultJsonObject.getString("msg").equals("book_not_found")){
				//未找到书籍信息				
				return isbn;
			}			
		}
		String summary="";
		if(resultJsonObject.has("summary")){
			summary=resultJsonObject.getString("summary");
			if(summary.length()>=500){
				summary=summary.substring(0, 499);
			}
		}
		String autor1="";
		String	author="";
		if(resultJsonObject.has("author")){
			autor1=resultJsonObject.getString("author");
			if(autor1.contains("\"")){
				author=resultJsonObject.getString("author").split("\"")[1];
				if(author.contains("u2022")){
					author=author.replace("\\u2022", "-");
				}
					
				
			}else{
				author=autor1;
			}	
		}
		String name="";
		if(resultJsonObject.has("title")){
			name=resultJsonObject.getString("title");
		}
		String image="";
		if(resultJsonObject.has("image")){
			image=resultJsonObject.getString("image");
		}
		String price="";
		if(resultJsonObject.has("price")){
			 price=resultJsonObject.getString("price").replace("元", "");
		}
			
		Bookinfo bookinfo=new Bookinfo();
		bookinfo.setAuto(author);
		bookinfo.setImage(image);
		bookinfo.setSummary(summary);
		bookinfo.setName(bookname);
		bookinfo.setPrice(price);
		bookinfo.setIsbn10(isbn);
		dao.save(bookinfo);	
		return "success";
	}

	/**
	 * 将获取到的数据进行序列号
	 * @param request 
	 * @param importBookForm 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getExcelFiledDataList(ImportBookForm importBookForm, HttpServletRequest request) {

		ArrayList<String> filedData = new ArrayList<String>();
		String bookname=importBookForm.getBookName();
		String source=importBookForm.getSource();
		String searchisbn10=importBookForm.getIsbn10();
		String Isisbn10=importBookForm.getIsisbn10();
		String position=importBookForm.getPosition();
		List<String> paramsList = new ArrayList<String>();
		List<ImportBookInfo> returlist=new ArrayList<ImportBookInfo>();
		String hql="From ";
		String hqlWhere = " and state='0' ";			
		if(StringUtils.isNotBlank(bookname)){
			hqlWhere += " and o.name like ?";
			paramsList.add("%" + bookname + "%");			
		}
		if(StringUtils.isNotBlank(source)){
			hqlWhere += " and o.source like ?";
			paramsList.add("%" + source + "%");			
		}
		if(StringUtils.isNotBlank(searchisbn10)){
			hqlWhere += " and o.isbn10 like ?";
			paramsList.add("%" + searchisbn10 + "%");				
		}
		if(StringUtils.isNotBlank(position)){
			if(!position.equals("0")){
				hqlWhere += " and o.position = ?";
				paramsList.add( position );	
			}
						
		}
		
		String grouplist="";
		if(StringUtils.isNotBlank(Isisbn10)&&Isisbn10.equals("0")){
			grouplist="isbn10";
			hqlWhere += " group by isbn10";
		}
		//添加排序条件
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.isbn10", "desc");
		Object [] params = paramsList.toArray();
		returlist=dao.findPage(ImportBookInfo.class, hqlWhere, params, orderby);		
		String[] ddlname_position=this.findddlname(book_position);
		ImportBookInfo importBookInfo=null;
//		"isbn号","书名","图书来源","二维码","所属平台","导入时间","数量","是否下载"
		StringBuffer line=null;
		long time1=System.currentTimeMillis();
		for(int i=0;i<returlist.size();i++){
			//查询书籍的数量
			line=new StringBuffer();
			String old_hql="From BookNumber a Where a.isbn = ?";
			importBookInfo=(ImportBookInfo)returlist.get(i);
			//isbn10
			line.append(importBookInfo.getIsbn10());
			line.append(",");
			line.append(importBookInfo.getName());
			line.append(",");
			line.append(importBookInfo.getSource());
			line.append(",");
			line.append(onlysignweb+importBookInfo.getOnlysign());
			line.append(",");
			line.append(ddlname_position[Integer.valueOf(returlist.get(i).getPosition())-1]);
			line.append(",");
			line.append(importBookInfo.getImportdate());
			line.append(",");
				
			BookNumber bookNumber=(BookNumber) dao.get(old_hql,importBookInfo.getIsbn10());		
			//数量
			line.append(String.valueOf(bookNumber.getNumber()));
			line.append(",");
			//获取唯一编号
			line.append(setfixnumber(importBookInfo.getId()));	
			line.append(",");
			String bookinfohql="From Bookinfo s Where s.isbn10 = ? ";
			
//			List<Bookinfo> bookinfolist=dao.find(bookinfohql,importBookInfo.getIsbn10());
//			if(bookinfolist==null||bookinfolist.size()==0){
//				line.append("未下载");	
//				line.append(",");
//			}else{
//				line.append("已下载");	
//				line.append(",");
//			}
			
			filedData.add(line.toString());
		}
		long time2=System.currentTimeMillis();
		System.out.println(time2-time1);
		return filedData;
	}
	/**
	 * 显示查询推荐的书籍
	 * @return
	 */
	public List<BookInfoForm> searchbooknice() {

		List<BookNice> list=dao.find(BookNice.class);
		List<BookInfoForm> resultlist=new ArrayList<BookInfoForm>();
		for(int i=0;i<list.size();i++){
			BookNice  bookNice=list.get(i);
			
			BookInfoForm bookInfoForm=this.finddoubanbookinfoByIsbn(bookNice.getIsbn10(),null);
			resultlist.add(bookInfoForm);
		}
		
		
		return resultlist;

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
	public List<BookInfoForm> searchbooknice(HttpServletRequest request) {
		List<BookNice> list=dao.find(BookNice.class);
		List<BookInfoForm> resultlist=new ArrayList<BookInfoForm>();
		for(int i=0;i<list.size();i++){
			BookNice  bookNice=list.get(i);
			
			BookInfoForm bookInfoForm=this.finddoubanbookinfoByIsbn(bookNice.getIsbn10(),null);
			resultlist.add(bookInfoForm);
		}
		
		
		return resultlist;
	}
	public List<BookInfoForm> searchbooknice(String bookname,
			HttpServletRequest request) {
		List<BookInfoForm> resultlist=new ArrayList<BookInfoForm>();
		List<String> paramsList = new ArrayList<String>();
		String hqlWhere = "and o.name like ? group by name";
		paramsList.add('%' + bookname + '%');
		Object [] params = paramsList.toArray();	
		//组织排序	
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.isbn10", "desc");
		//添加分页
		PageInfo pageinfo=new PageInfo(request);
		List<ImportBookInfo> list=dao.findCollectionByConditionWithPage(ImportBookInfo.class,hqlWhere, params, orderby,pageinfo);
		for(int i=0;i<list.size();i++){
			ImportBookInfo  importBookInfo=list.get(i);
			
			BookInfoForm bookInfoForm=this.finddoubanbookinfoByIsbn(importBookInfo.getIsbn10(),importBookInfo.getName());
			resultlist.add(bookInfoForm);
		}
		return resultlist;
		
	}
	/**
	 * 根据isbn查询剩余数量,返回结果为所在位置和所在位置的剩余数量，0不要进行显示
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
	/**
	 * 根据isbn查询剩余数量,返回结果为所在位置和所在位置的剩余数量,0本也要进行显示
	 * @param isbn
	 * @return
	 */
	public List<BookRemainForm> findbookremainmountByisbnall(String isbn) {
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
			bookRemainForm.setDdlCode(bookRemainingCount.getDdlCode());

			resultlist.add(bookRemainForm);
		}		
		return resultlist;
	}
	/**
	 * //查询已经借阅书籍的数量
	 * @param id
	 * @return
	 */
	public int getborrownumberBy(int id) {
		String hql ="from BookBorrowinfo a where a.borrower=? and a.state='1'";
		int number=dao.count(hql, String.valueOf(id));
		return number;
	}
	/**
	 * 个人借阅历史查询
	 * @param id 用户的id
	 * @return
	 */
	public List<BookInfoForm> findborrowhistory(int id) {
		//查询用户的借阅历史，不包括已经还书
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String hql = "From BookBorrowinfo s Where s.borrower = ? and s.state='1'";
		List<BookBorrowinfo> list=dao.find(hql, String.valueOf(id));
		if(list==null||list.size()==0){
			//说明没有借阅历史
			return null;
		}
		List<BookInfoForm> resultlist=new ArrayList<BookInfoForm>();
		BookInfoForm bookInfoForm =null;
		for(int i=0;i<list.size();i++){
			//根据onlysign,获取书籍的信息
			BookBorrowinfo bookBorrowinfo =list.get(i);
			bookInfoForm=finddoubanbookinfoByonlysign(bookBorrowinfo.getOnlysign());
			//根据isbn获取借阅时间和借书地点			
			bookInfoForm.setBorrowdate(bartDateFormat.format(bookBorrowinfo.getDate()));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		    Calendar rightNow = Calendar.getInstance();
		    rightNow.setTime(bookBorrowinfo.getDate());
		    rightNow.add(Calendar.MONTH,2);//日期加2个月
		   
		    Date dt1=rightNow.getTime();
		   
		    String reStr = sdf.format(dt1);
		    bookInfoForm.setBackdate(reStr);

			bookInfoForm.setPosition(this.findddlname(book_position)[Integer.valueOf(bookBorrowinfo.getBorrowposition())-1]);
			if(bookInfoForm!=null){
				resultlist.add(bookInfoForm);
			}
		}
		return resultlist;
	}
	@SuppressWarnings("unchecked")
	public List<BookSystemDDlForm> BookSystemDDlPOListToVOList(
			String positon) {
		String hqlWhere = " and o.Keyword = ?";
		Object [] params = {positon};
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.DdlCode", "asc");
		List<Bookdictionary> list = dao.findCollectionByConditionNoPage(Bookdictionary.class,hqlWhere, params, orderby);
		
		List<BookSystemDDlForm> formList = new ArrayList<BookSystemDDlForm>();
		BookSystemDDlForm bookSystemDDlForm = null;
		for(int i=0;list!=null && i<list.size();i++){
			Bookdictionary bookSystemDDl = list.get(i);
			bookSystemDDlForm = new BookSystemDDlForm();
			bookSystemDDlForm.setSeqID(String.valueOf(bookSystemDDl.getSecid()));
			bookSystemDDlForm.setKeyword(bookSystemDDl.getKeyword());
			bookSystemDDlForm.setDdlCode(String.valueOf(bookSystemDDl.getDdlCode()));
			bookSystemDDlForm.setDdlName(bookSystemDDl.getDdlName());
			formList.add(bookSystemDDlForm);
		}
		return formList;

	}
	/**
	 * 判断书籍是否已经被借阅
	 * @param onlysign
	 * @return
	 */
	public String getJudgeborrowByonlysign(String onlysign) {
		String hql = "From BookBorrowinfo s Where s.onlysign = ? and s.state='1'";
		List<BookBorrowinfo> list=dao.find(hql, onlysign);
		if(list==null||list.size()==0){
			return "success";
		}
		return "error";
	}
	/**
	 * 借阅排行榜，查询出借阅次数最多的6本书籍
	 * @return
	 */
	public List<Bookinfo> getborrowRanking() {
		String sql="select s.isbn10,count(s.isbn10) as number from book_borrow_infor a ,book_import_infor s where a.onlysign=s.onlysign group by s.isbn10 order by number desc";
		List<Object[]> list=dao.nativesql(sql);	
		List<Bookinfo> returnlist=new ArrayList<Bookinfo>();
		Bookinfo bookinfo=null;
		for(int i=0;i<6&&i<list.size();i++){
			String isbn10=(String) list.get(i)[0];
			String s=list.get(i)[1].toString();
			int number=Integer.parseInt(s);
			bookinfo=finddoubanbookinfo(isbn10).get(0);
//			if(bookinfo.getSummary().length()>100){
//				bookinfo.setSummary(bookinfo.getSummary().substring(0, 100)+"...");
//			}else{
//				bookinfo.setSummary(bookinfo.getSummary());
//			}
			returnlist.add(bookinfo);
		}
		
	
		return returnlist;
	}
	public String[] getclassifiarray() {
		String[] ddlname_classfi=this.findddlname(book_classfi);
		return ddlname_classfi;
	}
	/**
	 * 根据日期进行借阅排行查询
	 * @param twoBookInfoForm 
	 * @return
	 */
	public JSONObject getdateborrowranking(String starttime,String endtime,String classifi, TwoBookInfoForm twoBookInfoForm) {
		// TODO Auto-generated method stub
		String classifisql="";
		PagerTool pagerTool=new PagerTool();
		int totalRows=0;
		//当前页数
		int pageNum=1;
		if(twoBookInfoForm.getPageSize()!=null){
			pageSize=Integer.valueOf(twoBookInfoForm.getPageSize());
		}		
		if(StringUtils.isNotBlank(twoBookInfoForm.getCurrentPage())){
			pageNum=Integer.valueOf(twoBookInfoForm.getCurrentPage());
		}
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);
		if(StringUtils.isNotBlank(classifi)){
			classifisql="and s.classifi="+classifi;
		}
		int pagestart=(pagerTool.getCurrentPage() - 1) * pagerTool.getPageSize();
		String sql="select s.isbn10,count(s.isbn10) as number from book_borrow_infor a ,book_import_infor s "
				+ "where a.onlysign=s.onlysign "+classifisql
				+ "  and a.borrowdate between '"
				+ starttime+"' and '"
				+endtime+ "'group by s.isbn10 order by number desc limit  "+pagestart+","+pageSize;

		List<Object[]> list=dao.nativesql(sql);	
		String countsql="select s.isbn10,count(s.isbn10) as number from book_borrow_infor a ,book_import_infor s "
				+ "where a.onlysign=s.onlysign "+classifisql
				+ "  and a.borrowdate between '"
				+ starttime+"' and '"
				+endtime+ "'group by s.isbn10 order by number desc"; 
		String allsql="select s.isbn10,count(s.isbn10) as number from book_borrow_infor a ,book_import_infor s "
				+ "where a.onlysign=s.onlysign "+classifisql
				+ "  and a.borrowdate between '"
				+ starttime+"' and '"
				+endtime+ "'group by s.isbn10 order by number desc   ";
		totalRows=dao.nativesql(allsql).size();	
		
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);
		List<BookRankingForm> returnlist=new ArrayList<BookRankingForm>();
		
		BookRankingForm bookRankingForm=null;
		for(int i=0;i<list.size();i++){
			bookRankingForm=new BookRankingForm();
			String isbn10=(String) list.get(i)[0];
			//借阅次数
			String s=list.get(i)[1].toString();
//			int number=Integer.parseInt(s);
			Bookinfo bookinfo=finddoubanbookinfo(isbn10).get(0);
			bookRankingForm.setBookname(bookinfo.getName());
			bookRankingForm.setBorrownumber(s);
			returnlist.add(bookRankingForm);
		}
		JSONObject result = new JSONObject();
		result.put("totalRows", totalRows);
		result.put("totalPages", pagerTool.getTotalPages());
		result.put("bookList", returnlist);
		String[] classifiList=this.getclassifiarray();
		result.put("classifiList", classifiList);
		return result;
	}
		
	
	
	
	
}

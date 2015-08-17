package com.mini.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import sun.misc.BASE64Decoder;

import com.alibaba.fastjson.JSONWriter.Context;
import com.mini.dao.CommonDao;
import com.mini.entity.Activity;
import com.mini.entity.ActivityInit;
import com.mini.entity.ActivitySignup;
import com.mini.entity.ActivityStart;
import com.mini.entity.ActivityStatus;
import com.mini.entity.Bookdictionary;
import com.mini.entity.User;
import com.mini.entity.UserImage;
import com.mini.form.ActivityDictionaryForm;
import com.mini.form.ActivityInitForm;
import com.mini.form.ActivityStartForm;
import com.mini.form.ActivityStatusForm;
import com.mini.form.ActivityWindForm;
import com.mini.util.PageModel;
import com.mini.util.PagerTool;
import com.opensymphony.xwork2.ActionContext;


public class ActivityWindService {
	int pageSize=9;
	private boolean hasPrevious;
	private boolean hasNext;
	private String activityname="活动名称";
	private String activitystate="活动阶段";
	private final String titleflag="1";//主题
	private final String contentflag="2";//文本
	private final String imageflag="3";//图像
	private final int activitystatus=1;//图像
	private CommonDao dao;

	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	/**
	 * 获取数据字典中的活动
	 * @param activityWindForm
	 * @return
	 */
	public JSONObject getActivityDictionary(ActivityWindForm activityWindForm) {
		String[] activitydictionary=findddlname(activityname);
		String[] activitystatedictionary=findddlname(activitystate);
		//获取所有的活动
		JSONObject result = new JSONObject();	
		ArrayList<ActivityDictionaryForm> returnlist=new ArrayList<ActivityDictionaryForm>();
		ActivityDictionaryForm activityDictionaryForm=null;
		if(activityWindForm.getActivityStatus()==null){
			for(int i=0;i<activitydictionary.length;i++){
				activityDictionaryForm=new ActivityDictionaryForm();
				activityDictionaryForm.setActivityid(String.valueOf(i+1));
				activityDictionaryForm.setActivityName(activitydictionary[i]);
				returnlist.add(activityDictionaryForm);
			}
			result.put("activitydictionary",returnlist);
			return result;
		}
		int activityState=Integer.valueOf(activityWindForm.getActivityStatus());

		
		for(int i=0;i<activitydictionary.length;i++){
			//查询			
			String hql="From ActivityStatus where activityid=?";
			ActivityStatus activityStatus=(ActivityStatus) dao.get(hql, i+1);
			if(activityStatus==null){
				//设置为1
				ActivityStatus saveactivityStatus=new ActivityStatus();
				saveactivityStatus.setActivityid(i+1);
				saveactivityStatus.setStatus(activitystatus);
				saveactivityStatus.setActivityname(activitystatedictionary[activitystatus-1]);
				dao.save(saveactivityStatus);
				if(activitystatus==activityState){
					activityDictionaryForm=new ActivityDictionaryForm();
					activityDictionaryForm.setActivityid(String.valueOf(i+1));
					activityDictionaryForm.setActivityName(activitydictionary[i]);
					returnlist.add(activityDictionaryForm);
				}
				
				continue;
			}
			//说明是要获取的状态
			if(activityStatus.getStatus()==activityState){
				activityDictionaryForm=new ActivityDictionaryForm();
				activityDictionaryForm.setActivityid(String.valueOf(i+1));
				activityDictionaryForm.setActivityName(activitydictionary[i]);
				returnlist.add(activityDictionaryForm);
			}
		}
		
		result.put("activitydictionary",returnlist);
		return result;
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
			result[i]=bookdictionary.getDdlName().trim();
		}

		return result;
	}
	
	public String saveActivityStart(ActivityWindForm activityWindForm) throws IOException {
		//查询是否已经存在
		if(activityWindForm.getActivityName()==null){
			return "error";
		}
		String hql="From ActivityStart where activityid=?  ";
		List<ActivityStart> activitylist=dao.find(hql, Integer.valueOf(activityWindForm.getActivityName()));
		if(activitylist!=null&&activitylist.size()!=0){
			//说明是数据已经存在，则进行更新
			for(int i=0;i<activitylist.size();i++){
				ActivityStart activityStart=activitylist.get(i);
				dao.delete(ActivityStart.class, activityStart.getId());
			}
			
			
		}
		//说明数据不存在，则进行保存
		String activityTitle=activityWindForm.getActivityTitle();
		ActivityStart activityStarttitle=new ActivityStart();
		activityStarttitle.setFlag(titleflag);
		activityStarttitle.setContent(activityTitle);
		
		activityStarttitle.setActivityid(Integer.valueOf(activityWindForm.getActivityName()));
		
		dao.save(activityStarttitle);
		//保存内容
		ActivityStart activityStartcontent=null;
		String[] activityContent=activityWindForm.getActivityContent();
		if(activityContent!=null&&activityContent.length!=0){
			for(int i=0;i<activityContent.length;i++){
				activityStartcontent=new ActivityStart();
				activityStartcontent.setFlag(contentflag);
				activityStartcontent.setContent(activityContent[i]);				
				activityStartcontent.setActivityid(Integer.valueOf(activityWindForm.getActivityName()));				
				dao.save(activityStartcontent);
			}
		}
		//保存图片
		ActivityStart activityStartimage=null;
		String[] imagename=activityWindForm.getImagename();
		String[] imagedata=activityWindForm.getImagedata();
		String path="c:/image/activitystart/";
		if(imagename==null){
			//说明不存在图片
			return "success";
		}
		if((imagename.length!=imagedata.length)){
			return "error";
		}
		//判断名字是否和数据中的相同		
		if(imagedata!=null&&imagedata.length!=0){
			for(int i=0;i<imagename.length;i++){				
				activityStartimage=new ActivityStart();
				activityStartimage.setFlag(imageflag);
				//image的保存位置				
				File dir = new File(path);
		        // 创建文件夹
				if (!dir.exists()) {
					dir.mkdirs();
				}
	        	String prefix=imagename[i].substring(imagename[i].lastIndexOf(".")+1);
	        	String filename=activityWindForm.getActivityName()+UUID.randomUUID()+"."+prefix;
	        	File newfile = new File(path+filename);	        	
	        	if(!newfile.exists()){
	        		newfile.createNewFile();
	        	}	        	
				String imageData=imagedata[i];
	        	imageData = imageData.substring(30);
	        	imageData = URLDecoder.decode(imageData,"UTF-8");
	        	byte[] data = decode(imageData);
	    		// 写入到文件
	        	FileOutputStream  fo = new FileOutputStream(newfile);	 
	        	fo.write(data);
	        	fo.flush();
	        	fo.close();		        	
	        	filename="/activitystart/"+filename;
	        	activityStartimage.setContent(filename);	        	
	        	activityStartimage.setActivityid(Integer.valueOf(activityWindForm.getActivityName()));				
	        	dao.save(activityStartimage);
			 }
		}			
		return "success";
	}
	private byte[] decode(String imageData) throws IOException{
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] data = decoder.decodeBuffer(imageData);
		for(int i=0;i<data.length;++i)
	    {
	        if(data[i]<0)
	        {
	        	//调整异常数据
	            data[i]+=256;
	        }
	    }
		//
		return data;
	}
	public JSONObject getActivityStart(ActivityWindForm activityWindForm) {
		if(activityWindForm.getActivityName()==null){
			return null;
		}
		String hql="From ActivityStart where activityid=?  ";
		List<ActivityStart> activitylist=dao.find(hql, Integer.valueOf(activityWindForm.getActivityName()));
		if(activitylist==null||activitylist.size()==0){
			return null;
		}
		ActivityStartForm activityStartForm=new ActivityStartForm();		
		List<String> contentlist=new ArrayList<String>();
		List<String> iamgelist=new ArrayList<String>();
		for(int i=0;i<activitylist.size();i++){
			ActivityStart activityStart=activitylist.get(i);
			if(activityStart.getFlag().equals(titleflag)){
				activityStartForm.setActivityTitle(activityStart.getContent());
			}
			if(activityStart.getFlag().equals(contentflag)){
				contentlist.add(activityStart.getContent());				
			}
			if(activityStart.getFlag().equals(imageflag)){
				iamgelist.add(activityStart.getContent());				
			}
			
		}
 
		activityStartForm.setActivityContent(contentlist.toArray(new String[contentlist.size()]));
		activityStartForm.setImagename( iamgelist.toArray(new String[iamgelist.size()]));
		JSONObject result=new JSONObject();
		result.put("activitylist", activityStartForm);
		return result;
	}
	/**
	 * 保存活动的初始化信息
	 * @param activityWindForm
	 * @return
	 */
	public String saveActivityInit(ActivityWindForm activityWindForm) {
		if(activityWindForm.getActivityName()==null){
			return "error";
		}
		String hql="From ActivityInit where activityid=?  ";
		List<ActivityInit> activitylist=dao.find(hql, Integer.valueOf(activityWindForm.getActivityName()));
		if(activitylist!=null&&activitylist.size()!=0){
			//说明是数据已经存在，则进行更新
			for(int i=0;i<activitylist.size();i++){
				ActivityInit activityInit=activitylist.get(i);
				dao.delete(ActivityInit.class, activityInit.getId());
			}			
			
		}
		String[] itemName= activityWindForm.getItemName();
		String[] itemFlag=activityWindForm.getItemFlag();
		
		if((itemName==null||itemFlag==null)||(itemName.length!=itemFlag.length)){
			return "error";
		}		
		for(int i=0;i<itemName.length;i++){
			ActivityInit activityInit=new ActivityInit();
			activityInit.setActivityid(Integer.valueOf(activityWindForm.getActivityName()));
			activityInit.setFlag(itemFlag[i]);
			activityInit.setContent(itemName[i]);
			dao.save(activityInit);	
		}
		return "success";
	}
	/**
	 * 获取活动的初始化信息
	 * @param activityWindForm
	 * @param user 
	 * @return
	 */
	public JSONObject getActivityInit(ActivityWindForm activityWindForm, User user) {
		if(activityWindForm.getActivityName()==null){
			return null;
		}
		String hql="From ActivityInit where activityid=?  ";
		List<ActivityInit> activitylist=dao.find(hql, Integer.valueOf(activityWindForm.getActivityName()));
		if(activitylist==null||activitylist.size()==0){
			return null;
		}
		ActivityInitForm activityInitForm=new ActivityInitForm();		
		List<String> itemNamelist=new ArrayList<String>();
		List<String> itemFlaglist=new ArrayList<String>();
		for(int i=0;i<activitylist.size();i++){
			ActivityInit aactivityInit=activitylist.get(i);
			itemNamelist.add(aactivityInit.getContent());
			itemFlaglist.add(aactivityInit.getFlag());				
		}
		activityInitForm.setItemName(itemNamelist.toArray(new String[itemNamelist.size()]));
		activityInitForm.setItemFlag(itemFlaglist.toArray(new String[itemFlaglist.size()]));
		JSONObject result=new JSONObject();
		result.put("aactivityInit", activityInitForm);
		result.put("username", user.getName());
		result.put("usernumber", user.getNumber());
		return result;
	}
	/**
	 * 保存活动的报名信息
	 * @param activityWindForm
	 * @param user 
	 * @return
	 * @throws Exception 
	 */
	public String saveActivitySignup(ActivityWindForm activityWindForm, User user) throws Exception {
		if(activityWindForm.getActivityName()==null){
			return null;
		}
		String[] textarray=activityWindForm.getText();
		File[] filearray=activityWindForm.getFile();
		String[] filenamearray=activityWindForm.getFileFileName();
		String hql="From ActivityInit where activityid=? order by id asc ";
		List<ActivityInit> activitylist=dao.find(hql, Integer.valueOf(activityWindForm.getActivityName()));
		if(activitylist==null||activitylist.size()==0){
			return null;
		}
		if(activitylist.size()!=(textarray.length+filearray.length)){
			return "error";
		}
		//先存储文本
		int j=1,k=1;
		for(int i=0;i<activitylist.size();i++){
			ActivityInit activityInit=activitylist.get(i);
			//说明是文本
			if(activityInit.getFlag().equals(contentflag)){
				ActivitySignup activitySignup=new ActivitySignup();
				activitySignup.setActivityid(Integer.valueOf(activityWindForm.getActivityName()));
				activitySignup.setContent(textarray[j]);
				activitySignup.setUserid(user.getId());
				activitySignup.setFlag(String.valueOf(j++));
				dao.save(activitySignup);
				continue;
			}
			//说明是文件
			if(activityInit.getFlag().equals(imageflag)){
				ActivitySignup activitySignup=new ActivitySignup();
				activitySignup.setActivityid(Integer.valueOf(activityWindForm.getActivityName()));
				//保存文本路径
				String path="c:/image/initfile/";
				File dir = new File(path);
	        	// 创建文件夹
	        	if (!dir.exists()) {
	        		dir.mkdirs();
	        	}
	        	String prefix=filenamearray[k].substring(filenamearray[k].lastIndexOf(".")+1);
	        	String filename=user.getNumber()+UUID.randomUUID()+"."+prefix;
	        	File newfile = new File(path+filename);
	        	if(!newfile.exists()){
	        		newfile.createNewFile();
        	    }
	        	File file=filearray[k];
	        	FileInputStream  fi = new FileInputStream(file);
	        	FileOutputStream  fo = new FileOutputStream(newfile);
	        	FileChannel  in = fi.getChannel();//得到对应的文件通道	        	
	        	FileChannel  out = fo.getChannel();//得到对应的文件通道
	            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
	            fi.close();
                in.close();
                fo.close();
                out.close();
				activitySignup.setContent("/initfile/"+filename);
				activitySignup.setUserid(user.getId());
				activitySignup.setFlag(String.valueOf(k++));
				dao.save(activitySignup);
			}
		}	
		
		return "success";
	}
	public JSONObject getActivityStatusDictionary(ActivityWindForm activityWindForm) {
		String[] activitydictionary=findddlname(activitystate);
		JSONObject result=new JSONObject();
		result.put("activitystatus", activitydictionary);
		return result;
	}
	public JSONObject getActivityStatus(ActivityWindForm activityWindForm) {
		if(activityWindForm.getActivityName()==null){
			return null;
		}
		String hql="From ActivityStatus where activityid=?";
		ActivityStatus activityStatus=(ActivityStatus) dao.get(hql, Integer.valueOf(activityWindForm.getActivityName()));
		JSONObject result=new JSONObject();
		if(activityStatus==null){
			result.put("activityStatus", "1");
		}else{
			result.put("activityStatus", activityStatus.getStatus());
		}
		
		
		return result;
	}
	public JSONObject getallActivityStatus(ActivityWindForm activityWindForm) {
		//首先要先将所有的活动进行初始化
		String[] activitydictionary=findddlname(activityname);
		String[] activitystatedictionary=findddlname(activitystate);
		String resultinit=this.initactivityStatus(activitydictionary,activitystatedictionary);
		if(!resultinit.equals("success")){
			return null;
		}
		
		PagerTool pagerTool=new PagerTool();
		//当前页数
		int pageNum=1;
		int totalRows=0;
		if(activityWindForm.getPageSize()==null){
			pageSize=9;
		}else{
			pageSize=Integer.valueOf(activityWindForm.getPageSize());
		}		
		if(StringUtils.isNotBlank(activityWindForm.getCurrentPage())){
			pageNum=Integer.valueOf(activityWindForm.getCurrentPage());
		}
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);
		String hqlWhere = "  ";
		//组织排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.activityid", "asc");
		List<String> paramsList = new ArrayList<String>();		
		//查询条件，
		if(StringUtils.isNotBlank(activityWindForm.getActivityName())){
			//获取数据字典中activityname的位置
//			String position=getpositonnumber(activitydictionary,activityWindForm.getActivityName());
			hqlWhere += " and o.activityname like ?";
			paramsList.add("%"+activityWindForm.getActivityName()+"%");			
		}
		if(StringUtils.isNotBlank(activityWindForm.getActivityStatus())&&!(activityWindForm.getActivityStatus().equals("0"))){
			hqlWhere += " and o.status = cast( ? as integer ) ";
			paramsList.add(activityWindForm.getActivityStatus());	
			
		}		
		Object [] params = paramsList.toArray();
		@SuppressWarnings("unchecked")
		List<ActivityStatus> activityStatuslist=dao.findPage(ActivityStatus.class, hqlWhere, params, orderby, pagerTool);
		totalRows=pagerTool.getTotalRows();
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);	
		if(activityStatuslist==null||activityStatuslist.size()==0){
			JSONObject result = new JSONObject();	
			result.put("totalRows", totalRows);
			result.put("totalPages", pagerTool.getTotalPages());
			result.put("activitydictionary","");
			return result;
		}		
		
		List<ActivityStatusForm> returnlist=new ArrayList<ActivityStatusForm>();
		ActivityStatusForm activityStatusForm=null;
		for(int i=0;i<activityStatuslist.size();i++){
			ActivityStatus activityStatus=activityStatuslist.get(i);
			activityStatusForm=new ActivityStatusForm();
			activityStatusForm.setActivityid(String.valueOf(activityStatus.getActivityid()));
			activityStatusForm.setActivityname(activitydictionary[activityStatus.getActivityid()-1]);
			activityStatusForm.setStatus(String.valueOf(activityStatus.getStatus()));
			activityStatusForm.setStatusName(activitystatedictionary[activityStatus.getStatus()-1]);
			returnlist.add(activityStatusForm);
		}
		
		//获取所有的活动
		JSONObject result = new JSONObject();	
		result.put("totalRows", totalRows);
		result.put("totalPages", pagerTool.getTotalPages());
		result.put("activitydictionary",returnlist);
		return result;
	}
	/**
	 * 进行活动的初始化
	 * @param activitydictionary 
	 * @param activitystatedictionary 
	 * @return
	 */
	private String initactivityStatus(String[] activitydictionary, String[] activitystatedictionary) {
		
		List<ActivityStatus> activityStatuslist=dao.find(ActivityStatus.class);
		if(activityStatuslist.size()==activitydictionary.length){			
			return "success";
		}
		for(int i=0;i<activitydictionary.length;i++){
			//查询			
			String hql="From ActivityStatus where activityid=?";
			ActivityStatus activityStatus=(ActivityStatus) dao.get(hql, i+1);
			if(activityStatus==null){
				//设置为1
				ActivityStatus saveactivityStatus=new ActivityStatus();
				saveactivityStatus.setActivityid(i+1);
				saveactivityStatus.setStatus(activitystatus);
				saveactivityStatus.setActivityname(activitydictionary[i]);
				dao.save(saveactivityStatus);
				continue;
			}		
		}		
		return "success";
	}
	/**
	 * 获取数据字典项所在的位置
	 * @param col
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getpositonnumber(String[] col,
			String value) {
		for(int i=0;i<col.length;i++){
			if(col[i].trim().equals(value)){
				return String.valueOf(i+1);
			}
		}
		return "error";
	}
	public String updateActivityStatus(ActivityWindForm activityWindForm) {
		if(StringUtils.isBlank(activityWindForm.getActivityid())||StringUtils.isBlank(activityWindForm.getActivityStatus())){
			return null;
		}
		String hql="update ActivityStatus a set a.status=? where a.activityid=?";
		dao.updateByQuery(hql, Integer.valueOf(activityWindForm.getActivityStatus()),Integer.valueOf(activityWindForm.getActivityid()));
		return "success";
	}
	
	public String  getUserActivityStatus(ActivityWindForm activityWindForm, User user) {
		if(activityWindForm.getActivityName()==null){
			return null;
		}
		String hql="From ActivitySignup a where a.activityid=? and a.userid=?";
		ActivitySignup activitySignup=(ActivitySignup) dao.get(hql, Integer.valueOf(activityWindForm.getActivityName()),user.getId());
		if(activitySignup==null){
			return "no";
		}else{
			return "yes";
		}
	}
	
	
}

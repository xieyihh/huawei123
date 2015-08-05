package com.mini.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.mini.dao.CommonDao;
import com.mini.entity.BookNumber;
import com.mini.entity.Bookdictionary;
import com.mini.entity.Bookinfo;
import com.mini.entity.ImportBookInfo;
import com.mini.entity.PhysicalEditRedate;
import com.mini.entity.Physicalexam;
import com.mini.entity.Physicalinit;
import com.mini.entity.Physicalrelatives;
import com.mini.entity.Physicalreview;
import com.mini.entity.User;
import com.mini.form.PhysicalExpectdateForm;
import com.mini.form.PhysicalRelaviteForm;
import com.mini.form.PhysicalexamForm;
import com.mini.form.PhysicalinitForm;
import com.mini.util.InitString;
import com.mini.util.PagerTool;



public class PhysicalexamService {
	private  int pageSize=9;
	private boolean hasPrevious;
	private boolean hasNext;
	final private String physical_plan="体检批次"; 
	final private String physical_position="体检地点"; 
	final private String physical_state="体检状态"; 
	
	private CommonDao dao;

	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	public JSONObject searchphysicalexam(PhysicalexamForm physicalexamform) throws ParseException {
		//如果体检过期的就要进行设置为体检过期
		PagerTool pagerTool=new PagerTool();
		//对所有的未体检的进行判断，
		//删除所有数据，
		
		this.ChangePhysicalState();
		//当前页数
		int pageNum=1;
		int totalRows=0;
		pageSize=Integer.valueOf(physicalexamform.getPageSize());
		if(StringUtils.isNotBlank(physicalexamform.getCurrentPage())){
			pageNum=Integer.valueOf(physicalexamform.getCurrentPage());
		}
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);
		String hqlWhere = " and state='0' ";
		//组织排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.physicalimportdate", "desc");
		List<String> paramsList = new ArrayList<String>();
		
		//查询条件，工号，姓名，体检号，预约体检时间，体检时间，体检地点，体检状态，体检批次，是否有家属
		if(StringUtils.isNotBlank(physicalexamform.getUsernumber())){
			hqlWhere += " and o.usernumber like ?";
			paramsList.add("%" + physicalexamform.getUsernumber() + "%");
			
		}
		if(StringUtils.isNotBlank(physicalexamform.getUsername())){
			hqlWhere += " and o.username like ?";
			paramsList.add("%" + physicalexamform.getUsername() + "%");
			
		}
		
		//预约体检时间
		if(StringUtils.isNotBlank(physicalexamform.getPhysicalreservedate())){
			 
			hqlWhere += " and DATE_FORMAT( o.physicalreservedate,'%Y-%m-%d')=? ";
			paramsList.add(physicalexamform.getPhysicalreservedate());
			
		}
		//体检时间
		if(StringUtils.isNotBlank(physicalexamform.getPhysicaldate())){
			
			hqlWhere += " and DATE_FORMAT( o.physicaldate,'%Y-%m-%d')=? ";
			
			paramsList.add(physicalexamform.getPhysicaldate());
			
		}
		//体检状态
		if(StringUtils.isNotBlank(physicalexamform.getPhysicalstate())&&(!physicalexamform.getPhysicalstate().equals("0"))){
			hqlWhere += " and o.physicalstate=?";			
			paramsList.add( physicalexamform.getPhysicalstate());
			
		}
		//体检地点
		if(StringUtils.isNotBlank(physicalexamform.getPhysicalposition())&&(!physicalexamform.getPhysicalposition().equals("0"))){
			hqlWhere += " and o.physicalposition=?";			
			paramsList.add( physicalexamform.getPhysicalposition());			
		}
		//体检批次,为0查询所有
		if(StringUtils.isNotBlank(physicalexamform.getPhysicalplan())&&(!physicalexamform.getPhysicalplan().equals("0"))){
			hqlWhere += " and o.physicalplan=?";			
			paramsList.add( physicalexamform.getPhysicalplan());			
		}
		//是否有家属，0代表没有家属，1代表有家属
		if(StringUtils.isNotBlank(physicalexamform.getHasRelatives())){
			hqlWhere += " and o.hasRelatives=?";			
			paramsList.add( physicalexamform.getHasRelatives());			
		}
		Object [] params = paramsList.toArray();
		@SuppressWarnings("unchecked")
		List<Physicalexam> physicalexamlist=dao.findPage(Physicalexam.class, hqlWhere, params, orderby, pagerTool);
		List<PhysicalexamForm> returnlist=new ArrayList<PhysicalexamForm>();
		String[] physical_planarray=findddlname(physical_plan);
		String[] physical_statearray=findddlname(physical_state);
		String[] physical_positionarray=findddlname(physical_position);
		
		
		PhysicalexamForm physicalexamForm=null;
		for(int i=0;i<physicalexamlist.size();i++){
			Physicalexam physicalexam=physicalexamlist.get(i);
			physicalexamForm=this.findPhysicalexamPOTOVO(physicalexam,physical_planarray,physical_statearray,physical_positionarray);
			returnlist.add(physicalexamForm);
		}
		totalRows=pagerTool.getTotalRows();
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);
		JSONObject result = new JSONObject();		
		result.put("totalRows", totalRows);
		result.put("totalPages", pagerTool.getTotalPages());
		result.put("physicallist", returnlist);
		result.put("physical_plan",physical_planarray );
		result.put("physical_state", physical_statearray);
		result.put("physical_position", physical_positionarray);
		return result;
	}
	/**
	 * 将所有的未体检符合条件的变为体检过期
	 * @throws ParseException 
	 */
	private void ChangePhysicalState() throws ParseException {
		//判断当前日期和体检预约日期进行比较，看哪个比较大
		String hql="From Physicalexam o where o.state='0' and o.physicalstate=? ";
		List<Physicalexam> physicalexamlist=dao.find(hql,"3");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	
		Date dateTime2 = dateFormat.parse(dateFormat.format(new Date()));
		for(int i=0;i<physicalexamlist.size();i++){
			Physicalexam physicalexam=physicalexamlist.get(i);
			Date dateTime1 = dateFormat.parse(dateFormat.format(physicalexam.getPhysicalreservedate()));
			int jx = dateTime1.compareTo(dateTime2); 
			if(jx<0){
				//设置为体检过期
				if(physicalexam.getPhysicalstate().equals("3")||physicalexam.getPhysicalstate().equals("2")){
					//未体检才能变为条件过期					
					physicalexam.setPhysicalstate("2");;
				}	

			}
			
		}
		
	}
	/**
	 * 体检信息的PO对象变为VO对象
	 * @param physicalexam
	 * @param physical_statearray2 
	 * @param physical_statearray 
	 * @param physical_planarray 
	 * @return
	 * @throws ParseException 
	 */
	private PhysicalexamForm findPhysicalexamPOTOVO(Physicalexam physicalexam, String[] physical_plan, String[] physical_state, String[] physical_position) throws ParseException {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		PhysicalexamForm physicalexamForm=new PhysicalexamForm();
		physicalexamForm.setUsername(physicalexam.getUsername());
		physicalexamForm.setUsernumber(physicalexam.getUsernumber());
		
		physicalexamForm.setPhysicalplan(physical_plan[Integer.valueOf(physicalexam.getPhysicalplan())-1]);
		
		//判断当前日期和体检预约日期进行比较，看哪个比较大
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	
		Date dateTime1 = dateFormat.parse(dateFormat.format(physicalexam.getPhysicalreservedate()));
		Date dateTime2 = dateFormat.parse(dateFormat.format(new Date()));
		int i = dateTime1.compareTo(dateTime2); 
		if(i<0){
			//设置为体检过期
			if(physicalexam.getPhysicalstate().equals("3")||physicalexam.getPhysicalstate().equals("2")){
				//未体检才能变为条件过期
				physicalexamForm.setPhysicalstate(physical_state[1]);
				//physicalexam.setPhysicalstate("2");;
			}else{
				physicalexamForm.setPhysicalstate(physical_state[Integer.valueOf(physicalexam.getPhysicalstate())-1]);
			}		

		}else{
			//体检未过期
			physicalexamForm.setPhysicalstate(physical_state[Integer.valueOf(physicalexam.getPhysicalstate())-1]);
		}
		
		physicalexamForm.setPhysicalposition(physical_position[Integer.valueOf(physicalexam.getPhysicalposition())-1]);
		
		physicalexamForm.setPhysicalimportdate(bartDateFormat.format(physicalexam.getPhysicalimportdate()));
		physicalexamForm.setPhysicalreservedate(bartDateFormat.format(physicalexam.getPhysicalreservedate()));
		physicalexamForm.setId(String.valueOf(physicalexam.getId()));
		
		if(physicalexam.getPhysicaldate()!=null)
			physicalexamForm.setPhysicaldate(bartDateFormat.format(physicalexam.getPhysicaldate()));
		//判断是否有亲属
		if(physicalexam.getHasRelatives().equals("0")){
			//没有亲属
			physicalexamForm.setHasRelatives("无");
		}
		if(physicalexam.getHasRelatives().equals("1")){
			//没有亲属
			physicalexamForm.setHasRelatives("有");
		}
		
		return physicalexamForm;
	}
	/**
	 * 获取数据字典项所在的位置
	 * @param col
	 * @param value
	 * @return
	 */
	private String getpositonnumber(String[] col,
			String value) {
		for(int i=0;i<col.length;i++){
			if(col[i].trim().equals(value)){
				return String.valueOf(i+1);
			}
		}
		return "error";
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
	/**
	 * 获取体检批次
	 * @return
	 */
	public JSONObject getphysicalplan() {
		String[] physical_planarray=findddlname(physical_plan);
		JSONObject result = new JSONObject();		

		result.put("physical_plan",physical_planarray );
		return result;
	}
	/**
	 * 保存导入的体检信息
	 * @param excel
	 * @return
	 * @throws Exception 
	 * @throws FileNotFoundException 
	 */
	public String savephysicalexam(File excel, PhysicalexamForm physicalexamForm,List<String> errline) throws FileNotFoundException, Exception {
		//删除符合要求的所有数据
	
		BufferedReader filedata = new BufferedReader(new InputStreamReader(new FileInputStream(excel)));
		String hql="Update Physicalexam a Set a.state=? where a.physicalplan=? ";
		dao.updateByQuery(hql, "2",physicalexamForm.getPhysicalplan());
		String line = "";
		if((line=filedata.readLine())==null){
			return "firstnotmatch";	
		}
		if(!(line.trim().equals(InitString.PHYSICAL_information_head)||line.trim().equals(InitString.PHYSICAL_information_head+","))){
			return "firstnotmatch";
		}
		String[] physical_positionarray=findddlname(physical_position);
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Physicalexam physicalexam=null;
        while ((line = filedata.readLine()) != null) { 
        	//读取数据,进行分割
        	String[] data=line.split(",");
        	if((data.length<4)){
        		//数据长度不对，导入失败的信息
        		errline.add(line);
        		break;        		
        	}
        	String usernumber=data[0].trim();
        	if(StringUtils.isBlank(usernumber)){
        		//数据信息有错误
        		errline.add(line);
        		break;
        	}
        	//判断工号是否为8位
        	if(usernumber.length()!=8){
        		//数据信息有错误
        		errline.add(line);
        		break;
        	}
        	String username=data[1].trim();
        	if(StringUtils.isBlank(username)){
        		//数据信息有错误
        		errline.add(line);
        		break;
        	}
        	
        	String physicalposition=data[2].trim();
        	if(StringUtils.isBlank(physicalposition)){
        		//数据信息有错误
        		errline.add(line);
        		break;
        	}
        	String positionnumber=this.getpositonnumber(physical_positionarray, physicalposition);
        	if(positionnumber.equals("error")){
        		//数据信息有错误
        		errline.add(line);
        		break;       		
        	}
        
        	String physicalreservedates=data[3].trim();
        	if(StringUtils.isBlank(physicalreservedates)){
        		//数据信息有错误
        		errline.add(line);
        		break; 
        	}
        	if(physicalreservedates.contains("/")){
        		physicalreservedates=physicalreservedates.replace("/", "-"); 
        	}
        	Date physicalreservedate= bartDateFormat.parse(physicalreservedates.toString());
        	//保存信息
			physicalexam=new Physicalexam();
			physicalexam.setUsername(username);
			physicalexam.setUsernumber(usernumber);
			physicalexam.setPhysicalposition(positionnumber);
			physicalexam.setPhysicalreservedate(physicalreservedate);
			physicalexam.setHasRelatives(InitString.Relatives_no);
			physicalexam.setPhysicalplan(physicalexamForm.getPhysicalplan());
			//未体检
			physicalexam.setPhysicalstate(InitString.physicalstate_noexam);
			physicalexam.setPhysicalimportdate(new Date());
			physicalexam.setState(InitString.physicalstate_init);
			dao.save(physicalexam);        	
        }		
		return "success";				
	}
	/**
	 * 修改体检预约时间
	 * @param physicalexamForm
	 * @return
	 */
	public String savephysicalreservedate(PhysicalexamForm physicalexamForm) {
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
		String hql="Update Physicalexam a Set a.physicalreservedate=? where a.id=? ";
		try {
			dao.updateByQuery(hql, dateformat.parse(physicalexamForm.getPhysicalreservedate()),Integer.valueOf(physicalexamForm.getId()));
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return "success";
	}
	/**
	 * 删除用户的体检信息，将状态变为2
	 * @param physicalexamForm
	 * @return
	 */
	public String deletephysicalreservedate(PhysicalexamForm physicalexamForm) {
		String hql="Update Physicalexam a Set a.state=? where a.id=? ";
		dao.updateByQuery(hql, "2",Integer.valueOf(physicalexamForm.getId()));
		return "success";
	}
	/**
	 * 医院输入工号，返回员工姓名，体检批次、工号
	 * @throws ParseException 
	 */
	public JSONObject getUsernameByusernumber(PhysicalexamForm physicalexamForm) throws ParseException {
		Physicalinit physicalinit=(com.mini.entity.Physicalinit) dao.get(Physicalinit.class, 1);
		physicalexamForm.setPhysicalplan(physicalinit.getPhysicalplan());
		String hql="From Physicalexam a Where a.usernumber = ? and state='0' and a.physicalplan=? ";
		Physicalexam physicalexam=(Physicalexam) dao.get(hql,physicalexamForm.getUsernumber(),physicalexamForm.getPhysicalplan());
		if(physicalexam==null){
			//用户不存在
			return null;
		}
	
		String[] physical_planarray=findddlname(physical_plan);
		String[] physical_statearray=findddlname(physical_state);
		String[] physical_positionarray=findddlname(physical_position);	
		
		PhysicalexamForm	returnForm=this.findPhysicalexamPOTOVO(physicalexam,physical_planarray,physical_statearray,physical_positionarray);
		JSONObject result = new JSONObject();		

		result.put("physicalexamForm",returnForm );
		return result;
	}
	/**
	 * 修改用户的正式体检时间
	 * @param physicalexamForm
	 * @return
	 */
	public String savePhysicaldate(PhysicalexamForm physicalexamForm) {
		Physicalinit physicalinit=(com.mini.entity.Physicalinit) dao.get(Physicalinit.class, 1);
		physicalexamForm.setPhysicalplan(physicalinit.getPhysicalplan());
		String hashql="From Physicalexam a where a.id=?";
		Physicalexam physicalexam=(Physicalexam) dao.get(hashql, Integer.valueOf(physicalexamForm.getId()));
		if(physicalexam.getPhysicaldate()!=null){
			return "hasimport";
		}
		String physicalstate="1";//已体检
		String hql="Update Physicalexam a Set a.physicaldate=? , a.physicalstate=? where a.id=?  ";
		dao.updateByQuery(hql,new Date(),physicalstate,Integer.valueOf(physicalexamForm.getId()));
		return "success";
	}
	/**
	 * 批量导入用户用户修改的体检时间
	 * @param physicalexamForm
	 * @return
	 * @throws Exception 
	 * 
	 */
	public String savePhysicaldateByimport(PhysicalexamForm physicalexamForm,List<String> errline) throws  Exception {
			
		File excel=physicalexamForm.getFile();	
		BufferedReader filedata = new BufferedReader(new InputStreamReader(new FileInputStream(excel)));
		
		String line="";
		
		if((line=filedata.readLine())==null){
			return "firstnotmatch";	
		}
		line=line.trim();
		if(!(line.equals(InitString.PHYSICAL_Hospital_import)||line.equals(InitString.PHYSICAL_Hospital_import+","))){
			return "firstnotmatch";
		}
		while ((line = filedata.readLine()) != null) { 
			String[] data=line.split(",");
        	if(data.length<2){
        		//数据长度不对，导入失败的信息
        		errline.add(line);
        		break;        		
        	}
        	String usernumber=data[0].trim();
        	if(StringUtils.isBlank(usernumber)){
        		//数据信息有错误
        		errline.add(line);
        		break;
        	}
        	String physicalreservedate=data[1].trim();
        	if(StringUtils.isBlank(physicalreservedate)){
        		//数据信息有错误
        		errline.add(line);
        		break;
        	}
        	SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	if(physicalreservedate.contains("/")){
        		physicalreservedate=physicalreservedate.replace("/", "-"); 
        	}
        	
			String date=bartDateFormat.format(bartDateFormat.parse(physicalreservedate));
        	String hql="Update Physicalexam a Set a.physicaldate=DATE_FORMAT(?,'%Y-%m-%d') , a.physicalstate='1'"
					+ " where a.usernumber = ? and state='0' and a.physicalplan=?  ";
			dao.updateByQuery(hql,date,usernumber,physicalexamForm.getPhysicalplan());

		}		
			
		return "success";				
	}
	/**
	 * 保存用户的复查项
	 * @param physicalexamForm
	 * @return
	 */
	public String savePhysicalreview(PhysicalexamForm physicalexamForm) {
		//判断复查项是否已添加
		Physicalinit physicalinit=(com.mini.entity.Physicalinit) dao.get(Physicalinit.class, 1);
		physicalexamForm.setPhysicalplan(physicalinit.getPhysicalplan());
		String hql="From Physicalreview a Where a.usernumber = ? and state='0' and a.physicalplan=? ";
		List<Physicalreview> list=dao.find(hql,physicalexamForm.getUsernumber(),physicalexamForm.getPhysicalplan());
		if(list!=null&&list.size()!=0){
			return "haveimport";
		}
		Physicalreview physicalreview=new Physicalreview();
		physicalreview.setUsernumber(physicalexamForm.getUsernumber());
		physicalreview.setReviewcontent(physicalexamForm.getReviewcontent());
		physicalreview.setUsername(physicalexamForm.getUsername());
		physicalreview.setPhysicalplan(physicalexamForm.getPhysicalplan());
		physicalreview.setImportdate(new Date());
		physicalreview.setState("0");
		dao.save(physicalreview);
		return "success";
	}
	
	public JSONObject searchphysicalreview(PhysicalexamForm physicalexamForm) {
		PagerTool pagerTool=new PagerTool();
		//当前页数
		int pageNum=1;
		int totalRows=0;
		pageSize=Integer.valueOf(physicalexamForm.getPageSize());
		if(StringUtils.isNotBlank(physicalexamForm.getCurrentPage())){
			pageNum=Integer.valueOf(physicalexamForm.getCurrentPage());
		}
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);

		String hqlWhere = " and o.state='0' ";
		//组织排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.importdate", "desc");
		List<String> paramsList = new ArrayList<String>();
		
		//查询条件，工号，姓名，体检号，预约体检时间，体检时间，体检地点，体检状态，体检批次，是否有家属
		if(StringUtils.isNotBlank(physicalexamForm.getUsernumber())){
			hqlWhere += " and o.usernumber like ?";
			paramsList.add("%" + physicalexamForm.getUsernumber() + "%");
			
		}
		if(StringUtils.isNotBlank(physicalexamForm.getUsername())){
			hqlWhere += " and o.username like ?";
			paramsList.add("%" + physicalexamForm.getUsername() + "%");
			
		}
		if(StringUtils.isNotBlank(physicalexamForm.getReviewcontent())){
			hqlWhere += " and o.reviewcontent like ?";
			paramsList.add("%" + physicalexamForm.getReviewcontent() + "%");
			
		}
		//体检批次
		if(StringUtils.isNotBlank(physicalexamForm.getPhysicalplan())&&(!physicalexamForm.getPhysicalplan().equals("0"))){
			hqlWhere += " and o.physicalplan=?";			
			paramsList.add( physicalexamForm.getPhysicalplan());			
		}
		
		Object [] params = paramsList.toArray();
		
		List<Physicalreview> physicalexamlist=dao.findPage(Physicalreview.class, hqlWhere, params, orderby,pagerTool);
		List<PhysicalexamForm> returnlist=new ArrayList<PhysicalexamForm>();
		String[] physical_planarray=findddlname(physical_plan);
		String[] physical_statearray=findddlname(physical_state);
		String[] physical_positionarray=findddlname(physical_position);	
	
		PhysicalexamForm physicalreviewForm=null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	
		for(int i=0;i<physicalexamlist.size();i++){
			Physicalreview physicalreview=physicalexamlist.get(i);
			physicalreviewForm=new PhysicalexamForm();
			physicalreviewForm.setUsername(physicalreview.getUsername());
			//工号
			physicalreviewForm.setUsernumber(physicalreview.getUsernumber());
			//查询人员的体检时间和地点
			//体检批次
			String hql="From Physicalexam a Where a.usernumber = ? and state='0'  ";
			Physicalexam physicalexam=null;
			if(StringUtils.isNotBlank(physicalexamForm.getPhysicalplan())&&(!physicalexamForm.getPhysicalplan().equals("0"))){
				hql += " and a.physicalplan=?";	
				physicalexam=(Physicalexam) dao.get(hql,physicalreview.getUsernumber(),physicalexamForm.getPhysicalplan());
			}else{
				physicalexam=(Physicalexam) dao.get(hql,physicalreview.getUsernumber());

			}
			//体检时间
			if(physicalexam==null){
				//说明没有体检时间
				physicalreviewForm.setUsername(physicalreview.getUsername());
				physicalreviewForm.setUsernumber(physicalreview.getUsernumber());
				physicalreviewForm.setId(String.valueOf(physicalreview.getId()));
				physicalreviewForm.setReviewcontent(physicalreview.getReviewcontent());
				physicalreviewForm.setPhysicalimportdate(dateFormat.format(physicalreview.getImportdate()));
				physicalreviewForm.setPhysicalplan(physical_planarray[Integer.valueOf(physicalreview.getPhysicalplan())-1]);
				returnlist.add(physicalreviewForm);		
				continue;
			}
			if(physicalexamForm.getPhysicaldate()!=null&&StringUtils.isNotBlank(physicalexamForm.getPhysicaldate())){
				//说明体检时间为约束条件
				if(physicalexam.getPhysicaldate()==null||!physicalexamForm.getPhysicaldate().equals(dateFormat.format(physicalexam.getPhysicaldate()))){
					//不符合查询条件
					continue;
				}	
				
			}
			if(physicalexamForm.getPhysicalreservedate()!=null&&StringUtils.isNotBlank(physicalexamForm.getPhysicalreservedate())){
				//说明体检时间为约束条件
				if(physicalexam.getPhysicalreservedate()==null||!physicalexamForm.getPhysicalreservedate().equals(dateFormat.format(physicalexam.getPhysicalreservedate()))){
					//不符合查询条件
					continue;
				}
								
			}
			physicalreviewForm.setPhysicalreservedate(physicalexam.getPhysicalreservedate()==null?null:dateFormat.format(physicalexam.getPhysicalreservedate()));
			physicalreviewForm.setPhysicaldate(dateFormat.format(physicalexam.getPhysicaldate()));
			physicalreviewForm.setPhysicalposition(physical_positionarray[Integer.valueOf(physicalexam.getPhysicalposition())-1]);
			physicalreviewForm.setReviewcontent(physicalreview.getReviewcontent());	
			physicalreviewForm.setPhysicalplan(physical_planarray[Integer.valueOf(physicalreview.getPhysicalplan())-1]);
			physicalreviewForm.setId(String.valueOf(physicalreview.getId()));
		
			physicalreviewForm.setPhysicalimportdate(dateFormat.format(physicalreview.getImportdate()));
			returnlist.add(physicalreviewForm);			
			
		}
		totalRows=pagerTool.getTotalRows();
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);
		JSONObject result = new JSONObject();		
		result.put("totalRows", totalRows);
		result.put("totalPages", pagerTool.getTotalPages());
		result.put("physicallist", returnlist);
		result.put("physical_plan",physical_planarray );
		result.put("physical_state", physical_statearray);
		result.put("physical_position", physical_positionarray);
		return result;
	}
	/**
	 * 删除体检信息复查项
	 * @param physicalexamForm
	 * @return
	 */
	public String deletephsicalreview(PhysicalexamForm physicalexamForm) {
		String hql="Update Physicalreview a Set a.state=? where a.id=? ";
		dao.updateByQuery(hql, "2",Integer.valueOf(physicalexamForm.getId()));
		return "success";
	}
	/**
	 * 保存用户要查询的体检批次
	 * @param physicalexamForm
	 * @return
	 */
	public String savephsicalinit(PhysicalexamForm physicalexamForm) {
		Physicalinit Physicalinit=(com.mini.entity.Physicalinit) dao.get(Physicalinit.class, 1);
		if(Physicalinit==null){
			Physicalinit physicalinit1=new Physicalinit();
			physicalinit1.setId(1);
			physicalinit1.setPhysicalplan(physicalexamForm.getPhysicalplan());
			dao.save(physicalinit1);
			Physicalinit physicalinit2=new Physicalinit();
			physicalinit2.setId(2);
			physicalinit2.setPhysicalplan(physicalexamForm.getNophysicalcontentinfor());
			dao.save(physicalinit2);
			Physicalinit physicalinit3=new Physicalinit();
			physicalinit3.setId(3);
			physicalinit3.setPhysicalplan(physicalexamForm.getPhysicaldateedit());
			dao.save(physicalinit3);
		}else{
			String hql="Update Physicalinit a Set a.physicalplan=? where a.id='1' ";
			dao.updateByQuery(hql, physicalexamForm.getPhysicalplan());
			/* 无体检信息提示 */
			hql="Update Physicalinit a Set a.physicalplan=? where a.id='2' ";
			dao.updateByQuery(hql, physicalexamForm.getNophysicalcontentinfor());
			/* 体检日期修改 */
			hql="Update Physicalinit a Set a.physicalplan=? where a.id='3' ";
			dao.updateByQuery(hql, physicalexamForm.getPhysicaldateedit());
		}

		
		return "success";
	}
	/**
	 * 保存用户要查询的体检批次
	 * @param physicalexamForm
	 * @return
	 */
	public String saveeditphysicaldateinit(PhysicalexamForm physicalexamForm) {
		String hql="Update Physicalinit a Set a.physicalplan=? where a.id='2' ";
		dao.updateByQuery(hql, physicalexamForm.getPhysicalcontent());
		return "success";
	}
//	/**
//	 * 获取管理员的修改体检时间要求
//	 * @param physicalexamForm
//	 * @return
//	 */
//	public JSONObject geteditphysicaldateinit(PhysicalexamForm physicalexamForm) {
//		String hql="From Physicalinit a Where a.id='2' ";
//		Physicalinit Physicalinit=(com.mini.entity.Physicalinit) dao.get(Physicalinit.class, 2);
//		JSONObject result = new JSONObject();	
//		PhysicalexamForm returnphysicalexamForm=new PhysicalexamForm();
//		returnphysicalexamForm.setPhysicalcontent(Physicalinit.getPhysicalplan());
//		result.put("physicalexamForm", returnphysicalexamForm);
//		return result;
//	}
	/**
	 * 获取用户的体检信息
	 * @param physicalexamForm
	 * @param user 
	 * @return
	 * @throws ParseException 
	 */
	public JSONObject getuserphsicalinfo(PhysicalexamForm physicalexamForm, User user) throws ParseException {
		//将其变为8位
		String usernumber=user.getNumber();		 
		if(usernumber.length()<8){
			for(int i=usernumber.length();i<8;i++){
				usernumber="0"+usernumber;
			}
		}
		user.setNumber(usernumber);		
		String inithql="From Physicalinit a Where a.id=?";
		Physicalinit physicalinit=(Physicalinit) dao.get(inithql, 1);		
		String hql="From Physicalexam a Where a.usernumber = ? and state='0' and a.physicalplan=? ";
		Physicalexam physicalexam=(Physicalexam) dao.get(hql,user.getNumber(),physicalinit.getPhysicalplan());
		if(physicalexam==null){
			//没有体检信息
			Physicalinit returnphysicalinit=(Physicalinit) dao.get(Physicalinit.class, 2);
			JSONObject physicalinitresult = new JSONObject();	
			physicalinitresult.put("physicalinit", returnphysicalinit);
			JSONObject result = new JSONObject();	
			result.put("code", "200");
			result.put("message", "noImformation");
			result.put("context", physicalinitresult);
			return result;
		}
		String[] physical_planarray=findddlname(physical_plan);
		String[] physical_statearray=findddlname(physical_state);
		String[] physical_positionarray=findddlname(physical_position);
		PhysicalexamForm returnphysicalexamForm=this.findPhysicalexamPOTOVO(physicalexam,physical_planarray,physical_statearray,physical_positionarray);
		List<Physicalinit> returnlist=dao.find(Physicalinit.class);
		//设置状态，是申请体检还是已经拒绝还是已经同意还是没有申请
		hql="From PhysicalEditRedate where usernumber=? and physicalplan=?";
		List<PhysicalEditRedate> exceptdatelist=dao.find(hql, physicalexam.getUsernumber(),returnlist.get(0).getPhysicalplan());
		
		if(exceptdatelist==null||exceptdatelist.size()==0){
			//说明没有申请
			returnphysicalexamForm.setExpectState("0");
		}else{
			PhysicalEditRedate physicalEditRedate=exceptdatelist.get(0);
			if(physicalEditRedate.getFlag().equals("1")){
				//处于申请状态
				returnphysicalexamForm.setExpectState("1");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				if(exceptdatelist.get(0).getEditRedate()!=null){
					returnphysicalexamForm.setExpectphysicalDate(dateFormat.format(exceptdatelist.get(0).getEditRedate()));
				}				
				
			}else if(physicalEditRedate.getFlag().equals("2")){
				//处于同意状态
				returnphysicalexamForm.setExpectState("2");
				}else if(physicalEditRedate.getFlag().equals("3")){
					//处于拒绝状态
					returnphysicalexamForm.setExpectState("3");
				}
			
		}	
		
		JSONObject resultss = new JSONObject();	
		resultss.put("physicalexamForm", returnphysicalexamForm);	
		JSONObject result = new JSONObject();	
		result.put("code", "200");
		result.put("message", "success");
		result.put("context", resultss);
		return result;
	}
	/**
	 * 获取用户的家属列表
	 * @param physicalexamForm
	 * @param user
	 * @return
	 */
	public JSONObject getuserphsicalrelatives(
		PhysicalexamForm physicalexamForm, User user) {
		String inithql="From Physicalinit a Where a.id=?";
		Physicalinit physicalinit=(Physicalinit) dao.get(inithql, 1);	
		physicalexamForm.setPhysicalplan(physicalinit.getPhysicalplan());
		String hql="From Physicalrelatives a Where a.usernumber = ?  and a.physicalplan=? and state='0' ";
		List<Physicalrelatives> list=dao.find(hql, user.getNumber(),physicalexamForm.getPhysicalplan());
		JSONObject result = new JSONObject();	
		if(list==null){
			result.put("physicalrelativeslist", null);			
			result.put("physicalrelativessize", 0);
			return result;
		}
		List<PhysicalRelaviteForm> returnlist =new ArrayList<PhysicalRelaviteForm>();
		PhysicalRelaviteForm returnphysicalexamForm=null;
		String[] physical_planarray=findddlname(physical_plan);
		for(int i=0;i<list.size();i++){
			Physicalrelatives physicalrelatives=list.get(i);
			returnphysicalexamForm=new PhysicalRelaviteForm();
			returnphysicalexamForm.setRelationship(physicalrelatives.getRelationship());
			returnphysicalexamForm.setIdnumber(physicalrelatives.getIdnumber());
			returnphysicalexamForm.setPhonenumber(physicalrelatives.getPhonenumber());
			returnphysicalexamForm.setPhysicalplan(physical_planarray[Integer.valueOf(physicalrelatives.getPhysicalplan())-1]);
			returnphysicalexamForm.setRelativeName(physicalrelatives.getRelativeName());
			returnphysicalexamForm.setId(String.valueOf(physicalrelatives.getId()));
			returnlist.add(returnphysicalexamForm);
			
		}		
		result.put("physicalrelativeslist", returnlist);		
		result.put("physicalrelativessize", list.size());
		return result;
	}
	/**
	 * 保存用户的家属列表
	 * @param physicalexamForm
	 * @param user
	 * @return
	 */
	public String saveuserphsicalrelatives(
		PhysicalexamForm physicalexamForm, User user) {
		Physicalrelatives physicalrelatives=new Physicalrelatives();
		physicalrelatives.setIdnumber(physicalexamForm.getIdnumber());
		physicalrelatives.setPhonenumber(physicalexamForm.getPhonenumber());
		String inithql="From Physicalinit a Where a.id=?";
		Physicalinit physicalinit=(Physicalinit) dao.get(inithql, 1);		
		physicalrelatives.setPhysicalplan(physicalinit.getPhysicalplan());
		physicalrelatives.setRelationship(physicalexamForm.getRelationship());
		physicalrelatives.setUsernumber(user.getNumber());
		physicalrelatives.setState("0");
		physicalrelatives.setRelativeName(physicalexamForm.getRelativeName());
		dao.save(physicalrelatives);
		String hql="Update Physicalexam a Set a.hasRelatives=? where a.usernumber=? and state='0'";
		dao.updateByQuery(hql, "1",user.getNumber());
		return "success";
	}
	/**
	 * 删除用户的家属列表,状态为2代表已删除
	 * @param physicalexamForm
	 * @param user
	 * @return
	 */
	public String deleteuserphsicalrelatives(PhysicalexamForm physicalexamForm,
			User user) {
		String hql="Update Physicalrelatives a Set a.state=? where a.id=? ";
		dao.updateByQuery(hql, "2",Integer.valueOf(physicalexamForm.getId()));
		//判断是否全部删除。如果都删除则把家属状态变为0
		String inithql="From Physicalinit a Where a.id=?";
		Physicalinit physicalinit=(Physicalinit) dao.get(inithql, 1);		
	
		String shql="From Physicalrelatives a Where a.usernumber = ?  and a.physicalplan=? and state='0' ";
		List<Physicalrelatives> list=dao.find(shql, user.getNumber(),physicalinit.getPhysicalplan());
		if(list==null||list.size()==0){
			//说明都删除完
			String sshql="Update Physicalexam a Set a.hasRelatives=? where a.usernumber=? and state='0'";
			dao.updateByQuery(sshql, "0",user.getNumber());
		}

		return "success";

	}
	 
	public JSONObject getuserphsicalrelativesOnback(
			PhysicalexamForm physicalexamForm) throws Exception {
			String[] physical_planarray=findddlname(physical_plan);
			String hql="From Physicalrelatives a Where a.usernumber = ?  and a.physicalplan=? and state='0' ";
//			String physicalplan=new String(physicalexamForm.getPhysicalplan().getBytes("GBK"),"UTF-8");
//			physicalexamForm.setPhysicalplan(physicalplan);

			List<Physicalrelatives> list=dao.find(hql, physicalexamForm.getUsernumber(),this.setBookPosition(physical_planarray,physicalexamForm.getPhysicalplan()));
			JSONObject result = new JSONObject();	
			if(list==null){
				result.put("physicalrelativeslist", null);
				
				result.put("physicalrelativessize", 0);
				return result;
			}
			List<PhysicalRelaviteForm> returnlist =new ArrayList<PhysicalRelaviteForm>();
			PhysicalRelaviteForm returnphysicalexamForm=null;
			
			for(int i=0;i<list.size();i++){
				Physicalrelatives physicalrelatives=list.get(i);
				returnphysicalexamForm=new PhysicalRelaviteForm();
				returnphysicalexamForm.setRelationship(physicalrelatives.getRelationship());
				returnphysicalexamForm.setIdnumber(physicalrelatives.getIdnumber());
				returnphysicalexamForm.setPhonenumber(physicalrelatives.getPhonenumber());
				returnphysicalexamForm.setPhysicalplan(physical_planarray[Integer.valueOf(physicalrelatives.getPhysicalplan())-1]);
				returnphysicalexamForm.setRelativeName(physicalrelatives.getRelativeName());
				returnphysicalexamForm.setId(String.valueOf(physicalrelatives.getId()));
				returnlist.add(returnphysicalexamForm);
				
			}		
			result.put("physicalrelativeslist", returnlist);		
			result.put("physicalrelativessize", list.size());
			return result;
	}
	private String setBookPosition(String[] col,
			String value) {
		for(int i=0;i<col.length;i++){
			if(col[i].trim().equals(value.trim())){
				return String.valueOf(i+1);
			}
		}
		return "error";
	}
	public ArrayList<String> getExcelFiledNameList(String[] titles) {
		ArrayList<String> filedName = new ArrayList<String>();
		for(int i=0;i<titles.length-1;i++){
			String title = titles[i]+",";
			filedName.add(title);
		}
		filedName.add(titles[titles.length]);
		return filedName;
	}
	public ArrayList<String> getExcelFiledDataList(PhysicalexamForm physicalexamform,
			HttpServletRequest request) throws Exception {
		
		String hqlWhere = " and o.state='0' ";
		//组织排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.physicalreservedate", "desc");
		List<String> paramsList = new ArrayList<String>();		
		//查询条件，工号，姓名，体检号，预约体检时间，体检时间，体检地点，体检状态，体检批次，是否有家属
		if(StringUtils.isNotBlank(physicalexamform.getUsernumber())){
			hqlWhere += " and o.usernumber like ?";
			paramsList.add("%" + physicalexamform.getUsernumber() + "%");			
		}
		if(StringUtils.isNotBlank(physicalexamform.getUsername())){
			hqlWhere += " and o.username like ?";
			paramsList.add("%" + physicalexamform.getUsername() + "%");			
		}		
		//预约体检时间
		if(StringUtils.isNotBlank(physicalexamform.getPhysicalreservedate())){			 
			hqlWhere += " and DATE_FORMAT( o.physicalreservedate,'%Y-%m-%d')=? ";
			paramsList.add(physicalexamform.getPhysicalreservedate());			
		}
		//体检时间
		if(StringUtils.isNotBlank(physicalexamform.getPhysicaldate())){			
			hqlWhere += " and DATE_FORMAT( o.physicaldate,'%Y-%m-%d')=? ";			
			paramsList.add(physicalexamform.getPhysicaldate());			
		}
		//体检状态
		if(StringUtils.isNotBlank(physicalexamform.getPhysicalstate())&&(!physicalexamform.getPhysicalstate().equals("0"))){
			hqlWhere += " and o.physicalstate=?";			
			paramsList.add( physicalexamform.getPhysicalstate());			
		}
		//体检地点
		if(StringUtils.isNotBlank(physicalexamform.getPhysicalposition())&&(!physicalexamform.getPhysicalposition().equals("0"))){
			hqlWhere += " and o.physicalposition=?";			
			paramsList.add( physicalexamform.getPhysicalposition());			
		}
		//体检批次,为0查询所有
		if(StringUtils.isNotBlank(physicalexamform.getPhysicalplan())&&(!physicalexamform.getPhysicalplan().equals("0"))){
			hqlWhere += " and o.physicalplan=?";			
			paramsList.add( physicalexamform.getPhysicalplan());			
		}
		
		Object [] params = paramsList.toArray();
		List<Physicalexam> returlist=dao.findPage(Physicalexam.class, hqlWhere, params, orderby);
		//已经获取到数据		
//		List<PhysicalexamForm> returnlist=new ArrayList<PhysicalexamForm>();
		String[] physical_planarray=findddlname(physical_plan);
		String[] physical_statearray=findddlname(physical_state);
		String[] physical_positionarray=findddlname(physical_position);	
		ArrayList<String> filedData=new ArrayList<String>();
		if(returlist==null||returlist.size()==0){
			filedData.add("");
			return filedData;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	
		for(int i=0;i<returlist.size();i++){
			String line="";
			Physicalexam physicalexam=returlist.get(i);
//			String[] titles={"工号","姓名","体检地点","体检号","预约体检时间",,"体检日期","体检状态","体检批次","家属体检"};	
//			工号
			line=line+physicalexam.getUsernumber()+","+physicalexam.getUsername()+","+
				 physical_positionarray[Integer.valueOf(physicalexam.getPhysicalposition())-1]+","+
				 dateFormat.format(physicalexam.getPhysicalreservedate())+",";
			if(physicalexam.getPhysicaldate()!=null)
				line=line+dateFormat.format(physicalexam.getPhysicaldate())+",";
			else
				line=line+",";
			//判断当前日期和体检预约日期进行比较，看哪个比较大
			Date dateTime1 = dateFormat.parse(dateFormat.format(physicalexam.getPhysicalreservedate()));
			Date dateTime2 = dateFormat.parse(dateFormat.format(new Date()));
			if(dateTime1.compareTo(dateTime2)<0){
				//设置为体检过期
				if(physicalexam.getPhysicalstate().equals(InitString.physicalstate_noexam)||
						physicalexam.getPhysicalstate().equals(InitString.physicalstate_passexam)){
					//未体检和体检过期才能变为条件过期
					line=line+physical_statearray[1]+",";
				}else{
					line=line+physical_statearray[Integer.valueOf(physicalexam.getPhysicalstate())-1]+",";					
				}		

			}else{
				//体检未过期
				line=line+physical_statearray[Integer.valueOf(physicalexam.getPhysicalstate())-1]+",";	
			}
			//"体检批次"	
			line=line+physical_planarray[Integer.valueOf(physicalexam.getPhysicalplan())-1]+",";		
		
			//"家属体检"
			//判断是否有亲属
			if(physicalexam.getHasRelatives().equals("0")){
				//没有亲属
				line=line+"无"+",";	
			}
			if(physicalexam.getHasRelatives().equals("1")){
				//没有亲属
				line=line+"有"+",";	

			}
			filedData.add(line);
			
		}
	
		return filedData;
	}
	public ArrayList<String> getExcelFiledDataListnull(
		PhysicalexamForm physicalexamForm, HttpServletRequest request) {
		ArrayList<String> filedData = new ArrayList<String>();
		//工号
		String[] physical_positionarray=findddlname(physical_position);	
		String line="12345678"+","+"张三"+","+physical_positionarray[1]+","+"2015-6-6";
		filedData.add(line);
		return filedData;
	}
	public ArrayList<String> getExcelFiledDataListdate(
			PhysicalexamForm physicalexamForm, HttpServletRequest request) {
		ArrayList<String> filedData = new ArrayList<String>();
		//工号
		String line="12345678,2015-6-6";
		filedData.add(line);
		return filedData;
	}
	/**
	 * 导出家属体检信息
	 * @param physicalexamForm
	 * @param request
	 * @return
	 */
	public ArrayList<String> getExcelFiledDataListrelatives(
			PhysicalexamForm physicalexamForm, HttpServletRequest request) {
		String hql="From Physicalrelatives a Where state='0'  and a.physicalplan=? ";
		List<Physicalrelatives> list=new ArrayList<Physicalrelatives>();
		if(StringUtils.isBlank(physicalexamForm.getPhysicalplan())||(physicalexamForm.getPhysicalplan().equals("0"))){
			hql="From Physicalrelatives a Where state='0'  ";
			list=dao.find(hql);
		}else{
			//获取家属信息
			list=dao.find(hql, physicalexamForm.getPhysicalplan());
		}
		ArrayList<String> filedData = new ArrayList<String>();
		if(list==null||list.size()==0){
			filedData.add("");
			return filedData;
		}
		
		//{"工号","员工姓名","体检批次","家属姓名","身份证号","电话","关系"};	
		String[] physical_planarray=findddlname(physical_plan);
		for(int i=0;i<list.size();i++){
			String line="";
			Physicalrelatives physicalrelatives=list.get(i);
			String userhql="From Physicalexam a Where a.usernumber = ? and a.state='0' and a.physicalplan=?";
			List<Physicalexam> physicalexamlist= dao.find(userhql,physicalrelatives.getUsernumber(),physicalrelatives.getPhysicalplan());
//			if(physicalexamlist!)
			if(physicalexamlist==null||physicalexamlist.size()==0){
				continue;
			}
			Physicalexam physicalexam=physicalexamlist.get(0);
			line=line+physicalrelatives.getUsernumber()+","+physicalexam.getUsername()+","+
					physical_planarray[Integer.valueOf(physicalrelatives.getPhysicalplan())-1]+","+
					physicalrelatives.getRelativeName()+","+physicalrelatives.getIdnumber()+","+
					physicalrelatives.getPhonenumber()+","+physicalrelatives.getRelationship();
			filedData.add(line);
		}
		
		return filedData;
	}
	public ArrayList<String> getExcelFiledDataListreview(
			PhysicalexamForm physicalexamForm, HttpServletRequest request) {
		String hqlWhere = " and o.state='0' ";
		//组织排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.physicalplan", "desc");
		List<String> paramsList = new ArrayList<String>();
		
		//查询条件，工号，姓名，体检号，预约体检时间，体检时间，体检地点，体检状态，体检批次，是否有家属
		if(StringUtils.isNotBlank(physicalexamForm.getUsernumber())){
			hqlWhere += " and o.usernumber like ?";
			paramsList.add("%" + physicalexamForm.getUsernumber() + "%");
			
		}
		if(StringUtils.isNotBlank(physicalexamForm.getUsername())){
			hqlWhere += " and o.username like ?";
			paramsList.add("%" + physicalexamForm.getUsername() + "%");
			
		}		
		if(StringUtils.isNotBlank(physicalexamForm.getReviewcontent())){
			hqlWhere += " and o.reviewcontent like ?";
			paramsList.add("%" + physicalexamForm.getReviewcontent() + "%");
			
		}
		//体检批次
		if(StringUtils.isNotBlank(physicalexamForm.getPhysicalplan())&&(!physicalexamForm.getPhysicalplan().equals("0"))){
			hqlWhere += " and o.physicalplan=?";			
			paramsList.add( physicalexamForm.getPhysicalplan());			
		}		
		Object [] params = paramsList.toArray();
		@SuppressWarnings("unchecked")
		List<Physicalreview> physicalexamlist=dao.findPage(Physicalreview.class, hqlWhere, params, orderby);
		String[] physical_planarray=findddlname(physical_plan);
		//工号	姓名	体检批次	复查内容
		ArrayList<String> filedData = new ArrayList<String>();
		if(physicalexamlist==null||physicalexamlist.size()==0){			
			filedData.add("");
			return filedData;
		}
		for(int i=0;i<physicalexamlist.size();i++){
			String line="";
			Physicalreview Physicalreview=physicalexamlist.get(i);	
			line=line+Physicalreview.getUsernumber()+","+Physicalreview.getUsername()+","+
					physical_planarray[Integer.valueOf(Physicalreview.getPhysicalplan())-1]+","+
					Physicalreview.getReviewcontent();			
			filedData.add(line);
		}		
		return filedData;
	}
	public JSONObject getUsernameByusernumberlike(
			PhysicalexamForm physicalexamForm) throws Exception {
		//获取physical_plan
		Physicalinit physicalinit=(com.mini.entity.Physicalinit) dao.get(Physicalinit.class, 1);
		physicalexamForm.setPhysicalplan(physicalinit.getPhysicalplan());
		String hql="From Physicalexam a Where a.usernumber like ? and state='0' and a.physicalplan=? ";
		List<Physicalexam> physicalexamlist=dao.find(hql,'%'+physicalexamForm.getUsernumber()+'%',physicalexamForm.getPhysicalplan());
		List<PhysicalexamForm> returnlist=new ArrayList<PhysicalexamForm>();
		if(physicalexamlist==null){
			//用户不存在
			return null;
		}
	
		String[] physical_planarray=findddlname(physical_plan);
		String[] physical_statearray=findddlname(physical_state);
		String[] physical_positionarray=findddlname(physical_position);	
		for(int i=0;i<physicalexamlist.size();i++){
			PhysicalexamForm	returnForm=this.findPhysicalexamPOTOVO(physicalexamlist.get(i),physical_planarray,physical_statearray,physical_positionarray);
			returnlist.add(returnForm);
		}
		
//		PhysicalexamForm	returnForm=this.findPhysicalexamPOTOVO(physicalexam,physical_planarray,physical_statearray,physical_positionarray);
		JSONObject result = new JSONObject();		

		result.put("physicalexamForm",returnlist );
		return result;
	}
	public JSONObject geteditphysicalinit(PhysicalexamForm physicalexamForm) {
		
		List<Physicalinit> returnlist=dao.find(Physicalinit.class);
		JSONObject result = new JSONObject();	
		PhysicalexamForm returnphysicalexamForm=new PhysicalexamForm();
		String[] physical_planarray=findddlname(physical_plan);
		result.put("physical_plan",physical_planarray );
		result.put("physicalexamForm", returnlist);
		return result;
	}
	/**
	 * 保存修改体检时间
	 * @param physicalexamForm
	 * @return
	 * @throws Exception 
	 */
	public String applyExpectphysicalDate(PhysicalexamForm physicalexamForm) throws Exception {
		String[] physical_positionarray=findddlname(physical_position);
		PhysicalEditRedate physicalEditRedate=new PhysicalEditRedate();
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 		
		physicalEditRedate.setEditRedate(bartDateFormat.parse(physicalexamForm.getExpectphysicalDate()));
		physicalEditRedate.setUsername(physicalexamForm.getUsername());
		physicalEditRedate.setUsernumber(physicalexamForm.getUsernumber());
		//此时处于申请状态
		physicalEditRedate.setFlag(InitString.PHYSICAL_applystate);
		List<Physicalinit> list=dao.find(Physicalinit.class);
		physicalEditRedate.setPhysicalplan(list.get(0).getPhysicalplan());
		physicalEditRedate.setImportdate(new Date());
		String result=getpositonnumber(physical_positionarray,physicalexamForm.getPhysicalposition());
		if(result.equals("error")){
			return "error";
		}else{
			physicalEditRedate.setPhysicalposition(result);
		}		
		dao.save(physicalEditRedate);
		return "success";
	}
	public JSONObject applyExpectphysicalDatesearch(
			PhysicalexamForm physicalexamForm) {
		PagerTool pagerTool=new PagerTool();
		//当前页数
		int pageNum=1;
		int totalRows=0;
		
		if(StringUtils.isNotBlank(physicalexamForm.getCurrentPage())){
			pageNum=Integer.valueOf(physicalexamForm.getCurrentPage());
		}
		if(StringUtils.isNotBlank(physicalexamForm.getPageSize())){
			pageSize=Integer.valueOf(physicalexamForm.getPageSize());
		}
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);
		String hqlWhere = " and flag='1' ";
		//组织排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.importdate", "desc");
		List<String> paramsList = new ArrayList<String>();
		if(StringUtils.isNotBlank(physicalexamForm.getUsernumber())){
			hqlWhere += " and o.usernumber like ?";
			paramsList.add("%" + physicalexamForm.getUsernumber() + "%");			
		}
		if(StringUtils.isNotBlank(physicalexamForm.getUsername())){
			hqlWhere += " and o.username like ?";
			paramsList.add("%" + physicalexamForm.getUsername() + "%");
			
		}
		//体检批次
		if(StringUtils.isNotBlank(physicalexamForm.getPhysicalplan())&&(!physicalexamForm.getPhysicalplan().equals("0"))){
			hqlWhere += " and o.physicalplan=?";			
			paramsList.add( physicalexamForm.getPhysicalplan());			
		}
		if(StringUtils.isNotBlank(physicalexamForm.getPhysicalposition())&&(!physicalexamForm.getPhysicalposition().equals("0"))){
			hqlWhere += " and o.physicalposition=?";			
			paramsList.add( physicalexamForm.getPhysicalposition());			
		}
		Object [] params = paramsList.toArray();		
		List<PhysicalEditRedate> physicaldatelist=dao.findPage(PhysicalEditRedate.class, hqlWhere, params, orderby,pagerTool);
		List<PhysicalExpectdateForm> returnlist=new ArrayList<PhysicalExpectdateForm>();
		PhysicalExpectdateForm physicalExpectdateForm=null;
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String[] physical_planarray=findddlname(physical_plan);
		String[] physical_positionarray=findddlname(physical_position);
		for(int i=0;i<physicaldatelist.size();i++){
			PhysicalEditRedate physicalEditRedate=physicaldatelist.get(i);
			String hql="From Physicalexam s where s.usernumber=? and physicalplan=? ";
			Physicalexam physicalexam=(Physicalexam)dao.get(hql, physicalEditRedate.getUsernumber(),physicalEditRedate.getPhysicalplan());
			physicalExpectdateForm=new PhysicalExpectdateForm();
			physicalExpectdateForm.setEditRedate(bartDateFormat.format(physicalEditRedate.getEditRedate()));
			physicalExpectdateForm.setImportdate(bartDateFormat.format(physicalEditRedate.getImportdate()));
			physicalExpectdateForm.setPhysicalplan(physical_planarray[Integer.valueOf(physicalEditRedate.getPhysicalplan())-1]);
			physicalExpectdateForm.setUsername(physicalEditRedate.getUsername());
			physicalExpectdateForm.setUsernumber(physicalEditRedate.getUsernumber());
			physicalExpectdateForm.setPhysicalposition(physical_positionarray[Integer.valueOf(physicalEditRedate.getPhysicalposition())-1]);
			physicalExpectdateForm.setPhysicalreservedate(bartDateFormat.format(physicalexam.getPhysicalreservedate()));
			physicalExpectdateForm.setId(String.valueOf(physicalEditRedate.getId()));
			returnlist.add(physicalExpectdateForm);
		}
		totalRows=pagerTool.getTotalRows();
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);
		JSONObject result = new JSONObject();		
		result.put("totalRows", totalRows);
		result.put("totalPages", pagerTool.getTotalPages());
		result.put("physicallist", returnlist);
		result.put("physical_plan",physical_planarray );
		result.put("physical_position",physical_positionarray );
		return result;

	}
	public String applyPhysicalDate(PhysicalexamForm physicalexamForm) {
		String hql="Update PhysicalEditRedate a Set a.flag=? where a.id=? ";
		if(physicalexamForm.getFlag().equals("1")){
			//同意，并修改体检时间
			PhysicalEditRedate physicalEditRedate=(PhysicalEditRedate)dao.get(PhysicalEditRedate.class, Integer.valueOf(physicalexamForm.getId()));
			
			String edithql="Update Physicalexam a Set a.physicalreservedate=? where a.usernumber=? and a.physicalplan=?";
			dao.updateByQuery(hql,"2",Integer.valueOf(physicalexamForm.getId()));
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
			dao.updateByQuery(edithql, physicalEditRedate.getEditRedate(),physicalEditRedate.getUsernumber(),physicalEditRedate.getPhysicalplan());
		}
		if(physicalexamForm.getFlag().equals("2")){
			//不同意
			dao.updateByQuery(hql,"3",Integer.valueOf(physicalexamForm.getId()));
		}
		return "success";
	}
	/**
	 * 查询体检的初始化信息
	 * @param physicalexamForm
	 * @return
	 */
	public JSONObject searchphsicalinit(PhysicalexamForm physicalexamForm) {
		List<Physicalinit> list=dao.find(Physicalinit.class);
		Physicalinit physicalinit=(Physicalinit)dao.get(Physicalinit.class, 1);
		String[] physical_planarray=findddlname(physical_plan);
		PhysicalinitForm returnform=new PhysicalinitForm();
		JSONObject result = new JSONObject();
		returnform.setPhysicalplan(physical_planarray[Integer.valueOf(physicalinit.getPhysicalplan())-1]);
		result.put("physicalinit",returnform);
		return result;
	}
	public String savePhysicalreviewBymanager(PhysicalexamForm physicalexamForm) {
		//判断复查项是否已添加
		String hql="Update Physicalreview a Set a.reviewcontent=? where a.id=? ";
		dao.updateByQuery(hql, physicalexamForm.getReviewcontent(), Integer.valueOf(physicalexamForm.getId()));
		return "success";
	
	}
	
	
	
	

}

package com.mini.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.barcodelib.barcode.a.g.m.r;
import com.mini.dao.CommonDao;
import com.mini.entity.BookNumber;
import com.mini.entity.Bookinfo;
import com.mini.entity.Feedback;
import com.mini.entity.ImportBookInfo;
import com.mini.entity.Physicalexam;
import com.mini.entity.Physicalreview;
import com.mini.entity.User;
import com.mini.entity.UserImage;
import com.mini.form.FeedbackForm;
import com.mini.form.FeedbackImageForm;
import com.mini.form.ImportBookForm;
import com.mini.form.JifenRankingForm;
import com.mini.form.PhysicalexamForm;
import com.mini.util.PageModel;
import com.mini.util.PagerTool;

/**
 * 反馈信息相关的业务逻辑类
 * @author 雷晓冰	2014-12-31
 */
public class FeedbackService {
	private CommonDao dao;
	private boolean hasPrevious;
	private boolean hasNext;
	int pageSize=9;
	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	/**
	 * 添加反馈信息
	 * @param feedback
	 * @return
	 */
	public boolean add(Feedback feedback) {
		boolean flag = false;
		try {
			dao.save(feedback);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 删除反馈信息
	 * @param id
	 * @return
	 */
	public boolean delete(int id) {
		boolean flag = false;
		try {
			dao.delete(Feedback.class, id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 修改反馈信息的状态
	 * @param id
	 * @param state
	 * @return
	 */
	public boolean updateState(int id, Integer state) {
		String hql = "Update Feedback f Set f.state = ? Where f.id = ?";
		boolean flag = false;
		try {
			dao.updateByQuery(hql, state, id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 分页获取反馈信息
	 * @param feedback
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageModel<Feedback> findByConditions(Feedback feedback, int pageNum, int pageSize) {
		PageModel<Feedback> model = new PageModel<Feedback>();
		//添加查询条件
		List<Criterion> criterionList = new ArrayList<Criterion>();
		//姓名
		if(feedback.getName() != null && !feedback.getName().matches("\\s*")) {
			Criterion criterion = Restrictions.like("name", feedback.getName(), MatchMode.ANYWHERE);
			criterionList.add(criterion);
		}
		//工号
		if(feedback.getNumber() != null && !feedback.getNumber().matches("\\s*")) {
			Criterion criterion = Restrictions.like("number", feedback.getNumber(), MatchMode.ANYWHERE);
			criterionList.add(criterion);
		}
		int size = criterionList.size();
		Criterion[] criterions = new Criterion[size];
		for(int i=0; i<size; i++) {
			criterions[i] = criterionList.get(i);
		}
		Order order = Order.desc("time");
		Order[] orders = {order};
		@SuppressWarnings("unchecked")
		List<Feedback> list = dao.find(Feedback.class, criterions, orders, pageNum, pageSize);
		int count = dao.count(Feedback.class, criterions);
		model.setList(list);
		model.setPageNo(pageNum);
		model.setTotalRecords(count);
		return model;
	}
	/**
	 * 查找所有的将反馈信息发送至后台
	 * @return
	 */
	public List<FeedbackForm> findallfeedback() {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		List<Feedback> list=dao.find(Feedback.class);
		List<FeedbackForm> returnlist=new ArrayList<FeedbackForm>();
		if(list==null||list.size()==0){
			return null;
		}
		FeedbackForm feedbackForm=null;
		for(int i=0;i<list.size();i++){
			Feedback feedback=list.get(i);
			feedbackForm=new FeedbackForm();
			feedbackForm.setContent(feedback.getContent());
			feedbackForm.setName(feedback.getName());
			feedbackForm.setNumber(feedback.getNumber());
			feedbackForm.setTime(bartDateFormat.format(feedback.getTime()));
			returnlist.add(feedbackForm);
		}
		
		return returnlist;
	}
	/**
	 * 获取表头
	 * @return
	 */
	public ArrayList getfeedbackExcelFiledNameList() {
//		String [] titles = {"图书名称","二维码"};
		String [] titles = {"姓名","工号","反馈内容","反馈时间"};
		ArrayList filedName = new ArrayList();
		for(int i=0;i<titles.length;i++){
			String title = titles[i];
			filedName.add(title);
		}
		return filedName;
	}

	/**
	 * 将获取到的数据进行序列号
	 * @param request 
	 * @param importBookForm 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList getfeedbackExcelFiledDataList() {
		ArrayList filedData = new ArrayList();
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		List<Feedback> list=dao.find(Feedback.class);
		if(list==null||list.size()==0){
			return null;
		}
		for(int i=0;i<list.size();i++){
			Feedback feedback=list.get(i);
			ArrayList dataList = new ArrayList();
			dataList.add(feedback.getName());
			dataList.add(feedback.getNumber());
			dataList.add(feedback.getContent());			
			dataList.add(bartDateFormat.format(feedback.getTime()));
			filedData.add(dataList);
		}
		
		return filedData;
	}
	/**
	 * 获取用户反馈的一些信息带图片
	 * @param feedbackImageForm
	 * @return
	 */
	public JSONObject getFeedbackImage(FeedbackImageForm feedbackImageForm) {
		PagerTool pagerTool=new PagerTool();
		//当前页数
		int pageNum=1;
		int totalRows=0;
//		
		if(StringUtils.isNotBlank(feedbackImageForm.getPageSize())){
			pageSize=Integer.valueOf(feedbackImageForm.getPageSize());
		}
		
		if(StringUtils.isNotBlank(feedbackImageForm.getCurrentPage())){
			pageNum=Integer.valueOf(feedbackImageForm.getCurrentPage());
		}
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);

		String hqlWhere = "  ";
		//组织排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.feedbacktime", "desc");
		List<String> paramsList = new ArrayList<String>();		

		if(StringUtils.isNotBlank(feedbackImageForm.getUsernumber())){
			hqlWhere += " and o.usernumber like ?";
			paramsList.add("%" + feedbackImageForm.getUsernumber() + "%");
			
		}
		if(StringUtils.isNotBlank(feedbackImageForm.getFeedbackname())){
			hqlWhere += " and o.feedbackname like ?";
			paramsList.add("%" + feedbackImageForm.getFeedbackname() + "%");			
		}	
		//预约体检时间
		if(StringUtils.isNotBlank(feedbackImageForm.getStarttime())&&StringUtils.isNotBlank(feedbackImageForm.getEndtime())){ 
			hqlWhere += "and o.feedbacktime between DATE_FORMAT(?,'%Y-%m-%d') and DATE_FORMAT( ?,'%Y-%m-%d') ";
			paramsList.add(feedbackImageForm.getStarttime());	
			paramsList.add(feedbackImageForm.getEndtime());
		}
				
		Object [] params = paramsList.toArray();
		
		List<UserImage> feedbackImagelist=dao.findPage(UserImage.class, hqlWhere, params, orderby,pagerTool);
		totalRows=pagerTool.getTotalRows();
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);
		FeedbackImageForm feedbackImagereturn=null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	
		List<FeedbackImageForm> returnlist=new ArrayList<FeedbackImageForm>();
		for(int i=0;i<feedbackImagelist.size();i++){
			UserImage userImage=feedbackImagelist.get(i);
			feedbackImagereturn=new FeedbackImageForm();
			feedbackImagereturn.setId(String.valueOf(userImage.getId()));
			feedbackImagereturn.setUsernumber(userImage.getUsernumber());
			feedbackImagereturn.setFeedbackcontent(userImage.getFeedbackcontent());
			feedbackImagereturn.setFeedbackname(userImage.getFeedbackname());
			feedbackImagereturn.setFeedbacktime(dateFormat.format(userImage.getFeedbacktime()));
			feedbackImagereturn.setSmsContent(userImage.getSmsContent());
			feedbackImagereturn.setJifen(String.valueOf(userImage.getJifen()));
			String userhql="From User a where a.number=?";
			User user=(User)dao.get(userhql, userImage.getUsernumber());
			if(user!=null){
				feedbackImagereturn.setPhonenumber(user.getPhone());
			}			
			if(userImage.getImagename()!=null&&userImage.getImagename().contains(";")){
				feedbackImagereturn.setImagename(userImage.getImagename().split(";"));

			}
			returnlist.add(feedbackImagereturn);
		}
		JSONObject result = new JSONObject();		
		result.put("totalRows", totalRows);
		result.put("totalPages", pagerTool.getTotalPages());
		
		result.put("feedbackInfo", returnlist);
		return result;
	}
	/**
	 * 对反馈建议进行打分
	 * @param feedbackImageForm
	 * @return
	 */
	public String addJifen(FeedbackImageForm feedbackImageForm) {
		try {
			String hql="Update UserImage a Set a.jifen=? where a.id=?";
			int result=dao.updateByQuery(hql,Integer.valueOf(feedbackImageForm.getJifen())
					,Integer.valueOf(feedbackImageForm.getId()) );
			if(result==0){
				return "error";
			}else
				return "success";
			
		} catch (Exception e) {
			return "error";
		}
		
	}
	/**
	 * 导出反馈信息
	 * @param feedbackImageForm
	 * @param request 
	 * @return
	 */
	public ArrayList<String> exportFeedbackImage(FeedbackImageForm feedbackImageForm) {
		
		//查询某段时间内的反馈信息
		String hqlWhere = " ";
		//组织排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.feedbacktime", "desc");
		List<String> paramsList = new ArrayList<String>();

		if(StringUtils.isNotBlank(feedbackImageForm.getUsernumber())){
			hqlWhere += " and o.usernumber like ?";
			paramsList.add("%" + feedbackImageForm.getUsernumber() + "%");
			
		}
		if(StringUtils.isNotBlank(feedbackImageForm.getFeedbackname())){
			hqlWhere += " and o.feedbackname like ?";
			paramsList.add("%" + feedbackImageForm.getFeedbackname() + "%");			
		}	
		//预约体检时间
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isNotBlank(feedbackImageForm.getStarttime())&&StringUtils.isNotBlank(feedbackImageForm.getEndtime())){ 
			hqlWhere += "and o.feedbacktime between DATE_FORMAT(?,'%Y-%m-%d') and DATE_FORMAT( ?,'%Y-%m-%d') ";
			paramsList.add(feedbackImageForm.getStarttime());	
			paramsList.add(feedbackImageForm.getEndtime());
		}
		Object [] params = paramsList.toArray();		
		List<UserImage> feedbackImagelist=dao.findPage(UserImage.class, hqlWhere, params, orderby);
		StringBuffer line=null;
		ArrayList<String> filedData = new ArrayList<String>();
		//姓名,工号,反馈内容,反馈时间,回复内容 
		 
		for(UserImage userImage:feedbackImagelist){
			line=new StringBuffer();
			line.append(userImage.getFeedbackname()+",");
			line.append(userImage.getUsernumber()+",");
			String backcontent=userImage.getFeedbackcontent();
			if(backcontent!=null&&backcontent.contains(",")){
				backcontent=userImage.getFeedbackcontent().replace(",", "，");
			}
			if(backcontent==null){
				backcontent="";
			}
			line.append(backcontent+",");
			line.append(bartDateFormat.format(userImage.getFeedbacktime())+",");
			String smscontent=userImage.getSmsContent();
			if(smscontent!=null&&smscontent.contains(",")){
				smscontent=userImage.getSmsContent().replace(",", "，");
			}
			if(smscontent==null){
				smscontent="";
			}
			line.append(smscontent+",");
			filedData.add(line.toString());
		}
		
		return filedData;
	}
	/**
	 * 根据目前的打分获取排名
	 * @param feedbackImageForm
	 * @return
	 */
	public JSONObject getFeedBackRanking(FeedbackImageForm feedbackImageForm) {
		PagerTool pagerTool=new PagerTool();
		int totalRows=0;
		//当前页数
		int pageNum=1;
		if(feedbackImageForm.getPageSize()!=null){
			pageSize=Integer.valueOf(feedbackImageForm.getPageSize());
		}		
		if(StringUtils.isNotBlank(feedbackImageForm.getCurrentPage())){
			pageNum=Integer.valueOf(feedbackImageForm.getCurrentPage());
		}
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);
		int pagestart=(pagerTool.getCurrentPage() - 1) * pagerTool.getPageSize();
		String mysql="SELECT SUM(a.jifen),a.user_number,b.nickname from  user_image a,table_user b "
				+ "where b.user_number=a.user_number group by a.user_number limit "+pagestart+","+pageSize;
		List<Object[]> list=dao.nativesql(mysql);
		List<JifenRankingForm> returnlist=new ArrayList<JifenRankingForm>();
		JSONObject result=new JSONObject();
		JifenRankingForm returnform=null;
		for(int i=0;i<list.size();i++){
			returnform=new JifenRankingForm();
			returnform.setJifen(list.get(i)[0].toString());
			returnform.setRank(String.valueOf(i+1));
			returnform.setNickname(list.get(i)[2].toString());
			returnlist.add(returnform);			
		}			
		result.put("totalRows", totalRows);
		result.put("totalPages", pagerTool.getTotalPages());
		result.put("rankinfor", returnlist);
		return result;
	}
	
}

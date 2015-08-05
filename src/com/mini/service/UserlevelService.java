package com.mini.service;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.mini.dao.CommonDao;
import com.mini.entity.Bookdictionary;
import com.mini.entity.Physicalexam;
import com.mini.entity.User;
import com.mini.entity.UserRolePopedom;
import com.mini.entity.Userlevel;
import com.mini.form.PhysicalexamForm;
import com.mini.form.UserForm;
import com.mini.form.UserlevelForm;
import com.mini.util.PagerTool;

import net.sf.json.JSONObject;

/**
 * 处理班车信息相关的业务逻辑类
 * @author 雷晓冰	2014-11-19
 */
public class UserlevelService {
	int pageSize=9;
	private boolean hasPrevious;
	private boolean hasNext;
	private CommonDao dao;
	private String userlevel_flag="角色类型";

	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	/**
	 * 获取各种管理员的信息
	 * @return
	 */
	public JSONObject getuserlevel() {
		String[] userlevelarray=findddlname(userlevel_flag);
		JSONObject result=new JSONObject();
		result.put("userlevelarray", userlevelarray);
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
	/**
	 * 保存管理员等级的权限
	 * @param userlevelForm
	 * @return
	 */
	public String  saveuserrolepopedom(UserlevelForm userlevelForm ) {
		String[] userlevelarray=findddlname(userlevel_flag);	
		String user_level=userlevelForm.getUserlevel();
		//获取页面传过来的权限
		String rolepopedom=userlevelForm.getBook()+";"+userlevelForm.getPhysical()+";"+userlevelForm.getActivity()+";"
		+userlevelForm.getFeedback()+";"+userlevelForm.getSuperAdmin()+";";
		String hql="From UserRolePopedom a where a.userlevel=?";
		List<UserRolePopedom> rolelist=dao.find(hql,user_level);
		if(rolelist==null||rolelist.size()==0){
			//说明此等级不存在权限,则执行保存
			UserRolePopedom userRolePopedom=new UserRolePopedom();
			userRolePopedom.setUserlevel(user_level);
			userRolePopedom.setPopedomcode(rolepopedom);
			String levelresult=this.getpositonnumber(userlevelarray,user_level);
			if(levelresult.equals("error")){
				return "error";
			}else{
				dao.save(userRolePopedom);
				return "success";
				
			}
		}

		String updatehql="Update UserRolePopedom a Set a.popedomcode=? where a.userlevel=? ";
		dao.updateByQuery(updatehql,rolepopedom,user_level);
		return "success";
	
	}
	/**
	 * 获取管理的等级权限信息
	 * @param userlevelForm
	 * @return
	 */
	public JSONObject getuserrolepopedom(UserlevelForm userlevelForm) {
		String userlevel=userlevelForm.getUserlevel();
		String hql="From UserRolePopedom a where a.userlevel=?";
		List<UserRolePopedom> rolelist=dao.find(hql,userlevel);
		JSONObject result=new JSONObject();
		result.put("rolelist", rolelist);
		return result;
	}
	/**
	 * 获取用户的信息
	 * @param userlevelForm 
	 * @return
	 */
	public JSONObject getuserinfo(UserlevelForm userlevelForm) {
		String[] userlevelarray=findddlname(userlevel_flag);

		PagerTool pagerTool=new PagerTool();
		//当前页数
		int pageNum=0;
		int totalRows=0;
		pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);
		if(StringUtils.isNotBlank(userlevelForm.getCurrentPage())){
			pageNum=Integer.valueOf(userlevelForm.getCurrentPage());
		}
		if(StringUtils.isNotBlank(userlevelForm.getPageSize())){
			pageSize=Integer.valueOf(userlevelForm.getPageSize());
		}		
		if(StringUtils.isNotBlank(userlevelForm.getUserlevel())&&(!userlevelForm.getUserlevel().equals("0"))){
			//说明不是查询所有
			String sql="select a.user_id,a.user_name,a.user_number,s.user_level from table_user a,user_level s where ";
			sql+="a.user_id=s.user_id  and s.user_level="+userlevelForm.getUserlevel();
			if(StringUtils.isNotBlank(userlevelForm.getUsername())){
				 sql+= " a.user_name like %"+userlevelForm.getUsername()+"%";			
			}
			if(StringUtils.isNotBlank(userlevelForm.getUsernumber())){
				sql+=  " a.user_number like '%"+userlevelForm.getUsernumber()+"% ";
			}
			pagerTool.setTotalRows(dao.nativesql(sql).size());
			if(pageNum==1){
				sql+=" limit "+String.valueOf(0)+","+String.valueOf(pageSize);
			}else{
				sql+=" limit "+String.valueOf(pageNum*pageSize-1)+","+String.valueOf(pageSize);
			}		
			List<Object[]> list=dao.nativesql(sql);
			UserForm userform=null;
			List<UserForm> returnlist=new ArrayList<UserForm>();
			for(int i=0;i<list.size();i++){
				userform=new UserForm();
				userform.setUserid(list.get(i)[0].toString());
				userform.setUsername(list.get(i)[1].toString());
				userform.setUsernumber(list.get(i)[2].toString());
				if(list.get(i)[3].toString().equals("error")){
					userform.setUserlevel("一般用户");
				}else{
					String userlevel=userlevelarray[Integer.valueOf(list.get(i)[3].toString())-1];
					userform.setUserlevel(userlevel);
				}
				returnlist.add(userform);
				
			}
			totalRows=pagerTool.getTotalRows();
			pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);
			JSONObject result = new JSONObject();		
			result.put("UserFormlist", returnlist);
			result.put("totalRows", totalRows);
			result.put("totalPages", pagerTool.getTotalPages());
			return result;
		}else{
			//查询所有
			
			String hqlWhere = "  ";
			//组织排序
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("o.number", "desc");
			List<String> paramsList = new ArrayList<String>();		
			//查询条件，
			if(StringUtils.isNotBlank(userlevelForm.getUsername())){
				hqlWhere += " and o.name like ?";
				paramsList.add("%" + userlevelForm.getUsername() + "%");
				
			}
			if(StringUtils.isNotBlank(userlevelForm.getUsernumber())){
				hqlWhere += " and o.number like ?";
				paramsList.add("%" + userlevelForm.getUsernumber() + "%");
			}
			Object [] params = paramsList.toArray();
			List<User> userlist=dao.findPage(User.class, hqlWhere, params, orderby, pagerTool);
			if(userlist==null||userlist.size()==0){
				return null;
			}
			List<UserForm> returnlist=new ArrayList<UserForm>();
			UserForm userform=null;
			for(int i=0;i<userlist.size();i++){
				//获取用户等级信息
				User user=userlist.get(i);
				userform=new UserForm();
				userform.setUserid(String.valueOf(user.getId()));
				userform.setUsername(user.getName());
				userform.setUsernumber(user.getNumber());
				String userhql="From Userlevel a where a.userid=?";
				List<Userlevel> userlevellist=dao.find(userhql,String.valueOf(user.getId()));
				if(userlevellist==null||userlevellist.size()==0){
					userform.setUserlevel("一般用户");
				}else{
					
					if(userlevellist.get(0)==null){
						userform.setUserlevel("一般用户");
					}else{
						if(userlevellist.get(0).getUserlevel().equals("error")){
							userform.setUserlevel("一般用户");
						}else{
							String userlevel=userlevelarray[Integer.valueOf(userlevellist.get(0).getUserlevel())-1];
							userform.setUserlevel(userlevel);
						}
						
						
					}
				}
							
				returnlist.add(userform);
			}
			totalRows=pagerTool.getTotalRows();
			pagerTool.init(Integer.valueOf(totalRows), pageSize, pageNum, hasPrevious, hasNext);
			JSONObject result = new JSONObject();		
			result.put("UserFormlist", returnlist);
			result.put("totalRows", totalRows);
			result.put("totalPages", pagerTool.getTotalPages());
			return result;
			
		}
		
		
		
		
		
		
	}
	/**
	 * 保存用户的等级
	 * @param userlevelForm
	 * @return
	 */
	public String savelevel(UserlevelForm userlevelForm) {
		String[] userlevelarray=findddlname(userlevel_flag);		
		String userlevel=getpositonnumber(userlevelarray,userlevelForm.getUserlevel());
		if(userlevel.equals("error")){
			return "error";
		}
		String userid=userlevelForm.getId();
		String hql="From Userlevel a where a.userid=?";
		List<Userlevel> rolelist=dao.find(hql,userid);
		if(rolelist==null||rolelist.size()==0){
			//说明此等级不存在权限,则执行保存
			Userlevel user_level=new Userlevel();
			user_level.setUserid(userid);
			user_level.setUserlevel(userlevel);			
			dao.save(user_level);
			return "success";
		}
		String updatehql="Update Userlevel a Set a.userlevel=? where a.userid=? ";
		dao.updateByQuery(updatehql,userlevel,userid);
		return "success";
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
	public JSONObject getuserrolepopedomBylogin(UserlevelForm userlevelForm,
			User user) {
		String rolehql="From Userlevel a where a.userid=?";
		List<Userlevel> rolelist=dao.find(rolehql,String.valueOf(user.getId()));
		if(rolelist==null||rolelist.size()==0){
			return null;
		}
		String userlevel=rolelist.get(0).getUserlevel();
		String hql="From UserRolePopedom a where a.userlevel=?";
		List<UserRolePopedom> returnlist=dao.find(hql,userlevel);
		JSONObject result=new JSONObject();
		result.put("rolelist", returnlist);
		return result;
	}
	
}

package com.mini.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.mini.dao.CommonDao;
import com.mini.entity.User;
import com.mini.util.PageModel;
/**
 * 处理用户相关操作的业务逻辑类
 * @author 雷晓冰 2014-11-02
 */
public class UserService {
	private CommonDao dao;

	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	/**
	 * 添加新用户
	 * @param user
	 * @return
	 */
	public boolean add(User user) {
		boolean flag = false;
		try {
			dao.save(user);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public boolean update(User user) {
		boolean flag = false;
		try {
			dao.update(user);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 修改用户密码
	 * @param id
	 * @param newPassword
	 * @return
	 */
	public boolean updatePassword(int id, String newPassword) {
		String hql = "Update User u Set u.password = ? Where u.id = ?";
		boolean flag = false;
		try {
			dao.updateByQuery(hql, newPassword, id);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public boolean delete(int id) {
		boolean flag = false;
		try {
			dao.delete(User.class, id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 根据工号查找用户
	 * @param account
	 * @return
	 */
	public User findByAccount(String account) {
		String hql = "From User u where u.account=?";
		@SuppressWarnings("unchecked")
		List<User> list = dao.find(hql, account);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	/**
	 * 根据账号和工号查找用户
	 * @param number
	 * @param nickname 
	 * @return
	 */
	public User findByAccountAndNumber(String account, String number, String nickname) {
		String hql = "From User u where u.account=? Or u.number=? Or u.nickname=?";
		@SuppressWarnings("unchecked")
		List<User> list = dao.find(hql, account, number,nickname);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 根据ID查找用户
	 * @param id
	 * @return
	 */
	public User findById(int id) {
		String hql = "From User u Where u.id = ?";
		@SuppressWarnings("unchecked")
		List<User> list = dao.find(hql, id);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	/**
	 * 获取ID~姓名键值对
	 * @param list
	 * @return
	 */
	public Map<String, String> findMap(List<Integer> idList) {
		String hql = "Select u.id, u.name From User u Where u.id IN (:idList)";
		Map<String, String> map = new HashMap<String, String>();
		try {
			Query query = dao.getFactorySession().createQuery(hql);
			query.setParameterList("idList", idList);
			@SuppressWarnings("unchecked")
			List<Object[]> list = query.list();
			for(Object[] objects : list) {
				String id = objects[0].toString();
				String name = objects[1].toString();
				map.put(id, name);
			}
		} catch (Exception e) {
			
		}
		return map;
	}
	/**
	 * 查找用户信息
	 * @param user
	 * @param limit	是否分页
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageModel<User> findByConditions(User user, boolean limit, int pageNum, int pageSize) {
		PageModel<User> model = new PageModel<User>();
		//添加查询条件
		List<Criterion> criterionList = new ArrayList<Criterion>();
		//用户姓名
		if(user.getName() != null && !user.getName().matches("\\s*")) {
			Criterion criterion = Restrictions.like("name", user.getName(), MatchMode.ANYWHERE);
			criterionList.add(criterion);
		}
		//工号
		if(user.getNumber() != null && !user.getNumber().matches("\\s*")) {
			Criterion criterion = Restrictions.like("number", user.getNumber(), MatchMode.ANYWHERE);
			criterionList.add(criterion);
		}
		//等级
		if(user.getLevel() != null) {
			Criterion criterion = Restrictions.eq("level", user.getLevel());
			criterionList.add(criterion);
		}
		//权限
		if(user.getAuthority() != null && !user.getAuthority().matches("\\s*")) {
			Criterion criterion = Restrictions.like("authority", user.getAuthority(), MatchMode.ANYWHERE);
			criterionList.add(criterion);
		}
		int size = criterionList.size();
		Criterion[] criterions = new Criterion[size];
		for(int i=0; i<size; i++) {
			criterions[i] = criterionList.get(i);
		}
		List<User> list;
		int count;
		if(limit) {
			list = dao.find(User.class, criterions, pageNum, pageSize);
			count = dao.count(User.class, criterions);
		} else {
			list = dao.find(User.class, criterions);
			count = list.size();
		}
		model.setList(list);
		model.setTotalRecords(count);
		return model;
	}
	public Object findByphone(String phone) {
		String hql = "From User u where u.phone=?";
		@SuppressWarnings("unchecked")
		List<User> list = dao.find(hql, phone);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	/**
	 * 保存昵称
	 * @param user
	 * @param nickname 
	 * @return
	 */
	public String saveNickname(User user, String nickname) {
		String hql="Update User a Set a.nickname=? where a.number=?";
		int result=dao.updateByQuery(hql, nickname,user.getNumber());
		if(result!=1){
			return "error";
		}else{
			return "success";
		}
		
	}
	public User findByUsernumber(String number) {
		String hql = "From User u where u.number=?";
		@SuppressWarnings("unchecked")
		List<User> list = dao.find(hql, number);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	public User findBytelphone(String phone) {
		String hql = "From User u where u.phone=?";
		@SuppressWarnings("unchecked")
		List<User> list = dao.find(hql, phone);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
}

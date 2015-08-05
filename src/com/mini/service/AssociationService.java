package com.mini.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mini.dao.CommonDao;
import com.mini.entity.Association;
import com.mini.entity.User;
import com.mini.util.PageModel;
/**
 * 处理协会相关操作的业务逻辑类
 * @author 雷晓冰 2014-11-02
 */
public class AssociationService {
	private CommonDao dao;

	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	/**
	 * 创建协会
	 * @param association
	 * @return
	 */
	public boolean add(Association association) {
		boolean flag = false;
		try {
			dao.save(association);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 修改协会信息
	 * @param association
	 * @return
	 */
	public boolean update(Association association) {
		boolean flag = false;
		try {
			dao.update(association);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 修改协会状态
	 * @param id
	 * @param state
	 * @return
	 */
	public boolean updateState(int id, int state) {
		boolean flag = false;
		String hql = "Update Association a Set a.state = ? Where a.id = ?";
		try {
			dao.updateByQuery(hql, state, id);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 修改协会管理员
	 * @param id
	 * @param administrators
	 * @return
	 */
	public boolean updateAdministrators(int id, String administrators) {
		String hql = "Update Association a Set a.administrators = ? Where a.id = ?";
		boolean flag = false;
		try {
			dao.updateByQuery(hql, administrators, id);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 修改协会成员
	 * @param id
	 * @param user
	 * @return
	 */
	public boolean updateMember(int id, String members, int size) {
		String hql = "Update Association a Set a.members = ?, a.size = a.size + ? Where a.id = ?";
		boolean flag = false;
		try {
			dao.updateByQuery(hql, members, size, id);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 删除协会
	 * @param id
	 * @return
	 */
	public boolean delete(int id) {
		boolean flag = false;
		try {
			dao.delete(Association.class, id);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 根据ID查找协会
	 * @param id
	 * @return
	 */
	public Association findById(int id) {
		String hql = "From Association s Where s.id = ?";
		@SuppressWarnings("unchecked")
		List<Association> list = dao.find(hql, id);
		if(list.isEmpty()) {
			return null;
		} else {
			Association association = list.get(0);
			return association;
		}
	}
	/**
	 * 获取名称对应的协会
	 * @param name
	 * @return
	 */
	public Association findByName(String name) {
		String hql = "From Association s Where s.name = ?";
		@SuppressWarnings("unchecked")
		List<Association> list = dao.find(hql, name);
		if(list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
	/**
	 * 分页获取所有的协会信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageModel<Association> findAll(int pageNo, int pageSize) {
		PageModel<Association> model = new PageModel<Association>();
		@SuppressWarnings("unchecked")
		List<Association> list = dao.find(Association.class, pageNo, pageSize);
		int count = dao.count(Association.class);
		model.setList(list);
		model.setPageNo(pageNo);
		model.setPageSize(pageSize);
		model.setTotalRecords(count);
		return model;
	}
	/**
	 * 查找协会信息
	 * @param association
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageModel<Association> findByConditions(Association association, int pageNum, int pageSize) {
		PageModel<Association> model = new PageModel<Association>();
		//添加查询条件
		List<Criterion> criterionList =new ArrayList<Criterion>();
		//名称
		if(association.getName() != null) {
			Criterion criterion = Restrictions.like("name", association.getName(), MatchMode.ANYWHERE);
			criterionList.add(criterion);
		}
		//状态
		if(association.getState() == 1) {
			Criterion criterion = Restrictions.eq("state", 1);
			criterionList.add(criterion);
		}
		//成员ID
		if(association.getMembers() != null && !association.getMembers().matches("\\s*")) {
			String members = association.getMembers();
			if(!members.endsWith("&&")) {
				members += "&&";
			}
			Criterion criterion = Restrictions.like("members", members, MatchMode.ANYWHERE);
			criterionList.add(criterion);
		}
		int size = criterionList.size();
		Criterion[] criterions = new Criterion[size];
		for(int i=0; i<size; i++) {
			criterions[i] = criterionList.get(i);
		}
		Order sizeOrder = Order.desc("size");	//根据协会成员数量排序
		Order dateOrder = Order.desc("date");	//根据成立日期排序
		Order[] orders = {sizeOrder, dateOrder};
		@SuppressWarnings("unchecked")
		List<Association> list = dao.find(Association.class, criterions, orders, pageNum, pageSize);
		int count = dao.count(Association.class, criterions);
		model.setList(list);
		model.setTotalRecords(count);
		return model;
	}
}

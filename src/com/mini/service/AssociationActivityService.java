package com.mini.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mini.dao.CommonDao;
import com.mini.entity.AssociationActivity;
import com.mini.util.PageModel;

/**
 * 处理协会活动相关操作的业务逻辑类
 * @author 雷晓冰 2014-11-02
 */
public class AssociationActivityService {
	private CommonDao dao;

	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	/**
	 * 发起协会活动
	 * @return
	 */
	public boolean add(AssociationActivity activity) {
		boolean flag = false;
		try {
			dao.save(activity);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 修改活动参加者ID
	 * @param id
	 * @param attendees
	 * @param size
	 * @return
	 */
	public boolean updateAttendee(int id, String attendees, int size) {
		String hql = "Update AssociationActivity a Set a.attendees = ?, a.size = a.size + ? Where a.id = ?";
		boolean flag = false;
		try {
			dao.updateByQuery(hql, attendees, size, id);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 根据ID查找对应的协会活动信息
	 * @param id
	 * @return
	 */
	public AssociationActivity findById(int id) {
		String hql = "From AssociationActivity a Where a.id = ?";
		@SuppressWarnings("unchecked")
		List<AssociationActivity> list = dao.find(hql, id);
		if(list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
	/**
	 * 查找协会活动
	 * @param activity
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageModel<AssociationActivity> findByConditions(AssociationActivity activity, int pageNum, int pageSize) {
		PageModel<AssociationActivity> model = new PageModel<AssociationActivity>();
		//添加查询条件
		List<Criterion> criterionList =new ArrayList<Criterion>();
		//协会ID
		if(activity.getAssociationId() != null) {
			Criterion criterion = Restrictions.eq("associationId", activity.getAssociationId());
			criterionList.add(criterion);
		}
		//协会名称
		if(activity.getAssociation() != null && !activity.getAssociation().matches("\\s*")) {
			Criterion criterion = Restrictions.like("association", activity.getAssociation(), MatchMode.ANYWHERE);
			criterionList.add(criterion);
		}
		//参加者ID
		if(activity.getAttendees() != null && !activity.getAttendees().matches("\\s*")) {
			String attendees = activity.getAttendees();
			if(!attendees.endsWith("&&")) {
				attendees += "&&";
			}
			Criterion criterion = Restrictions.like("attendees", attendees, MatchMode.ANYWHERE);
			criterionList.add(criterion);
		}
		int size = criterionList.size();
		Criterion[] criterions = new Criterion[size];
		for(int i=0; i<size; i++) {
			criterions[i] = criterionList.get(i);
		}
		Order order = Order.desc("date");
		Order[] orders = {order};
		@SuppressWarnings("unchecked")
		List<AssociationActivity> list = dao.find(AssociationActivity.class, criterions, orders, pageNum, pageSize);
		int count = dao.count(AssociationActivity.class, criterions);
		model.setList(list);
		model.setTotalRecords(count);
		return model;
	}
}

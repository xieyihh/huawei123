package com.mini.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mini.dao.CommonDao;
import com.mini.entity.Activity;
import com.mini.util.PageModel;
/**
 * 处理大型活动相关操作的业务逻辑类
 * @author 雷晓冰 2014-11-02
 */
public class ActivityService {
	private CommonDao dao;

	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	/**
	 * 添加大型活动
	 * @param activity
	 * @return
	 */
	public boolean add(Activity activity) {
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
	public boolean updateAttendee(int id, String attendees, String attendeesProperty, int size) {
		String hql = "Update Activity a Set a.attendees = ?, a.attendeesProperty = ?, a.size = a.size + ? Where a.id = ?";
		boolean flag = false;
		try {
			dao.updateByQuery(hql, attendees, attendeesProperty, size, id);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 根据ID获取对应的大型活动信息
	 * @param id
	 * @return
	 */
	public Activity findById(int id) {
		String hql = "From Activity a Where a.id = ?";
		@SuppressWarnings("unchecked")
		List<Activity> list = dao.find(hql, id);
		if(list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
	/**
	 * 查询大型活动信息
	 * @param activity
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageModel<Activity> findByConditions(Activity activity, int pageNum, int pageSize) {
		PageModel<Activity> model = new PageModel<Activity>();
		//添加查询条件
		List<Criterion> criterionList = new ArrayList<Criterion>();
		//活动名称
		if(activity.getName() != null && !activity.getName().matches("\\s*")) {
			Criterion criterion = Restrictions.like("name", activity.getName(), MatchMode.ANYWHERE);
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

		List<Activity> list = dao.find(Activity.class, criterions, orders, pageNum, pageSize);
		int count = dao.count(Activity.class, criterions);
		model.setList(list);
		model.setTotalRecords(count);
		return model;
	}
}

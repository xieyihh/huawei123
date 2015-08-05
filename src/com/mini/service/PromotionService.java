package com.mini.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mini.dao.CommonDao;
import com.mini.entity.Promotion;
import com.mini.util.PageModel;

/**
 * 促销信息相关的业务逻辑类
 * @author 雷晓冰	2014-11-29
 */
public class PromotionService {
	private CommonDao dao;

	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	/**
	 * 添加促销信息
	 * @param promotion
	 * @return
	 */
	public boolean add(Promotion promotion) {
		boolean flag = false;
		try {
			dao.save(promotion);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 删除促销信息
	 * @param id
	 * @return
	 */
	public boolean delete(int id) {
		boolean flag = false;
		try {
			dao.delete(Promotion.class, id);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 修改状态
	 * @param id
	 * @param state
	 * @return
	 */
	public boolean updateState(int id, int state) {
		boolean flag = false;
		String hql = "Update Promotion p Set p.state = ? Where p.id = ?";
		try {
			dao.updateByQuery(hql, state, id);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 修改点赞信息
	 * @param id	话题ID
	 * @param favorAmount	点赞的数量变化
	 * @param favorId		点赞的用户ID
	 * @return
	 */
	public boolean updateFavor(int id, Integer favorAmount, String favorId) {
		String hql = "Update Promotion p Set p.favorAmount = p.favorAmount + ?, p.favorId = ? Where p.id = ?";
		boolean flag = false;
		try {
			dao.updateByQuery(hql, favorAmount, favorId, id);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 根据ID查询促销信息
	 * @param id
	 * @return
	 */
	public Promotion findById(int id) {
		String hql = "From Promotion p Where p.id = ?";
		@SuppressWarnings("unchecked")
		List<Promotion> list = dao.find(hql, id);
		if(list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
	/**
	 * 查询促销信息
	 * @param promotion
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageModel<Promotion> findByConditions(Promotion promotion, int pageNum, int pageSize) {
		PageModel<Promotion> model = new PageModel<Promotion>();
		//添加查询条件
		List<Criterion> criterionList = new ArrayList<Criterion>();
		//根据状态
		if(promotion.getState() != null) {
			Criterion criterion = Restrictions.eq("state", promotion.getState());
			criterionList.add(criterion);
		}
		//根据标题或者内容
		if(promotion.getTitle() != null && !promotion.getTitle().matches("\\s*")) {
			Criterion criterion = Restrictions.or(
					Restrictions.like("title", promotion.getTitle(), MatchMode.ANYWHERE), 
					Restrictions.like("detail", promotion.getTitle(), MatchMode.ANYWHERE));
			criterionList.add(criterion);
		}
		int size = criterionList.size();
		Criterion[] criterions = new Criterion[size];
		for(int i=0; i<size; i++) {
			criterions[i] = criterionList.get(i);
		}
		Order dateOrder = Order.desc("date");		//创建日期
		Order favorAmountOrder = Order.desc("favorAmount"); 	//点赞数量
		Order[] orders = {dateOrder, favorAmountOrder};
		@SuppressWarnings("unchecked")
		List<Promotion> list = dao.find(Promotion.class, criterions, orders, pageNum, pageSize);
		int count = dao.count(Promotion.class, criterions);
		model.setList(list);
		model.setPageNo(pageNum);
		model.setTotalRecords(count);
		return model;
	}
}

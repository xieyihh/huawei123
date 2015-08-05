package com.mini.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mini.dao.CommonDao;
import com.mini.entity.Topic;
import com.mini.util.PageModel;
/**
 * 处理话题相关操作的业务逻辑类
 * @author 雷晓冰	2014-11-24
 */
public class TopicService {
	private CommonDao dao;

	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	/**
	 * 添加话题
	 * @param topic
	 * @return
	 */
	public boolean add(Topic topic) {
		boolean flag = false;
		try {
			dao.save(topic);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 删除一个话题的同时删除所有相关的评论
	 * @param id
	 * @return
	 */
	public boolean delete(int id) {
		boolean flag = false;
		String hql = "Delete From Comment c Where c.topicId = ?";
		try {
			dao.delete(Topic.class, id);
			dao.deleteByQuery(hql, id);
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
		String hql = "Update Topic t Set t.favorAmount = t.favorAmount + ?, t.favorId = ? Where t.id = ?";
		boolean flag = false;
		try {
			dao.updateByQuery(hql, favorAmount, favorId, id);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 根据ID查找对应的话题
	 * @param id
	 * @return
	 */
	public Topic findById(int id) {
		String hql = "From Topic t Where t.id = ?";
		@SuppressWarnings("unchecked")
		List<Topic> list = dao.find(hql, id);
		if(list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
	/**
	 *	查询话题
	 * @param topic
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageModel<Topic> findByConditions(Topic topic, int pageNum, int pageSize) {
		PageModel<Topic> model = new PageModel<Topic>();
		//添加查询条件
		List<Criterion> criterionList = new ArrayList<Criterion>();
		//根据标题或详情查询，关键字存储在title里面
		if(topic.getTitle() != null && !topic.getTitle().matches("\\s*")) {
			Criterion criterion = Restrictions.or(
					Restrictions.like("title", topic.getTitle(), MatchMode.ANYWHERE), 
					Restrictions.like("detail", topic.getTitle(), MatchMode.ANYWHERE));
			criterionList.add(criterion);
		}
		int size = criterionList.size();
		Criterion[] criterions = new Criterion[size];
		for(int i=0; i<size; i++) {
			criterions[i] = criterionList.get(i);
		}
		Order order = Order.desc("updateTime");
		Order[] orders = {order};
		@SuppressWarnings("unchecked")
		List<Topic> list = dao.find(Topic.class, criterions, orders, pageNum, pageSize);
		int count = dao.count(Topic.class, criterions);
		model.setList(list);
		model.setPageNo(pageNum);
		model.setTotalRecords(count);
		return model;
	}
}

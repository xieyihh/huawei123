package com.mini.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.barcodelib.barcode.a.a.a.c;
import com.mini.dao.CommonDao;
import com.mini.entity.Comment;
import com.mini.util.PageModel;

/**
 * 处理评论相关操作的业务逻辑类
 * @author 雷晓冰	2014-11-24
 */
public class CommentService {
	private CommonDao dao;

	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	/**
	 * 添加评论的同时给对应话题的评论数量加1，并且修改话题的更新时间
	 * @param comment
	 * @return
	 */
	public boolean add(Comment comment) {
		boolean flag = false;
		String hql = "Update Topic t Set t.commentAmount = t.commentAmount + 1, t.updateTime = ? Where t.id = ?";
		try {
			dao.save(comment);
			dao.updateByQuery(hql, comment.getTime(), comment.getTopicId());
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 删除一条评论的同时要给对应话题的评论数量减1
	 * @param id
	 * @param topicId
	 * @return
	 */
	public boolean delete(int id, int topicId) {
		boolean flag = false;
		String hql = "Update Topic t Set t.commentAmount = t.commentAmount - 1 Where t.id = ?";
		try {
			dao.delete(Comment.class, id);
			dao.updateByQuery(hql, topicId);
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 查询评论信息
	 * @param comment
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageModel<Comment> findByConditions(Comment comment, int pageNum, int pageSize) {
		PageModel<Comment> model = new PageModel<Comment>();
		//添加查询条件
		List<Criterion> criterionList = new ArrayList<Criterion>();
		//所属话题ID
		if(comment.getTopicId() != null) {
			Criterion criterion = Restrictions.eq("topicId", comment.getTopicId());
			criterionList.add(criterion);
		}
		//用户ID
		if(comment.getUserId() != null) {
			Criterion criterion = Restrictions.eq("userId", comment.getUserId());
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
		List<Comment> list = dao.find(Comment.class, criterions, orders, pageNum, pageSize);
		int count = dao.count(Comment.class, criterions);
		model.setList(list);
		model.setPageNo(pageNum);
		model.setTotalRecords(count);
		return model;
	}
}

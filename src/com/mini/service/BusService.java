package com.mini.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mini.dao.CommonDao;
import com.mini.entity.Bus;
import com.mini.util.PageModel;
/**
 * 处理班车信息相关的业务逻辑类
 * @author 雷晓冰	2014-11-19
 */
public class BusService {
	private CommonDao dao;

	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	/**
	 * 添加班车信息
	 * @param bus
	 * @return
	 */
	public boolean add(Bus bus) {
		boolean flag = false;
		try {
			dao.save(bus);
			flag= true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 批量添加
	 * @param list
	 * @return
	 */
	public boolean add(List<Bus> list) {
		boolean flag = false;
		try {
			for(Bus bus : list) {
				dao.save(bus);
			}
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 修改班车信息
	 * @param bus
	 * @return
	 */
	public boolean update(Bus bus) {
		boolean flag = false;
		try {
			dao.update(bus);
			flag= true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 删除班车信息
	 * @param id
	 * @return
	 */
	public boolean delete(int id) {
		boolean flag = false;
		try {
			dao.delete(Bus.class, id);
			flag= true;
		} catch (Exception e) {
			
		}
		return flag;
	}
	/**
	 * 查找班车信息
	 * @param bus
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageModel<Bus> findByConditions(Bus bus, int pageNum, int pageSize) {
		PageModel<Bus> model = new PageModel<Bus>();
		//添加查询条件
		List<Criterion> criterionList = new ArrayList<Criterion>();
		//班车号
		if(bus.getNumber() != null && !bus.getNumber().matches("\\s*")) {
			Criterion criterion = Restrictions.like("number", bus.getNumber(), MatchMode.ANYWHERE);
			criterionList.add(criterion);
		}
		//线路地点
		if(bus.getLine() != null && !bus.getLine().matches("\\s*")) {
			Criterion criterion = Restrictions.like("line", bus.getLine(), MatchMode.ANYWHERE);
			criterionList.add(criterion);
		}
		//班车类别
		if(bus.getType() != null && !bus.getType().matches("\\s*")) {
			Criterion criterion = Restrictions.like("type", bus.getType(), MatchMode.ANYWHERE);
			criterionList.add(criterion);
		}
		int size = criterionList.size();
		Criterion[] criterions = new Criterion[size];
		for(int i=0; i<size; i++) {
			criterions[i] = criterionList.get(i);
		}
		Order order = Order.asc("type");
		Order[] orders = {order};
		@SuppressWarnings("unchecked")
		List<Bus> list = dao.find(Bus.class, criterions, orders, pageNum, pageSize);
		int count = dao.count(Bus.class, criterions);
		model.setList(list);
		model.setTotalRecords(count);
		return model;
	}
}

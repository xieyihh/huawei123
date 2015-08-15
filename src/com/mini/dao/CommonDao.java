package com.mini.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.mini.entity.BookRemainingCount;
import com.mini.entity.Bookdictionary;
import com.mini.entity.Bookinfo;
import com.mini.entity.ImportBookInfo;
import com.mini.entity.User;
import com.mini.util.PageInfo;
import com.mini.util.PagerTool;
/**
 * 通用DAO的Hibernate实现
 * @author 雷晓冰 2013-10-16
 */
public class CommonDao extends HibernateDaoSupport {
    
    @Resource(name="sessionFactory")    //为父类HibernateDaoSupport注入sessionFactory的值
    public void setSuperSessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }
	
	/**
	 * @Enclosing_Method: save
	 * @Description: 保存（持久化）一个对象
	 * @param object 要保存的对象
	 * @return
	 */
	public Serializable save(Object object) {
		return getHibernateTemplate().save(object);
	}
	/**  
	* @Name: saveObjectByCollection
	* @Description: 使用集合的形式进行批量保存
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2011-12-25 （创建日期）
	* @Parameters: Collection<T> entities 集合对象
	* @Return: 无
	*/
	public void saveObjectByCollection(Collection<Bookdictionary> entities) {
		this.getHibernateTemplate().saveOrUpdateAll(entities);
	}

	/**
	 * @Enclosing_Method: update
	 * @Description: 更新一个对象 
	 * @param object 要修改的对象
	 */
	public void update(Object object) {
		getHibernateTemplate().update(object);
	}

	/**
	 * @Enclosing_Method: updateByQuery
	 * @Description: 用语句更新记录
	 * @param queryString 查询语句
	 * @param objects 参数
	 */
	@SuppressWarnings("unchecked")
	public int updateByQuery(final String queryString, final Object... objects) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery(queryString);
				if (objects != null) {
					for (int i = 0; i < objects.length; i++) {
						query.setParameter(i, objects[i]);
					}
				}
				
				return query.executeUpdate();
			}
		});
	}
	/**
	 * 使用包含IN的HQL语句查询
	 * @param hql			HQL语句
	 * @param param	参数名称
	 * @param list			使用IN对应的数组
	 */
	public void updateByList(String hql, String param, List list) {
		Query query = getSession().createQuery(hql);
		query.setParameterList(param, list);
	}

	/**
	 * @Enclosing_Method: delete
	 * @Description: 删除一个对象 
	 * @param object 要删除的对象
	 */
	public void delete(Object object) {
		getHibernateTemplate().delete(object);
	}

	/**
	 * @Enclosing_Method: delete
	 * @Description: 根据类型和对象id删除一个对象 
	 * @param clazz 类型
	 * @param id 对象id
	 */
	
	public void delete(Class clazz, Serializable id) {
		getHibernateTemplate().delete(load(clazz, id));
	}

	/**
	 * @Enclosing_Method: deleteAll
	 * @Description: 根据类型删除全部对象 
	 * @param clazz 类型
	 * @return
	 */
	public Integer deleteAll(final Class clazz) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {
						Query query = session.createQuery("delete "
								+ clazz.getName());
						return new Integer(query.executeUpdate());
					}
				});
	}
	
	/**
	 * @Enclosing_Method: getFactorySession
	 * @Description: 获取session 
	 * @return
	 */
	public Session getFactorySession(){
		return this.getSession();
	}
	
	/**
	 * @Enclosing_Method: load
	 * @Description: 根据类型和对象id载入一个对象 
	 * @param clazz 类型
	 * @param id 对象id
	 * @return 目标对象
	 */
	public Object load(Class clazz, Serializable id) {
		return getHibernateTemplate().load(clazz, id);
	}

	/**
	 * @Enclosing_Method: get
	 * @Description: 根据类型和对象id从数据库取得一个对象 
	 * @param clazz 类
	 * @param id 对象id
	 * @return 目标对象
	 */
	public Object get(Class clazz, Serializable id) {
		return this.getSession().get(clazz, id);
	}

	/**
	 * @Enclosing_Method: findByNamedQuery
	 * @Description: 命名查询 
	 * @param queryName 命名查询语句
	 * @return 对象列表
	 */
	public List findByNamedQuery(final String queryName) {
		return getHibernateTemplate().findByNamedQuery(queryName);
	}

	/**
	 * @Enclosing_Method: findByNamedQuery
	 * @Description: 依据单个参数做命名查询 
	 * @param queryName 命名查询语句
	 * @param parameter 单个查询参数
	 * @return 对象列表
	 */
	public List findByNamedQuery(final String queryName, final Object parameter) {
		return getHibernateTemplate().findByNamedQuery(queryName, parameter);
	}

	/**
	 * @Enclosing_Method: findByNamedQuery
	 * @Description: 依据参数数组做命名查询 
	 * @param queryName 命名查询语句
	 * @param objects 查询参数数组
	 * @return 对象列表
	 */
	public List findByNamedQuery(final String queryName,
			final Object... objects) {
		return getHibernateTemplate().findByNamedQuery(queryName, objects);
	}

	/**
	 * @Enclosing_Method: find
	 * @Description: 查询全部 
	 * @param queryString 查询语句
	 * @return 对象列表
	 */
	public List find(final String queryString) {
		return getHibernateTemplate().find(queryString);
	}
	

	/**
	 * @Enclosing_Method: find
	 * @Description: 带参数查询全部 
	 * @param queryString 查询语句
	 * @param objects 查询参数
	 * @return 对象列表
	 */
	public List find(final String queryString, final Object... objects) {
		return getHibernateTemplate().find(queryString, objects);
	}

	/**
	 * @Enclosing_Method: deleteByQuery
	 * @Description: 根据查询和参数删除全部对象 
	 * @param queryString 查询语句
	 * @param objects 参数
	 * @return
	 */
	public Integer deleteByQuery(final String queryString,
			final Object... objects) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {
						Query query = session.createQuery(queryString);
						if (objects != null) {
							for (int i = 0; i < objects.length; i++) {
								query.setParameter(i, objects[i]);
							}
						}
				
						return new Integer(query.executeUpdate());
						
					}
				});
	}

	/**
	 * @Enclosing_Method: get
	 * @Description: 根据查询语句和查询参数从数据库取得一个对象 
	 * @param queryString 查询语句
	 * @param objects 参数
	 * @return 单个对象
	 */
	public Object get(final String queryString, final Object... objects) {
		List list = getHibernateTemplate().find(queryString, objects);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * @Enclosing_Method: count
	 * @Description: 获得某个对象的全部总数 
	 * @param clazz 对象Class
	 * @return
	 */
	public int count(Class clazz) {
		String queryString = "SELECT COUNT(*) FROM " + clazz.getName();
		List list = getHibernateTemplate().find(queryString);
		return Integer.parseInt(list.get(0).toString());
	}
	
	/**
	 * @Enclosing_Method: count
	 * @Description: 获得某个对象的全部总数 
	 * @param queryString 查询语句
	 * @return
	 */
	public int count(String queryString) {
		if(queryString.indexOf("select")<0){
			queryString="SELECT COUNT(*) "+queryString;
		}
		List list = getHibernateTemplate().find(queryString);
		return Integer.parseInt(list.get(0).toString());
	}
	
	/**
	 * @Enclosing_Method: count
	 * @Description: 获得某个对象的全部总数 
	 * @param queryString 查询语句
	 * @param objects 参数
	 * @return
	 */
	public int count(String queryString, final Object... objects) {
		if(queryString.indexOf("select")<0){
			queryString="SELECT COUNT(*) "+queryString;
		}
		List list = getHibernateTemplate().find(queryString, objects);
		return Integer.parseInt(list.get(0).toString());
	}

	/**
	 * @Enclosing_Method: count
	 * @Description: 根据参数获得某个对象的全部总数 
	 * @param clazz 对象Class
	 * @param propertyName 参数名
	 * @param propertyValue 参数值
	 * @return
	 */
	public int count(Class clazz, String propertyName, Object propertyValue) {
		String realValue = "";
		if (true == propertyValue instanceof String) {
			realValue = "'" + propertyValue + "'";
		} else {
			realValue = propertyValue.toString();
		}

		String queryString = "SELECT COUNT(*) FROM " + clazz.getName()
				+ " o WHERE o." + propertyName + " = " + realValue;
		List list = getHibernateTemplate().find(queryString);
		return Integer.parseInt(list.get(0).toString());
	}

	/**
	 * @Enclosing_Method: count
	 * @Description: 根据约束条件获得某个对象的全部总数 
	 * @param clazz 对象Class
	 * @param criterions 约束条件
	 * @return
	 */
	public int count(final Class clazz, final Criterion[] criterions) {
		Long count = (Long) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(clazz);
						// 循环遍历添加约束条件
						for (int i = 0; i < criterions.length; i++) {
							criteria.add(criterions[i]);
						}

						return criteria.setProjection(Projections.rowCount())
								.uniqueResult();
					}
				});

		return count.intValue();
	}
	/**
	 * @Enclosing_Method: count
	 * @Description: 根据约束条件获得某个对象的全部总数 
	 * @param clazz 对象Class
	 * @param criterions 约束条件
	 * @return
	 */
	public int count(final Class clazz, final Criterion[] criterions,final String grouplist) {
		List list=  getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(clazz);
						// 循环遍历添加约束条件
						for (int i = 0; i < criterions.length; i++) {
							criteria.add(criterions[i]);
						}
						List list=criteria.setProjection(Projections.projectionList()
								
								.add(Projections.groupProperty("isbn10"))).list();
						return list;
					}
				});
		
		return Integer.valueOf(list.size());
	}


	/**
	 * @Enclosing_Method: find
	 * @Description: 获得某个类型的全部对象列表 
	 * @param clazz 对象Class
	 * @return
	 */
	public List find(final Class clazz) {
		return this.find("from " + clazz.getName());
	}

	/**
	 * @Enclosing_Method: find
	 * @Description: 根据约束条件获得某个对象的全部对象列表 
	 * @param queryString 查询语句
	 * @param criterions 约束条件
	 * @return
	 */
	public List find(final String queryString, final Criterion[] criterions) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(queryString);
				// 循环遍历添加约束条件
				for (int i = 0; i < criterions.length; i++) {
					criteria.add(criterions[i]);
				}
				return criteria.list();
			}
		});
		return list;
	}

	/**
	 * @Enclosing_Method: find
	 * @Description: 根据约束条件获得某个对象的全部对象列表 
	 * @param clazz 对象Class
	 * @param criterions 约束条件
	 * @return
	 */
	public List find(final Class clazz, final Criterion[] criterions) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(clazz);
				// 循环遍历添加约束条件
				for (int i = 0; i < criterions.length; i++) {
					criteria.add(criterions[i]);
				}
				return criteria.list();
			}
		});
		return list;
	}


	/**
	 * @Enclosing_Method: find
	 * @Description: 根据约束条件获得某个对象的全部对象排序列表 
	 * @param queryString 查询语句
	 * @param criterions 约束条件
	 * @param orders 排序条件
	 * @return
	 */
	public List find(final String queryString, final Criterion[] criterions,
			final Order[] orders) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(queryString);
				// 循环遍历添加约束条件
				for (int i = 0; i < criterions.length; i++) {
					criteria.add(criterions[i]);
				}
				for (int i = 0; i < orders.length; i++) {
					criteria.addOrder(orders[i]);
				}
				return criteria.list();
			}
		});
		return list;
	}
	
	/**  
	* @Name: orderByCondition
	* @Description: 组织排序条件
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2011-12-23 （创建日期）
	* @Parameters: LinkedHashMap<String, String> orderby 排序条件
	* @Return: String 排序语句的字符串
	*/
	private String orderByCondition(LinkedHashMap<String, String> orderby) {
		StringBuffer buffer = new StringBuffer("");
		if(orderby!=null){
			buffer.append(" order by ");
			for(Map.Entry<String, String> map:orderby.entrySet()){
				buffer.append(" " + map.getKey() + " " + map.getValue() + ",");
			}
			buffer.deleteCharAt(buffer.length()-1);
		}
		return buffer.toString();
	}
	/**  
	* @Name: setParams
	* @Description: 对where条件中的参数设置参数值
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2011-12-23 （创建日期）
	* @Parameters: Object[] params 参数值
	* @Return: 无
	*/
	private void setParams(Query query,Object[] params) {
		for(int i=0;params!=null && i<params.length;i++){
			query.setParameter(i, params[i]);
		}
	}

	/**
	 * @Enclosing_Method: find
	 * @Description: 根据约束条件获得某个对象的全部对象排序列表   
	 * @param clazz 对象Class
	 * @param criterions 约束条件
	 * @param orders 排序条件
	 * @return
	 */
	public List find(final Class clazz, final Criterion[] criterions,
			final Order[] orders) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(clazz);
				// 循环遍历添加约束条件
				for (int i = 0; i < criterions.length; i++) {
					criteria.add(criterions[i]);
				}
				for (int i = 0; i < orders.length; i++) {
					criteria.addOrder(orders[i]);
				}
				return criteria.list();
			}
		});
		return list;
	}

	/**
	 * @Enclosing_Method: find
	 * @Description: 分页查询 
	 * @param queryString 查询语句
	 * @param page 页码
	 * @param size 页容量
	 * @return
	 */
	public List find(final String queryString, final int page, final int size) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				query.setFirstResult((page - 1) * size);
				query.setMaxResults(size);
				List list = query.list();
				return list;
			}
		});
		return list;
	}
	
	/**
	 * @Enclosing_Method: find
	 * @Description: 分页查询 
	 * @param clazz 对象Class
	 * @param page 页码
	 * @param size 页容量
	 * @return
	 */
	public List find(final Class clazz, final int page, final int size) {
		return this.find("from " + clazz.getName(), page, size);
	}

	/**
	 * @Enclosing_Method: find
	 * @Description: 根据参数分页查询 
	 * @param queryString 查询语句
	 * @param propertyName 参数名
	 * @param propertyValue 参数值
	 * @param page 页码
	 * @param size 页容量
	 * @return
	 */
	public List find(final String queryString, final String[] propertyName,
			final Object[] propertyValue, final int page, final int size) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				for (int i = 0; i < propertyName.length; i++) {
					query.setParameter(propertyName[i], propertyValue[i]);
				}
				query.setFirstResult((page - 1) * size);
				query.setMaxResults(size);
				List list = query.list();
				return list;
			}
		});
		return list;
	}
	
	/**
	 * @Enclosing_Method: find
	 * @Description: 根据参数分页查询 
	 * @param clazz 查询语句
	 * @param propertyName 对象Class
	 * @param propertyValue 参数值
	 * @param page 页码
	 * @param size 页容量
	 * @return
	 */
	public List find(final Class clazz, final String[] propertyName,
			final Object[] propertyValue, final int page, final int size) {
		return this.find("from " + clazz.getName(),propertyName,propertyName, page, size);
	}

	/**
	 * @Enclosing_Method: find
	 * @Description: 根据约束条件分页查询 
	 * @param queryString 查询语句
	 * @param criterions 约束条件
	 * @param page 页码
	 * @param size 页容量
	 * @return
	 */
	public List find(final String queryString, final Criterion[] criterions,
			final int page, final int size) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(queryString);
				// 循环遍历添加约束条件
				for (int i = 0; i < criterions.length; i++) {
					criteria.add(criterions[i]);
				}
				criteria.setFirstResult((page - 1) * size);
				criteria.setMaxResults(size);
				return criteria.list();
			}
		});
		return list;
	}

	/**
	 * @Enclosing_Method: find
	 * @Description: 根据约束条件分页查询 
	 * @param queryString 查询语句
	 * @param page 页码
	 * @param size 页容量
	 * @param objects 约束条件
	 * @return
	 */
	public List find(final String queryString,
			final int page, final int size,final Object... objects) {
		
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				for (int i = 0; i < objects.length; i++) {
					query.setParameter(i, objects[i]);
				}
				query.setFirstResult((page - 1) * size);
				query.setMaxResults(size); 
				List list = query.list();
				return list;
			}
		});
		return list;
	}
	
	/**
	 * @Enclosing_Method: find
	 * @Description: 根据约束条件分页查询 
	 * @param clazz 对象Class
	 * @param criterions 约束条件
	 * @param page 页码
	 * @param size 页容量
	 * @return
	 */
	public List find(final Class clazz, final Criterion[] criterions,
			final int page, final int size) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(clazz);
				// 循环遍历添加约束条件
				for (int i = 0; i < criterions.length; i++) {
					criteria.add(criterions[i]);
				}
				criteria.setFirstResult((page - 1) * size);
				criteria.setMaxResults(size);
				return criteria.list();
			}
		});
		return list;
	}
	
	/**
	 * @Enclosing_Method: find
	 * @Description: 根据约束条件分页排序查询 
	 * @param queryString 查询语句
	 * @param criterions 约束条件
	 * @param orders 排序条件
	 * @param page 页码
	 * @param size 页容量
	 * @return
	 */
	public List find(final String queryString, final Criterion[] criterions,
			final Order[] orders,	final int page, final int size) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(queryString);
				// 循环遍历添加约束条件
				for (int i = 0; i < criterions.length; i++) {
					criteria.add(criterions[i]);
				}
				for (int i = 0; i < orders.length; i++) {
					criteria.addOrder(orders[i]);
				}
				criteria.setFirstResult((page - 1) * size);
				criteria.setMaxResults(size);
				return criteria.list();
			}
		});
		return list;
	}
	/**
	 * 分页排序查询
	 * @param clazz
	 * @param orders 排序条件
	 * @param page 页码
	 * @param size 页容量
	 * @return
	 */
	public List find(final Class clazz, final Order[] orders,	
			final int page, final int size) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(clazz);
				//添加排序条件
				for (int i = 0; i < orders.length; i++) {
					criteria.addOrder(orders[i]);
				}
				criteria.setFirstResult((page - 1) * size);
				criteria.setMaxResults(size);
				return criteria.list();
			}
		});
		return list;
	}
	/**
	 * 分页排序查询
	 * @param clazz
	 * @param orders 排序条件
	 * @param page 页码
	 * @param size 页容量
	 * @return
	 */
	public List find(final Class clazz, final Order[] orders,	
			final int page, final int size,final String group) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(clazz);
				//添加排序条件
				for (int i = 0; i < orders.length; i++) {
					criteria.addOrder(orders[i]);
				}
				criteria.setFirstResult((page - 1) * size);
				criteria.setMaxResults(size);
				//criteria.setProjection();
				return criteria.list();
			}
		});
		return list;
	}
	/**
	 * 排序查询
	 * @param clazz
	 * @param orders
	 * @return
	 */
	public List find(final Class clazz, final Order[] orders) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(clazz);
				//添加排序条件
				for (int i = 0; i < orders.length; i++) {
					criteria.addOrder(orders[i]);
				}
				return criteria.list();
			}
		});
		return list;
	}

	/**
	 * @Enclosing_Method: find
	 * @Description: 根据约束条件分页排序查询 
	 * @param clazz 对象Class
	 * @param criterions 约束条件
	 * @param orders 排序条件
	 * @param page 页码
	 * @param size 页容量
	 * @return
	 */
	public List find(final Class clazz, final Criterion[] criterions,
			final Order[] orders,	final int page, final int size) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(clazz);
				// 循环遍历添加约束条件
				for (int i = 0; i < criterions.length; i++) {
					criteria.add(criterions[i]);
				}
				//添加排序条件
				for (int i = 0; i < orders.length; i++) {
					criteria.addOrder(orders[i]);
				}
				criteria.setFirstResult((page - 1) * size);
				criteria.setMaxResults(size);
				return criteria.list();
			}
		});
		return list;
	}
	/**
	 * @Enclosing_Method: find
	 * @Description: 根据约束条件分页排序查询 
	 * @param clazz 对象Class
	 * @param criterions 约束条件
	 * @param orders 排序条件
	 * @param page 页码
	 * @param size 页容量
	 * @return
	 */
	public List findPage(final Class clazz,String hqlWhere,final Object[] params,
			 LinkedHashMap<String, String> orderby,	final PagerTool pagerTool) {
		String hql = "from " + clazz.getSimpleName() + " o where 1=1";
		//组织排序条件
		String hqlOrderBy = this.orderByCondition(orderby);
		hql = hql + hqlWhere + hqlOrderBy;
		final String finalHql = hql;
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(finalHql);
				setParams(query,params);
				pagerTool.setTotalRows(query.list().size());//设置总计路数
				query.setFirstResult((pagerTool.getCurrentPage() - 1) * pagerTool.getPageSize());//当前页中的数据从第几条开始查询
				query.setMaxResults(pagerTool.getPageSize());//当前页显示几条记录
				return query.list();
			}
		});
		return list;
	}
	/**
	 * @Enclosing_Method: find
	 * @Description: 根据约束条件分页排序查询 
	 * @param clazz 对象Class
	 * @param criterions 约束条件
	 * @param orders 排序条件
	 * @param page 页码
	 * @param size 页容量
	 * @return
	 */
	public List findPage(final Class clazz,String hqlWhere,final Object[] params,
			 LinkedHashMap<String, String> orderby) {
		String hql = "from " + clazz.getSimpleName() + " o where 1=1";
		//组织排序条件
		String hqlOrderBy = this.orderByCondition(orderby);
		hql = hql + hqlWhere + hqlOrderBy;
		final String finalHql = hql;
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(finalHql);
				setParams(query,params);		
				
				return query.list();
			}
		});
		return list;
	}

	/**
	 * @Enclosing_Method: getProjectionBean
	 * @Description: 根据投影与约束条件获得某些数据 
	 * @param clazz 对象Class
	 * @param prolist 投影
	 * @param criterions 约束条件
	 * @return
	 */
	public Object getProjectionBean(final Class clazz, final ProjectionList prolist,final Criterion[] criterions) {
		Object object = (Object) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(clazz);
						// 循环遍历添加约束条件
						if(criterions!=null){
						for (int i = 0; i < criterions.length; i++) {
							criteria.add(criterions[i]);
						}
						}
						return criteria.setProjection(prolist)
								.uniqueResult();
					}
				});

		return object;
	}
	/**
	 * 获取最大的编号
	 * @param clazz
	 * @param num	截取后面几位
	 * @return
	 */
	public String getMaxNumber(Class clazz, int num) {
		String number = null;
		num = -num;	//表示取后几位
		String hql = "Select substring(number, " + num + ") From " + clazz.getSimpleName() + " Order by substring(number, " + num + ") desc";
		List<String> list = this.getSession().createQuery(hql).list();
		if(list.size() > 0) {
			number = list.get(0);
		}
		return number;
	}

	public int executeHql(String hql) {
		Query q = this.getSession().createQuery(hql);
		return q.executeUpdate();
	}
	/**
	 * 执行带参数的SQL语句
	 * @param hql
	 * @param param
	 * @return
	 */
	public int executeHql(String hql, Object[] objects) {
		Query q = this.getSession().createQuery(hql);
		if (objects != null && objects.length > 0) {
			for (int i = 0; i < objects.length; i++) {
				q.setParameter(i, objects[i]);
			}
		}
		return q.executeUpdate();
	}
//	public List<ImportBookInfo> findCollectionByConditionNoPage(String hqlWhere,
//			final Object[] params, LinkedHashMap<String, String> orderby) {
//		/**
//		 * 组织HQL语句的Where条件
//		 *      select * from elec_text o where 1=1     放置DAO层
//				and o.textName like ?              放置Service层
//				and o.textRemark like ?
//				order by o.textDate desc , o.textName asc 
//		 */
//		String hql = "from " + "ImportBookInfo "+ " o where 1=1";
//		//组织排序条件
//		String hqlOrderBy = this.orderByCondition(orderby);
//		hql = hql + hqlWhere + hqlOrderBy;
//		final String finalHql = hql;
//		@SuppressWarnings("unchecked")
//		List<ImportBookInfo> list = (List<ImportBookInfo>)this.getHibernateTemplate().execute(new HibernateCallback(){
//            public Object doInHibernate(Session session)
//					throws HibernateException, SQLException {
//				Query query = session.createQuery(finalHql);
//				setParams(query,params);
//				return query.list();
//			}
//		});
//		return list;
//	}
	public List findCollectionByConditionNoPage(final Class clazz,String hqlWhere,
			final Object[] params, LinkedHashMap<String, String> orderby) {
		/**
		 * 组织HQL语句的Where条件
		 *      select * from elec_text o where 1=1     放置DAO层
				and o.textName like ?              放置Service层
				and o.textRemark like ?
				order by o.textDate desc , o.textName asc 
		 */
		String hql = "from " + clazz.getSimpleName()+ " o where 1=1";
		//组织排序条件
		String hqlOrderBy = this.orderByCondition(orderby);
		hql = hql + hqlWhere + hqlOrderBy;
		final String finalHql = hql;
		@SuppressWarnings("unchecked")
		List list = this.getHibernateTemplate().execute(new HibernateCallback(){
            public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(finalHql);
				setParams(query,params);
				return query.list();
			}
		});
		return list;
	}
//	public List<Bookdictionary> findCollectionByConditionNoPage1(String hqlWhere,
//			final Object[] params, LinkedHashMap<String, String> orderby) {
//		/**
//		 * 组织HQL语句的Where条件
//		 *      select * from elec_text o where 1=1     放置DAO层
//				and o.textName like ?              放置Service层
//				and o.textRemark like ?
//				order by o.textDate desc , o.textName asc 
//		 */
//		String hql = "from " + "Bookdictionary "+ " o where 1=1";
//		//组织排序条件
//		String hqlOrderBy = this.orderByCondition(orderby);
//		hql = hql + hqlWhere + hqlOrderBy;
//		final String finalHql = hql;
//		@SuppressWarnings("unchecked")
//		List<Bookdictionary> list = (List<Bookdictionary>)this.getHibernateTemplate().execute(new HibernateCallback(){
//            public Object doInHibernate(Session session)
//					throws HibernateException, SQLException {
//				Query query = session.createQuery(finalHql);
//				setParams(query,params);
//				return query.list();
//			}
//		});
//		return list;
//	}
	/**
	 * 创建分页的方法
	 * @param hqlWhere
	 * @param params
	 * @param orderby
	 * @param pageinfo
	 * @return
	 */

	public List findCollectionByConditionWithPage(final Class clazz,
			String hqlWhere,final Object[] params,
			LinkedHashMap<String, String> orderby, final PageInfo pageInfo) {
		String hql = "from " + clazz.getSimpleName()+ " o where 1=1";
		//组织排序条件
		String hqlOrderBy = this.orderByCondition(orderby);
		hql = hql + hqlWhere + hqlOrderBy;
		final String finalHql = hql;
		
		List list = this.getHibernateTemplate().execute(new HibernateCallback(){
            public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(finalHql);
				setParams(query,params);
				//添加分页功能
				pageInfo.setTotalResult(query.list().size());//通过pageInfo对象设置列表中的总记录数
				query.setFirstResult(pageInfo.getBeginResult());//当前页中的数据从第几条开始查询
				query.setMaxResults(pageInfo.getPageSize());//当前页显示几条记录
				return query.list();
			}
		});
		return list;
	}

	
	/**  
	* @Name: deleteObjectByCollection
	* @Description: 通过集合的形式删除对象

	* @Return: 无
	*/
	public void deleteObjectByCollection(Collection<Bookdictionary> entities) {
		this.getHibernateTemplate().deleteAll(entities);
	}
	//查询本地的sql
	public List<Object[]> nativesql(String sql){
		List<Object[]> result=this.getSession().createSQLQuery(sql).list();
		return result;
		
	}
	public BigInteger  nativecountsql(String sql){
		BigInteger  result=(BigInteger ) this.getSession().createSQLQuery(sql).list().get(0);
		return result;
		
	}
	public List<BookRemainingCount> countnativesql(String isbn) {
//		String sql="select count(position)-"
//				+ "(select count(*) from (select *  from book_import_infor where isbn10="
//				+ isbn+" and state='0' ) b,book_borrow_infor s where b.onlysign=s.onlysign),"
//				+ "position from book_import_infor where isbn10= "+isbn+
//				" and state='0' group by position";
//		String sql="select count(position),"
//		+ "position ,t.DdlName,t.DdlCode,name from book_import_infor LEFT JOIN (select * from book_dictionary a where a.Keyword="+"\"所在位置\""
//		+ " ) t On position=t.DdlCode where isbn10= "+isbn+
//		" and state='0' group by position";
//		String sql="select count(position)-(select count(*) from (select *  from book_import_infor where isbn10='9787108017079' and state='0' ) b,"
//				+ "book_borrow_infor s where b.onlysign=s.onlysign),position ,t.DdlName,name "+
//				"from book_import_infor LEFT JOIN (select * from book_dictionary a where a.Keyword="+"\"所在位置\""
//				+ ") t On position=t.DdlCode where isbn10='9787108017079' and state='0' group by position ";

		String sql="select re.number-IFNULL(bb.lastnumber,0),re.position as lastposition,re.DdlName,re.DdlCode,re.name "
				+ "from (select count(position) as number,position ,t.DdlName,t.DdlCode,name from book_import_infor n LEFT JOIN "
				+ "(select * from book_dictionary a "
				+ "where a.Keyword=\"所在位置\" ) t On n.position=t.DdlCode where n.isbn10= " 
				+ isbn
				+ " and n.state='0' group by n.position ) as re left join "
				+ "(select  s.borrowposition as position,count(*) as lastnumber from (select *  from book_import_infor n where "
				+ "n.isbn10="+isbn
				+ " and n.state='0' ) as ww,book_borrow_infor s where ww.onlysign=s.onlysign and s.state='1'   ) "
				+ "as bb on bb.position=re.position";
		List result=this.getSession().createSQLQuery(sql).list();
		List<BookRemainingCount> newlist=new ArrayList<BookRemainingCount>();
		BookRemainingCount bookRemainingCount=null;
		for(int i=0;i<result.size();i++){
			Object[] res=(Object[]) result.get(i);
			bookRemainingCount=new BookRemainingCount();
			if(res.length==5){
				if(res[0]!=null){
					bookRemainingCount.setCount(String.valueOf(res[0]));;
				}else{
					bookRemainingCount.setCount("0");;
				}
				
				if(res[1]!=null){
					bookRemainingCount.setPosition(String.valueOf(res[1]));
				}
				if(res[2]!=null){
					bookRemainingCount.setDdlName(String.valueOf(res[2]));
				}
				if(res[3]!=null){
					bookRemainingCount.setDdlCode(String.valueOf(res[3]));
				}
				if(res[4]!=null){
					bookRemainingCount.setBookname(String.valueOf(res[4]));
				}
			}			
			newlist.add(bookRemainingCount);
			
		}
		
		
		return newlist;
	}
	public Object getClassByproperty(Class clazz, String propertyName, Object propertyValue){
//		String hql="From User s Where s.phone =? ";
		String hql="From "+clazz.getName() +" Where " +propertyName +"=?";
		List list=find(hql, propertyValue);
		if(list==null&&list.size()==0){
			return null;
		}
		return list.get(0);
	}

	

}

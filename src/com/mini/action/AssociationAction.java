package com.mini.action;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;

import com.mini.entity.Association;
import com.mini.entity.User;
import com.mini.service.AssociationService;
import com.mini.service.UserService;
import com.mini.util.IdListUtil;
import com.mini.util.PageModel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 处理协会相关请求的Action类
 * @author 雷晓冰 2014-11-02
 */
public class AssociationAction extends BaseAction implements ModelDriven<Association> {
	private static final long serialVersionUID = -3063939930206388934L;
	private Association association = new Association();
	private AssociationService service;
	private UserService userService;
	private Integer memberId;		//成员ID
	private int pageNum;				//页号
	private int pageSize;				//行数
	
	public AssociationService getService() {
		return service;
	}
	public void setService(AssociationService service) {
		this.service = service;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setAssociation(Association association) {
		this.association = association;
	}
	@Override
	public Association getModel() {
		return association;
	}
	/**
	 * 创建协会
	 */
	public void add() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User founder = (User) session.get("user");
		this.association.setFounderId(founder.getId());
		this.association.setFounder(founder.getName());
		this.association.setDate(new Date(System.currentTimeMillis()));
		//协会创建人默认是管理员和成员
		this.association.setAdministrators(founder.getId() + "&&");
		this.association.setMembers(founder.getId() + "&&");
		this.association.setSize(1);
		//如果是普通用户或者是模块管理员但不是协会模块的管理员，则需要经过管理员审批，否则直接创建
		if(founder.getLevel() == 0 || 
				(founder.getLevel() == 1 && !founder.getAuthority().contains("association&"))) {
			this.association.setState(0);
		} else {
			this.association.setState(1);
		}
		boolean flag = this.service.add(this.association);
		if(flag) {
			if(this.association.getState() == 0) {
				msg = "协会申请成功，等待管理员审批";
			} else {
				msg = "协会创建成功";
			}
		} else {
			if(this.association.getState() == 0) {
				msg = "协会申请失败，请稍后重试";
			} else {
				msg = "协会创建失败，请稍后重试";
			}
		}
		this.association = new Association();
		map.put("success", flag);
		map.put("msg", msg);
		super.writeJson(map);
	}
	/**
	 * 判断名称对应的协会是否存在
	 */
	public void exist() {
		Association tempAssociation = service.findByName(this.association.getName());
		if(tempAssociation == null) {
			writeJson(false);
		} else {
			writeJson(true);
		}
	}
	/**
	 * 删除协会
	 */
	public void delete() {
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag = false;
		//只有管理员才有此项权限
		if(user.getLevel() == 2 || 
				(user.getLevel() == 1 && user.getAuthority().contains("discount&&"))) {
			flag = service.delete(this.association.getId());
			if(flag) {
				msg = "删除成功";
			} else {
				msg = "删除失败";
			}
		} else {
			msg = "您不是管理员，没有此项权限";
			flag = false;
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.association = new Association();
		writeJson(map);
	}
	/**
	 * 获取协会信息
	 */
	public void find() {
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		//如果是普通用户或者是模块管理员但不是协会模块的管理员，则只能查看申请通过的协会
		if(user.getLevel() == 0 || 
				(user.getLevel() == 1 && !user.getAuthority().contains("association&"))) {
			this.association.setState(1);
		}
		PageModel<Association> model = service.findByConditions(this.association, pageNum, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", model.getTotalRecords());
		map.put("list", model.getList());
		this.association = new Association();
		writeJson(map);
	}
	/**
	 * 查看协会详情
	 */
	public void viewDetail() {
		this.association = service.findById(this.association.getId());
		writeJson(this.association);
		this.association = new Association();
	}
	/**
	 * 批准协会成立
	 */
	public void authorize() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		boolean flag;
		//只有管理员才有审批的权限
		if(user.getLevel() == 0 || 
				(user.getLevel() == 1 && !user.getAuthority().contains("association&"))) {
			flag = false;
			msg = "您没有审批的权限";
		} else {
			flag = service.updateState(this.association.getId(), 1);
			if(flag) {
				msg = "审批成功";
			} else {
				msg = "审批失败，请稍后再试";
			}
		}
		this.association = new Association();
		map.put("msg", msg);
		map.put("success", flag);
		writeJson(map);
	}
	/**
	 * 加入协会
	 */
	public void join() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		boolean flag;
		this.association = service.findById(this.association.getId());
		if(this.association == null) {
			msg = "协会不存在，操作失败";
			flag = false;
		} else {
			//将用户ID加入协会成员中
			String members = this.association.getMembers() + user.getId() + "&&";
			flag = service.updateMember(this.association.getId(), members, 1);
			if(flag) {
				msg = "加入成功";
			} else {
				msg = "无法加入，请稍后重试";
			}
		}
		this.association = new Association();
		map.put("success", flag);
		map.put("msg", msg);
		writeJson(map);
	}
	/**
	 * 退出协会
	 */
	public void quit() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		boolean flag;
		this.association = service.findById(this.association.getId());
		if(this.association == null) {
			msg = "协会不存在，操作失败";
			flag = false;
		} else {
			//如果是管理员，则需要删除管理员中包含的用户ID
			if(this.association.getAdministrators().indexOf(user.getId() + "&&") >= 0) {
				String administrators = this.association.getAdministrators().replaceAll(user.getId() + "&&", "");
				flag = service.updateAdministrators(this.association.getId(), administrators);
			}
			//删除协会成员中包含的用户ID
			String members = this.association.getMembers().replaceAll(user.getId() + "&&", "");
			flag = service.updateMember(this.association.getId(), members, -1);
			if(flag) {
				msg = "退出成功";
			} else {
				msg = "无法退出，请稍后重试";
			}
		}
		this.association = new Association();
		map.put("success", flag);
		map.put("msg", msg);
		writeJson(map);
	}
	/**
	 * 设置一个成员作为协会管理员
	 */
	public void setAdmin() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag;
		this.association = service.findById(this.association.getId());
		if(this.association == null) {
			msg = "协会不存在，操作失败";
			flag = false;
		} else {
			String administrators = this.association.getAdministrators() + this.memberId + "&&";
			flag = service.updateAdministrators(this.association.getId(), administrators);
			if(flag) {
				msg = "管理员设置成功";
				map.put("administrators", administrators);
			} else {
				msg = "管理员设置失败，请稍后重试";
			}
		}
		this.association = new Association();
		this.memberId = null;
		map.put("success", flag);
		map.put("msg", msg);
		writeJson(map);
	}
	/**
	 * 收回一个成员的管理权限
	 */
	public void cancelAdmin() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag;
		this.association = service.findById(this.association.getId());
		if(this.association == null) {
			msg = "协会不存在，操作失败";
			flag = false;
		} else {
			String administrators = this.association.getAdministrators();
			administrators = administrators.replaceAll(this.memberId + "&&", "");
			flag = service.updateAdministrators(this.association.getId(), administrators);
			if(flag) {
				msg = "管理员权限成功被收回";
				map.put("administrators", administrators);
			} else {
				msg = "管理员权限收回失败，请稍后重试";
			}
		}
		this.association = new Association();
		this.memberId = null;
		map.put("success", flag);
		map.put("msg", msg);
		writeJson(map);
	}
	/**
	 * 获取协会管理员的姓名
	 */
	public void getAdministrators() {
		List<Integer> administrators = IdListUtil.string2List(this.association.getAdministrators());
		Map<String, String> map = userService.findMap(administrators);
		this.association = new Association();
		writeJson(map);
	}
	/**
	 * 获取协会成员的姓名
	 */
	public void getMembers() {
		List<Integer> members = IdListUtil.string2List(this.association.getMembers());
		Map<String, String> map = userService.findMap(members);
		this.association = new Association();
		writeJson(map);
	}

}

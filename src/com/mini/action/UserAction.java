package com.mini.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;

import com.mini.entity.User;
import com.mini.form.UserForm;
import com.mini.service.UserService;
import com.mini.util.PageModel;
import com.mini.util.PswUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 处理用户相关请求的Action类
 * @author 雷晓冰 2014-11-02
 */
public class UserAction extends BaseAction implements ModelDriven<User> {
	private static final long serialVersionUID = 6899644660260463334L;
	private User user = new User();
	private UserService service;
	private String message;
	private String oldPassword;
	private String newPassword;
	private int pageNum;				//页号
	private int pageSize;				//数量
	private boolean limit = true;		//是否分页
	private String module;		//管理员所属模块名称
	//用于传递User类属性值的属性
	private Integer userId;
	private Integer userLevel;
	private String userNumber;
	private String userName;
	private String userAccount;
	private String userPhone;
	private String userRemark;
	private static final String LOGIN_SUCCESS = "login_success";	//登录成功
	private static final String LOGIN_FAILED = "login_failed";		//登录失败
	private static final String LOGOUT = "logout";
	private static final String REGISTER_SUCCESS = "register_success";	//注册成功
	private static final String REGISTER_FAILED = "register_failed";		//注册失败
	private String prePage;
	public String getPrePage() {
		return prePage;
	}
	public void setPrePage(String prePage) {
		this.prePage = prePage;
	}
	public UserService getService() {
		return service;
	}
	public void setService(UserService service) {
		this.service = service;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
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
	public boolean isLimit() {
		return limit;
	}
	public void setLimit(boolean limit) {
		this.limit = limit;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserRemark() {
		return userRemark;
	}
	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public User getModel() {
		return user;
	}
	/**
	 * 用户登录
	 * @return
	 */
	public String login() {
		//System.out.println(this.user.getAccount());
		
		User tempUser = service.findByAccount(this.user.getAccount());
		//System.out.println(this.user.getPassword()+";"+tempUser.getSalt());
		//System.out.println(PswUtil.str2Sha1(this.user.getPassword(), tempUser.getSalt()));
		if(tempUser == null || 
				!tempUser.getPassword().equals(PswUtil.str2Sha1(this.user.getPassword(), tempUser.getSalt()))) {
			this.message = "用户名或密码有误，请重新输入";
			this.user = new User();
			return LOGIN_FAILED;
		}
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		this.user = new User();
		session.put("user", tempUser);
		
		//获取跳转到登陆界面之前的页面地址，由拦截器提供
        prePage = (String) session.get("prePage");
        //清除session中的数据
        session.remove("prePage");
        if(prePage==null){
        	//不是拦截器跳转到登陆页面的，直接访问的登陆页面
        	return LOGIN_SUCCESS;
        }else{
        	return "yes";
        }
		
	}
	/**
	 * 通过Ajax方式登录
	 */
	public void loginByAjax() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean success = false;
		User tempUser = service.findByAccount(this.user.getAccount());
		if(tempUser == null || 
				!tempUser.getPassword().equals(PswUtil.str2Sha1(this.user.getPassword(), tempUser.getSalt()))) {
			msg = "用户名或密码有误，请重新输入";
			success = false;
		} else {
			msg = "登录成功";
			success = true;
			ActionContext context = ActionContext.getContext();
			SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
			session.put("user", tempUser);
			map.put("user", tempUser);
		}
		this.user = new User();
		map.put("success", success);
		map.put("msg", msg);
		writeJson(map);
	}
	/**
	 * 退出
	 * @return
	 */
	public String logout() {
		this.user = new User();
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		session.put("user", null);
		session.clear();
		return LOGOUT;
	}
	/**
	 * 判断账号是否已经存在
	 */
	public void exist() {
		User tempUser = service.findByAccountAndNumber(this.user.getAccount(), this.user.getNumber());
		Map<String, Object> map = new HashMap<String, Object>();
		if(tempUser != null) {
			map.put("exist", true);
			if(tempUser.getAccount().equals(this.user.getAccount())) {
				map.put("field", "account");
				map.put("name", "账号");
			} else {
				map.put("field", "number");
				map.put("name", "工号");
			}
		} else {
			map.put("exist", false);
		}
		writeJson(map);
	}
	/**
	 * 注册新用户
	 * @return
	 */
	public String register() {
		//判断用户账号是否存在
		if(service.findByAccount(this.user.getAccount()) != null) {
			this.message = "账号：" + this.user.getAccount() + "已经存在";
			return REGISTER_FAILED;
		}
		if(service.findByphone(this.user.getPhone()) != null) {
			this.message = "电话：" + this.user.getPhone() + "已经存在";
			return REGISTER_FAILED;
		}
		
		String salt = PswUtil.getRandomSalt();
		String password = PswUtil.str2Sha1(user.getPassword(), salt);
		user.setSalt(salt);
		user.setPassword(password);
		user.setLevel(0);
		user.setAuthority("");
		boolean flag = service.add(user);
		if(flag) {
			this.message = "注册成功，请登录";
			return REGISTER_SUCCESS;
		} else {
			this.message = "注册失败，请稍后重试";
			return REGISTER_FAILED;
		}
	}
	/**
	 * 获取当前用户信息
	 */
	public void getCurrent() {
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		writeJson(user);
	}
	/**
	 * 根据ID获取用户信息
	 */
	public void findById() {
		User user = service.findById(this.userId);
		this.userId = null;
		writeJson(user);
	}
	/**
	 * 查询用户信息
	 */
	public void find() {
		this.parseUser();
		Map<String, Object> map = new HashMap<String, Object>();
		PageModel<User> model = service.findByConditions(this.user, limit, pageNum, pageSize);
		map.put("total", model.getTotalRecords());
		map.put("list", model.getList());
		this.user = new User();
		writeJson(map);
	}
	/**
	 * 修改用户信息
	 */
	public void update() {
		this.parseUser();
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag;
		//如果修改了账号，则检验新的账号是否已经存在
		if(!user.getAccount().equals(this.user.getAccount()) 
				&& (this.service.findByAccount(this.user.getAccount()) != null)) {
			msg = "账号：" + this.user.getAccount() + "已经存在";
			flag = false;
		} else {
			user.setName(this.user.getName());
			user.setAccount(this.user.getAccount());
			user.setNumber(this.user.getNumber());
			user.setPhone(this.user.getPhone());
			user.setRemark(this.user.getRemark());
			flag = service.update(user);
			if(flag) {
				msg = "保存成功";
				session.put("user", user);
			} else {
				msg = "保存失败，请稍后重试";
			}
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.user = new User();
		writeJson(map);
	}
	/**
	 * 修改密码
	 */
	public void updatePassword() {
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag;
		//验证旧密码
		if(!user.getPassword().equals(PswUtil.str2Sha1(this.oldPassword, user.getSalt()))) {
			msg = "旧密码有误";
			flag = false;
		} else {
			this.newPassword = PswUtil.str2Sha1(this.newPassword, user.getSalt());
			flag = service.updatePassword(user.getId(), this.newPassword);
			if(flag) {
				msg = "密码修改成功";
				user.setPassword(this.newPassword);
				session.put("user", user);
			} else {
				msg = "密码修改失败，请稍后重试";
			}
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.oldPassword = null;
		this.newPassword = null;
		writeJson(map);
	}
	/**
	 * 设置管理员
	 */
	public void addAdmin() {
		this.parseUser();
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag;
		this.user = service.findById(this.user.getId());
		String authority = this.user.getAuthority();
		if(authority == null) {
			authority = "";
		}
		authority += this.module + "&&";
		this.user.setAuthority(authority);
		this.user.setLevel(1);
		flag = service.update(this.user);
		if(flag) {
			msg = "管理员设置成功";
		} else {
			msg = "管理员设置失败，请稍后重试";
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.user = new User();
		writeJson(map);
	}
	/**
	 * 取消一个用户的管理员权限
	 */
	public void cancelAdmin() {
		this.parseUser();
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag;
		this.user = service.findById(this.user.getId());
		String authority = this.user.getAuthority().replaceAll(this.module + "&&", "");
		this.user.setAuthority(authority);
		if(this.user.getAuthority().equals("")) {
			this.user.setLevel(0);
		}
		flag = service.update(this.user);
		if(flag) {
			msg = "管理员取消成功";
		} else {
			msg = "管理员取消失败，请稍后重试";
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.user = new User();
		writeJson(map);
	}
	/**
	 * 根据user**属性设置this.user对象的相应属性值
	 */
	private void parseUser() {
		if(this.userId == null) {
			this.user.setId(0);
		} else {
			this.user.setId(userId);
		}
		this.user.setLevel(userLevel);
		this.user.setNumber(userNumber);
		this.user.setName(userName);
		this.user.setAccount(userAccount);
		this.user.setPhone(userPhone);
		this.user.setRemark(userRemark);
		this.userId = null;
		this.userLevel = null;
		this.userNumber = null;
		this.userName = null;
		this.userAccount = null;
		this.userPhone = null;
		this.userRemark = null;
	}
	/**
	 * 删除用户
	 */
	public void delete() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag = service.delete(this.userId);
		if(flag) {
			msg = "删除成功";
		} else {
			msg = "删除失败";
		}
		this.userId = null;
		map.put("success", flag);
		map.put("msg", msg);
		writeJson(map);
	}
	/**
	 * 导出用户信息
	 * @throws IOException 
	 */
	public void exportUser() throws IOException {
		this.user = new User();
		List<User> list = service.findByConditions(user, false, pageNum, pageSize).getList();
		ActionContext context = ActionContext.getContext();
		HttpServletResponse response = (HttpServletResponse) context.get(ServletActionContext.HTTP_RESPONSE);
		response.setContentType("application/octet-stream");
		response.setBufferSize(4096);
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户信息.xlsx", "UTF-8"));
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache");
		getExportStream(list, response.getOutputStream());
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	/**
	 * 获取文件输出流
	 * @param list
	 * @param stream
	 */
	public void getExportStream(List<User> list, OutputStream stream) {
		Workbook workbook = new XSSFWorkbook();
		//表头格式
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		Font headerFont = workbook.createFont();
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerStyle.setFont(headerFont);
		//内容格式
		CellStyle bodyStyle = workbook.createCellStyle();
		bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		Sheet sheet = workbook.createSheet();
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("姓名");
		row.createCell(1).setCellValue("微信号");
		row.createCell(2).setCellValue("工号");
		row.createCell(3).setCellValue("电话");
		this.setCellStyle(row, headerStyle);
		int count = 1;
		for(User tempUser : list) {
			row = sheet.createRow(count);
			row.createCell(0).setCellValue(tempUser.getName());
			row.createCell(1).setCellValue(tempUser.getAccount());
			row.createCell(2).setCellValue(tempUser.getNumber());
			row.createCell(3).setCellValue(tempUser.getPhone());
			this.setCellStyle(row, bodyStyle);
			count++;
		}
		try {
			workbook.write(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将一行的单元格设为相同的格式
	 * @param row
	 * @param style
	 */
	private void setCellStyle(Row row, CellStyle style) {
		int first = row.getFirstCellNum();
		int last = row.getLastCellNum();
		while(first < last) {
			row.getCell(first).setCellStyle(style);
			first++;
		}
	}
	/**
	 * 获取员工姓名和工号
	 * @param row
	 * @param style
	 */
	public void getuserinfo() {
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		UserForm userform=new UserForm();
		if(user==null){
			returnObject("noUser");
		}else{
			userform.setUsername(user.getName());
			userform.setUsernumber(user.getNumber());
			JSONObject result=new JSONObject();
			result.put("userinfo", userform);
			returnObject(result);
		}
		
		
	}
}

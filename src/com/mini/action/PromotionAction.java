package com.mini.action;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;

import com.mini.entity.Promotion;
import com.mini.entity.User;
import com.mini.service.PromotionService;
import com.mini.util.PageModel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 处理促销信息相关请求的Action类
 * @author 晓冰
 *
 */
public class PromotionAction extends BaseAction implements ModelDriven<Promotion>  {
	private static final long serialVersionUID = 3081988003487389909L;
	private Promotion promotion = new Promotion();
	private PromotionService service;
	private int pageNum;				//页号
	private int pageSize;				//数量
	private int thumbnailWidth;	//缩略图宽度
	private String photoDirectory;		//存放照片的目录
	
	public PromotionService getService() {
		return service;
	}
	public void setService(PromotionService service) {
		this.service = service;
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
	public int getThumbnailWidth() {
		return thumbnailWidth;
	}
	public void setThumbnailWidth(int thumbnailWidth) {
		this.thumbnailWidth = thumbnailWidth;
	}
	public String getPhotoDirectory() {
		return photoDirectory;
	}
	public void setPhotoDirectory(String photoDirectory) {
		this.photoDirectory = photoDirectory;
	}
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
	@Override
	public Promotion getModel() {
		return this.promotion;
	}
	/**
	 * 加载打折信息
	 */
	public void find() {
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		//如果当前用户是管理员，加载所有的打折信息；普通用户只加载审核通过的打折信息
		if(user.getLevel() == 0 || 
				(user.getLevel() == 1 && !user.getAuthority().contains("discount&&&"))) {
			this.promotion.setState(1);
		}
		PageModel<Promotion> model = service.findByConditions(this.promotion, pageNum, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", model.getTotalRecords());
		map.put("list", model.getList());
		this.promotion = new Promotion();
		writeJson(map);
	}
	/**
	 * 根据ID查看具体的打折信息
	 */
	public void findById() {
		Promotion promotion = service.findById(this.promotion.getId());
		this.promotion = new Promotion();
		writeJson(promotion);
	}
	/**
	 * 发布促销信息
	 */
	public void add() {
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		Date date = new Date(System.currentTimeMillis());
		this.promotion.setDate(date);
		this.promotion.setPublisherId(user.getId());
		this.promotion.setPublisher(user.getName());
		this.promotion.setFavorAmount(0);
		this.promotion.setFavorId("");
		//从Session获取之前上传的照片路径
		this.promotion.setPhotoUrl((String) session.get("photoUrl"));
		if(this.promotion.getPhotoUrl() == null) {
			this.promotion.setPhotoUrl("");
		}
		session.put("photoUrl", null);
		//管理员发布的促销信息不需要审核，普通用户发布的需要审核
		if(user.getLevel() == 2 || 
				(user.getLevel() == 1 && user.getAuthority().contains("discount&&"))) {
			this.promotion.setState(1);
		} else {
			this.promotion.setState(0);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag = service.add(this.promotion);
		if(flag) {
			if(this.promotion.getState() == 1) {
				msg = "打折信息发布成功";
			} else {
				msg = "打折信息发布成功，等待管理员审核";
			}
		} else {
			msg = "打折信息发布失败，请稍后重试";
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.promotion = new Promotion();
		writeJson(map);
	}
	/**
	 * 上传照片
	 */
	public void uploadPhoto() {
		Integer random = new Double(Math.random() * 10000).intValue();			//获取随机数
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
		StringBuffer filenameBuffer = new StringBuffer();
		filenameBuffer.append(format.format(new Date(System.currentTimeMillis())))
								.append("_")
								.append(random)
								.append(uploadFileName.substring(uploadFileName.lastIndexOf(".")));		//加上后缀名
		String filename = filenameBuffer.toString();
		String directoryPath = ServletActionContext.getServletContext().getRealPath("/" + this.getPhotoDirectory()) + "/";
		try {
			this.uploadFile(upload, directoryPath, filename, thumbnailWidth);
		} catch (IOException e) {
			System.out.println("文件：" + filename + "上传失败");
		}
		String filepath = this.getPhotoDirectory() + "/" + filename;
		//将临时的照片路径存入Session
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		String photoUrl = (String) session.get("photoUrl");
		if(photoUrl == null) {
			photoUrl = filepath;
		} else {
			photoUrl += "&&" + filepath;
		}
		session.put("photoUrl", photoUrl);
		writeJson(true);
	}
	/**
	 * 批准一项打折信息
	 */
	public void authorize() {
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		Map<String, Object> map = new HashMap<String, Object>();
		String msg;
		boolean flag = false;
		//只有管理员才有此项权限
		if(user.getLevel() == 0 || 
				(user.getLevel() == 1 && !user.getAuthority().contains("discount&&&"))) {
			msg = "您不是管理员，没有此项权限";
			flag = false;
		} else {
			flag = service.updateState(this.promotion.getId(), 1);
			if(flag) {
				msg = "批准成功";
			} else {
				msg = "审批失败，请稍后重试";
			}
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.promotion = new Promotion();
		writeJson(map);
	}
	/**
	 * 删除打折信息
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
			//先删除照片
			String directoryPath = ServletActionContext.getServletContext().getRealPath("/") + "/";
			StringTokenizer tokenizer = new StringTokenizer(this.promotion.getPhotoUrl(), "&&");
			while(tokenizer.hasMoreElements()) {
				String token = tokenizer.nextToken();
				if(!token.equals("")) {
					File photo = new File(directoryPath + token);
					photo.delete();
				}
			}
			//再删除数据
			flag = service.delete(this.promotion.getId());
			if(flag) {
				msg = "删除成功";
			} else {
				msg = "删除失败，请稍后重试";
			}
		} else {
			msg = "您不是管理员，没有此项权限";
			flag = false;
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.promotion = new Promotion();
		writeJson(map);
	}
	/**
	 * 给一个话题点赞
	 */
	public void favor() {
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		boolean flag = false;
		Promotion promotion = service.findById(this.promotion.getId());
		if(promotion.getFavorId().contains(user.getId() + "&&")) {
			flag = false;
			msg = "已经赞过，不能重复点赞";
		} else {
			String favorId = promotion.getFavorId() + user.getId() + "&&";
			flag = service.updateFavor(this.promotion.getId(), 1, favorId);
			if(flag) {
				msg = "点赞成功";
			} else {
				msg = "点赞失败，请稍后重试";
			}
		}
		map.put("success", flag);
		map.put("msg", msg);
		this.promotion = new Promotion();
		writeJson(map);
	}
}

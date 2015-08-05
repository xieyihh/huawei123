/*** 
 *  @Program Name  : gkjsj
 *  @Written by    : OD_Team
 *  @version       : Copyright (c) 2010 by OD_Team v1.00
***/
package com.mini.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.LazyInitializationException;

import com.alibaba.fastjson.JSON;
import com.mini.util.ThumbnailUtil;
import com.opensymphony.xwork2.ActionSupport;

/*** 
 *  @Program Name  : gkjsj.com.gkjsj.action.BaseAction.java
 *  @version       : v1.00
 *  @Description   : 定义一些XML、JSON数据的转化操作
 ***/

public class BaseAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	public File upload;
	public String uploadFileName;
	public String savePath;	//保存上传文件的路径
	//返回到前台的JSON字符串
	public String jsonString;
	private HttpServletRequest request=null;
	private HttpServletResponse response=null;

	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getSavePath() {
		return ServletActionContext.getServletContext().getRealPath("/" + savePath);
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	/**
	 * @Enclosing_Method: outJsonString
	 * @Description: 将JSON返回到前台
	 * @param str
	 */
	public void outJsonString(String str) {
		getResponse().setContentType("text/javascript;charset=UTF-8");
		outString(str);
	}

	/**
	 * @Enclosing_Method: outString
	 * @Description: 将字符串返回到前台
	 * @param str
	 */
	public void outString(String str) {
		try {
			PrintWriter out = getResponse().getWriter();
			if(str.startsWith("[")){
				str=str.substring(1, str.length()-1);
			}
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Enclosing_Method: outXMLString
	 * @Description: 将XML格式的数据返回到前台
	 * @param xmlStr
	 */
	public void outXMLString(String xmlStr) {
		getResponse().setContentType("application/xml;charset=UTF-8");
		outString(xmlStr);
	}

	/**
	 * @Enclosing_Method: getRequest
	 * @Description:获取HttpServletRequest对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * @Enclosing_Method: getResponse
	 * @Description: 获得Response对象
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * @Enclosing_Method: getSession
	 * @Description: 获取Session对象
	 * @return
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * @Enclosing_Method: getServletContext
	 * @Description: 获取ServletContext对象
	 * @return
	 */
	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	/**
	 * @Enclosing_Method: getRealyPath
	 * @Description: 获取项目路径
	 * @param path
	 * @return
	 */
	public String getRealyPath(String path) {
		return getServletContext().getRealPath(path);
	}
	
	/**
	 * @Enclosing_Method: Map2json
	 * @Description: 将Map对象转化为JSON格式的数据
	 * @param map
	 * @return
	 */
	public String Map2json(Map map){
		if(map!=null)
		{
			JSONArray jsonarray = JSONArray.fromObject(map);
			return jsonarray.toString();
		}
			
		
		return "";
	}
	
	/**
	 * @Enclosing_Method: Obj2json
	 * @Description: 将Object转成Json格式
	 * @param obj
	 * @return
	 */
	public String Obj2json(Object obj){
		if(obj!=null)
		{
			JSONObject jsonObj = JSONObject.fromObject(obj);
			return jsonObj.toString();
		}
			
		
		return "";
	}
	
	public void writeJson(Object object) {
		try {
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			ServletActionContext.getResponse().getWriter().write(json);
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void resultError(String code,
			String message) {
		JSONObject result = new JSONObject();
		result.put("code", code);		
		result.put("message", message);
		result.put("context", "fail");
		writeJson(result);
	}
	public void returnObject(Object object){
		JSONObject result = new JSONObject();
		result.put("code", "200");
		result.put("message", "success");
		result.put("context", object);
//		System.out.print(result.toString());
		writeJson(result);
	}
	/**
	 * 上传Excel文件
	 * @return
	 */
	public File uploadExcelFile() {
		Integer random = new Double(Math.random() * 10000).intValue();			//获取随机数
		String extension = uploadFileName.substring(uploadFileName.lastIndexOf("."));	//获取文件的扩展名
		String filename = new Long(System.currentTimeMillis()).toString() + "-" + random.toString() + extension;
		//如果保存上传文件的目录不存在，新建一个
		File directory = new File(getSavePath());
		if(!directory.exists()) {
			directory.mkdir();
		}
		File excel = new File(getSavePath() + "\\" + filename);
		//保存上传的文件
		try {
			FileOutputStream outputStream = new FileOutputStream(excel);
			FileInputStream inputStream = new FileInputStream(getUpload());
			byte[] buffer = new byte[1024];
			int length = 0;
			while((length = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
			}
			outputStream.close();
			inputStream.close();
		} catch(IOException e) {
			return null;
		}
		return excel;
	}
	/**
	 * 上传文件
	 * @return
	 */
	public void uploadFile(File inputFile, String directoryPath, String filename, int thumbnailWidth) throws IOException {
		File directory = new File(directoryPath);
		if(!directory.exists()) {
			directory.mkdir();
		}
		File outputFile = new File(directoryPath + filename);
		//如果需要生成缩略图就产生缩略图并保存，否则保存原图
		if(ThumbnailUtil.generateThumbnail(inputFile, outputFile, thumbnailWidth)) {
			return;
		}
		FileOutputStream outputStream = new FileOutputStream(outputFile);
		FileInputStream inputStream = new FileInputStream(inputFile);
		byte[] buffer = new byte[1024];
		int length = 0;
		while((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}
		outputStream.close();
		inputStream.close();
	}
	
	
}
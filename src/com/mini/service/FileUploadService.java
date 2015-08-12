package com.mini.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import sun.misc.BASE64Decoder;

import com.mini.dao.CommonDao;
import com.mini.entity.User;
import com.mini.entity.UserImage;
import com.mini.form.FileUploadlForm;
import com.mini.util.PageModel;
/**
 * 处理用户相关操作的业务逻辑类
 * @author 雷晓冰 2014-11-02
 */
public class FileUploadService {
	private CommonDao dao;

	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	
	public String getimageupload(User user, FileUploadlForm fileUploadlForm) throws Exception {
		if(user==null){
			//说明没有登录
			UserImage useriamge=new UserImage();
            useriamge.setUsernumber(fileUploadlForm.getUsernumber());
            useriamge.setFeedbackname(fileUploadlForm.getFeedbackname());
            useriamge.setFeedbackcontent(fileUploadlForm.getFeedbackcontent());

            
            useriamge.setJifen(0);
            useriamge.setFeedbacktime(new Date());
            dao.save(useriamge);  
			return "noUser";
		}else{
			UserImage useriamge=null;
			String filenameflag="";//本次上传的标志位
			if(fileUploadlForm.getImagename()==null||fileUploadlForm.getImagename()==""){
				useriamge=new UserImage();
	            useriamge.setUsernumber(user.getNumber());
	            useriamge.setFeedbackname(user.getName());
	            useriamge.setFeedbackcontent(fileUploadlForm.getFeedbackcontent());
	            useriamge.setFeedbacktime(new Date());
	            useriamge.setJifen(0);
	            dao.save(useriamge);  
	        	return "success";			
				
			}else{
				String[] imagename=fileUploadlForm.getImagename().split("%;-%");
				String[] imagedata=fileUploadlForm.getImagedata().split("%;-%");
				System.out.println(imagename);
				if(imagename==null||imagename.length==0){
					useriamge=new UserImage();
		            useriamge.setUsernumber(user.getNumber());
		            useriamge.setFeedbackname(user.getName());
		            useriamge.setFeedbackcontent(fileUploadlForm.getFeedbackcontent());
		            useriamge.setFeedbacktime(new Date());
		            useriamge.setJifen(0);
		            dao.save(useriamge);  
		        	return "success";
				}
				for(int i=0;i<imagename.length;i++){
					 String path="c:/image/problemimage/";
					 File dir = new File(path);
			        	// 创建文件夹
		        	 if (!dir.exists()) {
		        		dir.mkdirs();
		        	 }
		        	String prefix=imagename[i].substring(imagename[i].lastIndexOf(".")+1);
		        	String filename=user.getNumber()+UUID.randomUUID()+"."+prefix;
		        	File newfile = new File(path+filename);		        	
		        	if(!newfile.exists()){
		        		newfile.createNewFile();
	        	    }
		        	String imageData=imagedata[i];
		        	imageData = imageData.substring(30);
		        	imageData = URLDecoder.decode(imageData,"UTF-8");
		        	byte[] data = decode(imageData);
		    		// 写入到文件
		        	FileOutputStream  fo = new FileOutputStream(newfile);	 
		        	fo.write(data);
		        	fo.flush();
		        	fo.close();
		        	filename="/problemimage/"+filename;
		        	filenameflag=filenameflag+filename+";"   ;
		        	
				}
				useriamge=new UserImage();
	            useriamge.setImagename(filenameflag);
	            useriamge.setUsernumber(user.getNumber());
	            useriamge.setFeedbackname(user.getName());
	            useriamge.setFeedbackcontent(fileUploadlForm.getFeedbackcontent());
	            useriamge.setFeedbacktime(new Date());
	            useriamge.setJifen(0);
	            dao.save(useriamge);
	            return "success";
			}
			
		}
	}
	private byte[] decode(String imageData) throws IOException{
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] data = decoder.decodeBuffer(imageData);
		for(int i=0;i<data.length;++i)
	    {
	        if(data[i]<0)
	        {
	        	//调整异常数据
	            data[i]+=256;
	        }
	    }
		//
		return data;
	}
	
}

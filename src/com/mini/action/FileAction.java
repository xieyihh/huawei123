package com.mini.action;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;

import com.mini.entity.AssociationActivity;
import com.mini.entity.User;
import com.mini.entity.UserImage;
import com.mini.form.FileUploadlForm;
import com.mini.form.ImportBookForm;
import com.mini.service.ActivityService;
import com.mini.service.FileUploadService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class FileAction  extends BaseAction implements ModelDriven<FileUploadlForm>{
	private FileUploadlForm fileUploadlForm=new FileUploadlForm();
	private FileUploadService service;

	 
	public FileUploadService getService() {
		return service;
	}

	public void setService(FileUploadService service) {
		this.service = service;
	}

	public FileUploadlForm getModel() {
		// TODO Auto-generated method stub
		return fileUploadlForm;
	}

    public void imageupload() throws Exception {
        
    	ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");

		String result=service.getimageupload(user,fileUploadlForm);
		if(result.equals("success")){
			returnObject("success");
		}else{
			returnObject("noUser");
		}
       

    }

}
package com.mini.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.sf.json.JSONObject;

import com.mini.dao.CommonDao;
import com.mini.entity.SecurityCode;
import com.mini.entity.User;
import com.mini.entity.UserImage;
import com.mini.form.DuanXinForm;
import com.mini.form.FeedbackImageForm;
import com.mini.util.PswUtil;
import com.mini.util.SmsServer;

public class SmsService {
	
	private CommonDao dao;
	private String sendmessage="您的验证码为";
	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 保存短信的验证码
	 * @param duanXinForm
	 * @param user 
	 * @return
	 * @throws Exception 
	 */
	public String savesecuritycode(DuanXinForm duanXinForm, User user) throws Exception {
		 //判断此手机号是否注册
		 String phonenumber=duanXinForm.getPhone();
		 int exituser=dao.count(User.class,"phone",phonenumber);
		 if(exituser==0){
			 return "user_not_exit";
		 }
		 //用户存在，则生成验证码
		 //生成一个四位数的验证码		 
		 String code=generateWord();
		 //System.out.println(code);		 
		 SecurityCode securityCode=new SecurityCode();
		 securityCode.setCode(code);
		 securityCode.setPhone(phonenumber);
		//如果验证码已经存在，则更新，不存在则
		 int count=dao.count(SecurityCode.class,"phone",phonenumber);
		 if(count!=0){
			 //说明验证码已存在，则更新
			 String hql ="Update SecurityCode a Set a.code=? where a.phone=?";
			 dao.updateByQuery(hql,code,phonenumber);
		 }else{
			 dao.save(securityCode);
		 }
		 SmsServer smsServer=new SmsServer();
		 String result=smsServer.SendSms(phonenumber, sendmessage+code);
		 if(result.equals("success")){
			 return "success";
		 }
		return "error";
	}
	private String generateWord() {  
        String[] beforeShuffle = new String[] {"0", "1","2", "3", "4", "5", "6", "7",  
                "8", "9", "a", "b", "b", "d", "e", "f", "g", "h", "i", "j",  
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",  
                "w", "x", "y", "z" };  
        List list = Arrays.asList(beforeShuffle);  
        Collections.shuffle(list);  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < list.size(); i++) {  
            sb.append(list.get(i));  
        }  
        String afterShuffle = sb.toString();  
        String result = afterShuffle.substring(5, 9);  
        return result;  
    }  
	/**
	 * 判断验证码是否正确
	 * @param duanXinForm
	 * @return
	 */
	public String getJudgesecuritycode(DuanXinForm duanXinForm) {
		String code=duanXinForm.getValicode();
		String phone=duanXinForm.getPhone();
		
		String hql="From SecurityCode s Where s.phone =? ";
		List<SecurityCode> codelist=dao.find(hql, phone);
		if(codelist==null&&codelist.size()==0){
			return "error";
		}
		SecurityCode securityCode=codelist.get(0);
		if(!securityCode.getCode().equals(code)){
			return "error";
		}
		User user=getUserByphone(phone);
		if(user==null)
			return "error";
		return user.getAccount();

	}
	public User getUserByphone(String phone){
		String hql="From User s Where s.phone =? ";
		List<User> usrlist=dao.find(hql, phone);
		if(usrlist==null&&usrlist.size()==0){
			return null;
		}
		return usrlist.get(0);
	}

	/**
	 * 修改密码
	 * @param duanXinForm
	 * @return
	 */
	public String updatepassword(DuanXinForm duanXinForm) {
		String salt = PswUtil.getRandomSalt();
		String password = PswUtil.str2Sha1(duanXinForm.getPassword(), salt); 
		User user=(User)dao.getClassByproperty(User.class,"account",duanXinForm.getAccount());
		if(user==null)
			return "error";
		String hql ="Update User a Set a.password=?,a.salt=? where a.id=?";
		dao.updateByQuery(hql,password,salt,user.getId());

		return "success";
	}
	/**
	 * 对用户的反馈发送短信
	 * @param feedbackImageForm
	 * @return
	 */
	public String feedBackSendSms(FeedbackImageForm feedbackImageForm) {
		SmsServer smsServer=new SmsServer();
		 //发送短信，保存积分
		//UserImage feedback=(UserImage)dao.get(UserImage.class, Integer.valueOf(feedbackImageForm.getId()));
		
		String hql="Update UserImage a Set a.smsContent=? where a.id=?";
		dao.updateByQuery(hql,feedbackImageForm.getSmsContent(),Integer.valueOf(feedbackImageForm.getId()) );
		String result;
		try {			
			result = smsServer.SendSms(feedbackImageForm.getPhonenumber(),"感谢您对吾爱武家建议平台的支持,您的反馈建议处理如下："+ feedbackImageForm.getSmsContent());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "error";
		}
		 if(result.equals("success")){
			 return "success";
		 }
		return "error";
	}
	
	
	


}

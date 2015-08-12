package com.mini.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



/*
功能:		 HTTP接口 发送短信

说明:		http://api.duanxin.cm/?ac=send&username=用户账号&password=MD5位32密码&phone=号码&content=内容
状态:
	100 发送成功
	101 验证失败
	102 短信不足
	103 操作失败
	104 非法字符
	105 内容过多
	106 号码过多
	107 频率过快
	108 号码内容空
	109 账号冻结
	110 禁止频繁单条发送
	111 系统暂定发送
	112 号码不正确
	120 系统升级
*/

public class SmsServer {
	// 向StringBuffer追加用户名
	private String username="70207421";
	private String password="18627907745";
	
			
	/**
	 * 发送短信接口
	 * @param context
	 * @throws IOException 
	 */
	public String  SendSms(String phone,String content) throws IOException{
		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer("http://api.duanxin.cm/?");	
		sb.append("action=send&username="+username);
		// 向StringBuffer追加密码（密码采用MD5 32位 小写）
		sb.append("&password="+encryption(password));
		// 向StringBuffer追加手机号码
		sb.append("&phone="+phone);
		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content="+URLEncoder.encode(content));
		// 创建url对象
		URL url = new URL(sb.toString());

		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");

		// 发送
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		// 返回发送结果
		String resultline = in.readLine();
//		System.out.println(resultline);
		if(resultline.equals("100")){
			return "success";
		}
		return "error";
	}
	/**
	 * 发送短信接口
	 * @param context
	 * @throws IOException 
	 */
	public String  GetSms(String phone,String content) throws IOException{
		
		return "error";
	}
	 /**
     * 
     * @param plainText
     *            明文
     * @return 32位密文
     */
    public String encryption(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
 
            int i;
 
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
 
            re_md5 = buf.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }
}
package com.mini.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.mini.util.PswUtil;

/**
 * 处理微信请求的Action类
 * @author 雷晓冰	2014-12-02
 */
public class WechatAction extends BaseAction {
	private static final long serialVersionUID = -4139771650064370712L;
	private String echostr;		//微信传过来的随即字符串
	
	public String getEchostr() {
		return echostr;
	}
	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}
	/**
	 * 处理微信消息的入口
	 */
	public void process() {
		String result;
		//如果是微信的激活验证，直接返回随机字符串
		if(this.echostr != null) {
			result = this.echostr;
		} else {
			result = processMsg();
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(result);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			System.out.println("消息回复出异常");
		}
		System.out.println("消息回复成功！！！");
		this.echostr = null;
	}
	/**
	 * 处理用户发的普通消息
	 * @return
	 */
	private String processMsg() {
		String result = new String();
		String xml = readMsg();
		Map<String, String> requestMap = parseXml(xml);
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("ToUserName", requestMap.get("FromUserName"));
		responseMap.put("FromUserName", requestMap.get("ToUserName"));
		String content = "请点击链接注册，体验班车查询、协会活动、讨论交流、打折信息等丰富多彩的功能！http://119.29.36.111/register.jsp";
		if(requestMap.get("MsgType").equals("text")) {	//发送的是文本消息
			responseMap.put("MsgType", "text");
			responseMap.put("Content", content);
			responseMap.put("MsgId", System.currentTimeMillis() + PswUtil.getRandomStr(6));
		} else if(requestMap.get("Event").equals("subscribe")) {	//用户新关注
			System.out.println("新关注的用户：" + requestMap.get("FromUserName"));
			responseMap.put("MsgType", "text");
			responseMap.put("Content", "您好，欢迎关注吾爱吾家！" + content);
			responseMap.put("MsgId", System.currentTimeMillis() + PswUtil.getRandomStr(6));
		}
		result = formatXmlResponse(responseMap);
		System.out.println("回复的消息打包完毕");
		return result;
	}
	/**
	 * 读取用户发送过来的XML消息体
	 * @return
	 */
	private String readMsg() {
		String xml = "";
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			InputStream stream = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			StringBuffer buffer = new StringBuffer();
			String line;
			while((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			xml = buffer.toString();
			reader.close();
			stream.close();
		} catch (IOException e) {
			
		}
		return xml;
	}
	/**
	 * 解析XML字符串
	 * @param xml
	 * @return
	 */
	private Map<String, String> parseXml(String xml) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			@SuppressWarnings("unchecked")
			Iterator<Element> iterator = root.elementIterator();
			while(iterator.hasNext()) {
				Element element = iterator.next();
				map.put(element.getName(), element.getText());
			}
		} catch (DocumentException e) {
			
		}
		return map;
	}
	/**
	 * 封装返回给用户的消息
	 * @param map
	 * @return
	 */
	private String formatXmlResponse(Map<String, String> map) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<xml><ToUserName><![CDATA[")
					.append(map.get("ToUserName"))
					.append("]]></ToUserName><FromUserName><![CDATA[")
					.append(map.get("FromUserName"))
					.append("]]></FromUserName><CreateTime>")
					.append(System.currentTimeMillis())
					.append("</CreateTime><MsgType><![CDATA[")
					.append(map.get("MsgType"))
					.append("]]></MsgType><Content><![CDATA[")
					.append(map.get("Content"))
					.append("]]></Content></xml>");
		return buffer.toString();
	}
}
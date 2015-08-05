package com.mini.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.mini.service.TokenThreadService;
import com.mini.util.WeixinUtil;

/**
 * 初始化servlet
 * 
 */
public class InitServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
//
//	public void init() throws ServletException {
//		// 获取web.xml中配置的参数
//		TokenThreadService.appid = getInitParameter("appid");
//		TokenThreadService.appsecret = getInitParameter("appsecret");
//
//		log.info("weixin api appid:{}", TokenThreadService.appid);
//		log.info("weixin api appsecret:{}", TokenThreadService.appsecret);
//
//		// 未配置appid、appsecret时给出提示
//		if ("".equals(TokenThreadService.appid) || "".equals(TokenThreadService.appsecret)) {
//			log.error("appid and appsecret configuration error, please check carefully.");
//		} else {
//			// 启动定时获取access_token的线程
//			new Thread(new TokenThreadService()).start();
//		}
//	}
}
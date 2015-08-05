package com.mini.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mini.dao.CommonDao;
import com.mini.entity.AccessToken;
import com.mini.util.WeixinUtil;

/**
 * 定时获取微信access_token的线程
 * 
 */
//public class TokenThreadService implements Runnable {
//	private static Logger log = LoggerFactory.getLogger(TokenThreadService.class);
//	// 第三方用户唯一凭证，
//	public static String appid = "wxcc6e2694f7871912";
//	// 第三方用户唯一凭证密钥
//	public static String appsecret = "b5952d97bf973e7e843391fe428ea249";
//	public static AccessToken accessToken = null;
	
//	public void run() {
//		while (true) {
//			try {
//				accessToken = WeixinUtil.getAccessToken(appid, appsecret);
//				if (null != accessToken) {
//					log.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getToken());
//					// 休眠7000秒                                  
//					System.out.println("获取access_token成功，有效时长{}秒 token:{}"+accessToken.getExpiresIn()+accessToken.getToken());
//					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
//				} else {
//					// 如果access_token为null，60秒后再获取
//					Thread.sleep(60 * 1000);
//				}
//			} catch (InterruptedException e) {
//				try {
//					Thread.sleep(60 * 1000);
//				} catch (InterruptedException e1) {
//					log.error("{}", e1);
//				}
//				log.error("{}", e);
//			}
//		}
//	}
//}
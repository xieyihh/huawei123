package com.mini.entity;



/**
 * 接口访问凭证
 * 
 */
public class AccessToken {
	// 获取到的凭证
	private String id;
	private String token;
	// 凭证有效时间，单位：秒
	private int expiresIn;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}
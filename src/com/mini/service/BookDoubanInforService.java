package com.mini.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.mini.dao.CommonDao;
import com.mini.entity.Bookinfo;
import com.mini.entity.ImportBookInfo;

public class BookDoubanInforService {
	private CommonDao dao;

	public CommonDao getDao() {
		return dao;
	}
	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
	
	public void savedoubanbookinfo(String isbn) {
		// TODO Auto-generated method stub
		ImportBookInfo bookimportinfo=new ImportBookInfo();
		bookimportinfo.setIsbn10("2233");
		bookimportinfo.setName("2233");
		bookimportinfo.setPosition("2233");;
		bookimportinfo.setImportdate(new Date());;
		bookimportinfo.setSource("2233");
		bookimportinfo.setOnlysign("2233");
        dao.save(bookimportinfo);
	}
	public String finddoubanbookinfo(String isbn) {
		// TODO Auto-generated method stub
		String hql = "From ImportBookInfo s Where s.isbn10 = ?";
		//查找信息
		List<ImportBookInfo> list=dao.find(hql,isbn);
	
		return null;
	}

}

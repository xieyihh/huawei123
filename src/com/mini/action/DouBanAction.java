package com.mini.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;








import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.mini.entity.Bookinfo;
import com.opensymphony.xwork2.ModelDriven;

public class DouBanAction extends BaseAction implements ModelDriven<Bookinfo>{

	@Override
	public Bookinfo getModel() {
		// TODO Auto-generated method stub
		return null;
	}

}

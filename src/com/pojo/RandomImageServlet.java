package com.pojo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RandomImageServlet
 */
public class RandomImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//图片宽度
	 int width=0;
	 //图片高度
	 int height=0;
	 //图片上随机字符个数
	 int randomStrNum=0;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RandomImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
@Override
	public void init() throws ServletException {
	// TODO Auto-generated method stub
	super.init();
	//获取宽度
	  width=Integer.parseInt(this.getInitParameter("width"));
	  //获取高度
	  height=Integer.parseInt(this.getInitParameter("height"));
	  //获取个数
	  randomStrNum=Integer.parseInt(this.getInitParameter("num"));
	  System.out.print(randomStrNum);
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		  //获取HttpSession对象
		  HttpSession session=request.getSession();
		  //获取随机字符串
		  String randomStr=CreateCodeImage.random(randomStrNum);
		  if(null!=session){
		   //设置参数
		   session.setAttribute("randomStr", randomStr);
		   //设置响应类型,输出图片客户端不缓存
		   response.setDateHeader("Expires", 1L);  
		   response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		   response.addHeader("Pragma", "no-cache");
		   response.setContentType("image/jpeg"); 
		   //输出到页面
		   CreateCodeImage.render(randomStr, response.getOutputStream(), width, height);
		 }
	}
}


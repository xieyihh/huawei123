package com.mini.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;

import com.mini.entity.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
/**
 * 检测用户是否已经登录的拦截器
 * @author 雷晓冰	2013-06-12
 */
public class LoginInterceptor extends MethodFilterInterceptor {
	private static final long serialVersionUID = 1L;
	private static final String NOT_LOGIN = "not_login";

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		ActionContext context = ActionContext.getContext();
		SessionMap<String, Object> session = (SessionMap<String, Object>) context.getSession();
		User user = (User) session.get("user");
		HttpServletRequest request = ServletActionContext.getRequest();
		// 获取此请求的地址，请求地址包含application name，进行subString操作，去除application name
		String[] requesturl=request.getRequestURI().split("/");
		
        String path = requesturl[requesturl.length-1];
		//如果用户尚未登录就拦截用户的请求
		if(user == null) {		
			
            // 获得请求中的参数
            String queryString = request.getQueryString();
            // 预防空指针
            if (queryString == null) {
                queryString = "";
            }
            // 拼凑得到登陆之前的地址
            String realPath = path+"?"+queryString;
            session.put("prePage", realPath);
			HttpServletResponse response = ServletActionContext.getResponse();
			if (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with").equalsIgnoreCase(
							"XMLHttpRequest"))// 如果是ajax请求响应头会有，x-requested-with；
			{
				response.setHeader("sessionstatus", "timeout");// 在响应头设置session状态
				return null;
			} else {
				return NOT_LOGIN;
			}
		}
		String result = invocation.invoke();
//		if(path.equals("excelAdd.action")){
//			//判断权限是否足够
//			if(user.getLevel()<2){
//				//权限不足
//				session.put("prePage", "forum.jsp");
//				return "nolevel";
//			}
//			
//		}
		return result;
	}
}

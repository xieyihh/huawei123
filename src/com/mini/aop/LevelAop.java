package com.mini.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class LevelAop {
	public void JudgeLevel(ProceedingJoinPoint  joinPoint){

		Object[] args = joinPoint.getArgs(); 
		HttpServletRequest request = null; 
	
		for (int i = 0; i < args.length; i++) { 
			if (args[i] instanceof HttpServletRequest) {
				 request = (HttpServletRequest) args[i];  
			}
		}
		String[] requesturl=request.getRequestURI().split("/");
		
        String path = requesturl[requesturl.length-1];
        System.out.println(joinPoint.getSignature().getName());
		System.out.println("切面配置成功");
		
		
	}

}

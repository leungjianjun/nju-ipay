package com.ipay.server.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class AjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
	/**
	 * 重写spring security自带的登录失败处理方法,使其同时支持ajax和form.ajax登录失败后将返回json的失败数据
	 */
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
		if (request.getContentType().contains("application/json")) {
			response.setStatus(400);
	        response.getWriter().print("{\"status\":\"false\"}");
	        response.getWriter().flush();
		}else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}

}

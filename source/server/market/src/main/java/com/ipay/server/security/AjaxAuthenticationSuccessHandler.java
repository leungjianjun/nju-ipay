package com.ipay.server.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	/**
	 * 重写spring security自带的登录成功处理方法,使其同时支持ajax和form.ajax登录成功后将返回json的成功数据
	 */
	public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		if (request.getContentType().contains("application/json")) {
			response.getWriter().print("{\"status\":\"true\"}");
			response.getWriter().flush();
		} else {
			super.onAuthenticationSuccess(request, response, auth);
		}
	}
}

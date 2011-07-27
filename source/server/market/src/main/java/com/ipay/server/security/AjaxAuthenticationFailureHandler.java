package com.ipay.server.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class AjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
		if (request.getContentType().contains("application/json")) {
	        response.getWriter().print("{\"status\":\"false\"}");
	        response.getWriter().flush();
		}else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}

}

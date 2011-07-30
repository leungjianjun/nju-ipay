package com.ipay.server.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AjaxUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		System.out.println("=====================================?");
		return super.attemptAuthentication(request, response);
	}

}

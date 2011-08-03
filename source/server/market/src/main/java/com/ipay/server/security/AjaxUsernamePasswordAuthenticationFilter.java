package com.ipay.server.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AjaxUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	LoginParam loginParam;
	
	/**
	 * 重写spring security 自带的登录认证，是登录认证支持json格式的ajax登录
	 */
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		//如果是json的登录就添加对json的支持
		System.out.println("================================attempt authentication");
		if (request.getContentType().contains("application/json")) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				loginParam = mapper.readValue(request.getReader(),LoginParam.class);
			} catch (Exception e) {
				loginParam = new LoginParam();
			}
		}else{
			loginParam = new LoginParam();
			loginParam.setAccount(request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY));
			loginParam.setPassword(request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY));
		}
		
		return super.attemptAuthentication(request, response);
		
	}
	
	protected String obtainPassword(HttpServletRequest request) {
        return loginParam.getPassword();
    }


    protected String obtainUsername(HttpServletRequest request) {
        return loginParam.getAccount();
    }
	
	static class LoginParam{
		private String account;
		private String password;
		
		public String getAccount(){
			return this.account;
		}
		
		public void setAccount(String account){
			this.account = account;
		}
		
		public String getPassword(){
			return this.password;
		}
		
		public void setPassword(String password){
			this.password = password;
		}
	}
	

}

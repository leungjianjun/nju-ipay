package com.ipay.server.web;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipay.server.entity.User;
import com.ipay.server.security.PasswordEncoder;
import com.ipay.server.service.IUserService;
import com.ipay.server.service.ServiceException;

/**
 * 处理用户权限,登录登出
 * @author ljj
 *
 */
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private IUserService<User> userService;

	public IUserService<User> getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(IUserService<User> userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value="/client/logout",method=RequestMethod.GET)
	public @ResponseBody Object clientLogout(HttpSession session,Principal principal){
		logger.info("client "+principal.getName()+" logout");
		session.invalidate();
		return Collections.singletonMap("status", true);
	}
	
	@RequestMapping(value="/client/changePassword",method=RequestMethod.GET)
	public @ResponseBody Object changePassword(@RequestBody Map<String, String> param,Principal principal){
		User user = userService.getUser(principal.getName(), 
				PasswordEncoder.encode(param.get("oldPassword"),principal.getName()));
		user.setPassword(PasswordEncoder.encode(param.get("newPassword"),principal.getName()));
		userService.update(user);
		//防御式
		return Collections.singletonMap("status", true);
	}
	
	/**
	 * 统一异常处理方法，把所有的service exception放在这里处理，返回json风格
	 * @param exception
	 * 			ServiceException异常
	 * @param response
	 * 			http响应
	 * @return
	 * 			错误的json风格消息
	 */
	@ExceptionHandler(ServiceException.class)
	public @ResponseBody Map<String, String> handleServiceException(ServiceException exception,HttpServletResponse response){
		response.setStatus(exception.getHttpStatusCode());
		Map<String, String> failureMessages = new HashMap<String, String>();
		failureMessages.put("status", "false");
		failureMessages.put("error", exception.getMessage());
		return failureMessages;
	}

}

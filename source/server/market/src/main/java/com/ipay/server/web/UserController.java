package com.ipay.server.web;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipay.server.entity.User;
import com.ipay.server.service.IUserService;

/**
 * 处理用户权限,登录登出
 * @author ljj
 *
 */
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private IUserService<User> userService;

	public IUserService<User> getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(IUserService<User> userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value="/client/logout",method=RequestMethod.GET)
	public @ResponseBody Object clientLogout(HttpSession session){
		session.invalidate();
		return Collections.singletonMap("status", true);
	}

}

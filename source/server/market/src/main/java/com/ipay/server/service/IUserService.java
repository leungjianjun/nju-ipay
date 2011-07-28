package com.ipay.server.service;

import com.ipay.server.entity.Authority;
import com.ipay.server.entity.User;

/**
 * <p>提供用户服务的接口</p>
 * 
 * 用户服务包括用户权限设置
 * @author ljj
 *
 * @param <T>
 */
public interface IUserService<T extends User> extends IService<T> {
	
	public void createAuthority(Authority authority);
	
	public Authority getAuthority(String name);

}

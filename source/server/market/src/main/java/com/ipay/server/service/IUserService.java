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
	
	/**
	 * 新建一个权限
	 * 
	 * @param authority
	 */
	public void createAuthority(Authority authority);
	
	/**
	 * 根据权限名字获取权限实体
	 * 
	 * @param name
	 * @return
	 */
	public Authority getAuthority(String name);
	
	/**
	 * 根据帐号密码获取用户,注意密码的哈希化在controller层完成
	 * 
	 * @param account
	 * 			用户帐号
	 * @param password
	 * 			哈希过的的密码
	 * @return
	 */
	public T getUser(String account,String password);

}

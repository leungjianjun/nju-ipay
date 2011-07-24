package com.ipay.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 该实体包含一个权限的信息
 * @author ljj
 *
 */
@Entity
public class Authority extends BaseEntity {
	/**
	 * SpringSecurity中默认的角色/授权名前缀.
	 */
	public static final String AUTHORITY_PREFIX = "ROLE_";

	/**
	 * 权限名
	 */
	@Column(nullable = false, unique = true)
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String getPrefixedName() {
		return AUTHORITY_PREFIX + name;
	}
}

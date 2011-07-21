package com.ipay.server.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.common.collect.Lists;

@Entity
@Inheritance(strategy = InheritanceType.JOINED )
public class User extends BaseEntity {
	
	@NotNull
	@Size(min = 2, max = 25)
	private String password;
	
	@NotNull
	@Size(min = 2, max = 25)
	@Column(nullable = false, unique = true)
	private String account;
	
	/**
	 * 权限管理控制
	 */
	@ManyToMany
	@JoinTable
	@Fetch(FetchMode.SUBSELECT)
	private List<Authority> authorityList = Lists.newArrayList();

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public List<Authority> getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(List<Authority> authorityList) {
		this.authorityList = authorityList;
	}

}

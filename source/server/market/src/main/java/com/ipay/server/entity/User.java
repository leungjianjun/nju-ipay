package com.ipay.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

}

package com.ipay.server.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * 该实体包含顾客的详细信息
 * @author ljj
 *
 */
@Entity
public class ClientInfo extends BaseEntity {
	
	/**
	 * 手机号
	 */
	@NotNull
	private String phonenum;
	
	/**
	 * 真实姓名,实名制
	 */
	@NotNull
	private String realname;
	
	/**
	 * 注册时间
	 */
	@Temporal(value = TemporalType.TIMESTAMP)
	@NotNull
	private Date createDate;

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}

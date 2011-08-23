package com.ipay.server.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User extends BaseEntity{
	
	/**
	 * 银行开户的帐号默认为信用卡号，可以修改
	 */
	@NotNull
	@Size(min = 2, max = 25)
	@Column(nullable = false, unique = true)
	private String account;
	
	/**
	 * 密码使用sha加密
	 */
	@NotNull
	@Size(min = 2, max = 25)
	private String password;
	
	/**
	 * 信用需要个人信息，用户的真实姓名，可直接从身份证复印件获取
	 */
	private String realname;
	
	/**
	 * 地址，用户的住址
	 */
	private String address;
	
	/**
	 * 身份证号
	 */
	private String idnumber;
	
	/**
	 * 电话号码，需要联系用户
	 */
	private String phonenum;
	
	/**
	 * 备注信息
	 */
	private String remark;
	
	/**
	 * 出生年月日
	 */
	@Temporal(value = TemporalType.DATE)
	@NotNull
	private Date birthday;
	
	/**
	 * 性别，男真女假
	 * 
	 */
	private boolean sex;
	
	/**
	 * 帐号是否可用
	 */
	private boolean enbale = true;
	
	/**
	 * 用户的信用卡
	 */
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn
	private CreditCard creditCard;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public boolean isEnbale() {
		return enbale;
	}

	public void setEnbale(boolean enbale) {
		this.enbale = enbale;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	

}

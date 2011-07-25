package com.ipay.server.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.collect.Sets;

/**
 * 该实体包含商场的详细信息
 * @author ljj
 *
 */
@Entity
public class MarketInfo extends BaseEntity {
	
	/**
	 * 投诉电话
	 */
	private String complainPhone;
	
	/**
	 * 介绍
	 */
	private String introduction;
	
	/**
	 * 地址
	 */
	private String location;
	
	/**
	 * 商场名
	 */
	private String name;
	
	/**
	 * 服务电话
	 */
	private String servicePhone;
	
	/**
	 * 注册时间
	 */
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createDate;
	
	/**
	 * 特价商品列表
	 */
	@OneToMany(fetch = FetchType.LAZY)
	private Set<SpecialProduct> specialProducts = Sets.newHashSet();

	public String getComplainPhone() {
		return complainPhone;
	}

	public void setComplainPhone(String complainPhone) {
		this.complainPhone = complainPhone;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Set<SpecialProduct> getSpecialProducts() {
		return specialProducts;
	}

	public void setSpecialProducts(Set<SpecialProduct> specialProducts) {
		this.specialProducts = specialProducts;
	}

}

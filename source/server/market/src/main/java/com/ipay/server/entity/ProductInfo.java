package com.ipay.server.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.google.common.collect.Sets;

/**
 * 该实体包含独立于商场的商品信息,是所有商店共用的商品信息,如条形码,产地,介绍等
 * @author ljj
 *
 */
@Entity
public class ProductInfo extends BaseEntity {
	
	/**
	 * 品牌,出版社
	 */
	private String banner;
	
	/**
	 * 条形码
	 */
	private String barcode;
	
	/**
	 * 商品名称
	 */
	private String name;
	
	/**
	 * 商品属性,不要包括上面已经列出的上行啊
	 */
	@OneToMany
	@JoinColumn
	private Set<Attribute> atttributes = Sets.newHashSet();

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Attribute> getAtttributes() {
		return atttributes;
	}

	public void setAtttributes(Set<Attribute> atttributes) {
		this.atttributes = atttributes;
	}


}

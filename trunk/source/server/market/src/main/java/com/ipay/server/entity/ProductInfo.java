package com.ipay.server.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.google.common.collect.Sets;

@Entity
public class ProductInfo extends BaseEntity {
	
	private String banner;
	
	private String barcode;
	
	private String name;
	
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

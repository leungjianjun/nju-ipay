package com.ipay.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Attribute extends BaseEntity {
	
	@Column(name="key_string")
	private String key;
	
	@Column(name="value_string")
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

package com.ipay.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 该实体包含某个商品的属性
 * @author ljj
 *
 */
@Entity
public class Attribute extends BaseEntity {
	
	/**
	 * 属性名
	 */
	@Column(name="key_string")
	private String key;
	
	/**
	 * 属性值
	 */
	@Column(name="value_string")
	private String value;
	
	public Attribute(String key,String value){
		this.key=key;
		this.value=value;
	}

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

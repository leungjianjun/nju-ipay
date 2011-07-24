package com.ipay.server.entity;

import javax.persistence.*;

/**
 * 该实体是一个基类,所有实体必须继承该实体,方便管理,同时包含所有的实体的基本信息
 * @author ljj
 *
 */
@MappedSuperclass
public class BaseEntity {
	
	/**
	 * 实体id,hibernate自主负责
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	/**
	 * 版本管理,有hibernate自主负责
	 */
	@Version
	private Integer version;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getVersion() {
		return version;
	}

}

/*
 * entity是服务器要用的实体模型,同时使用hibernate框架把对象保存的关系数据库中,减少复杂性
 * 这里需要用到annotation配置数据库,以及数据的正确性.
 */
package com.ipay.server.entity;

import javax.persistence.Entity;

/**
 * 
 * @author ljj
 *
 */
@Entity
public class Admin extends User {

}

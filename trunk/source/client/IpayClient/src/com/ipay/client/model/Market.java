package com.ipay.client.model;

public class Market {
	private int id;
	private String name;
	private String laction;
	private String introduction;
	private String servicePhone;
	private String complainPhone;
	private String createDate;
	public Market(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLaction() {
		return laction;
	}
	public void setLaction(String laction) {
		this.laction = laction;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getServicePhone() {
		return servicePhone;
	}
	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}
	public String getComplainPhone() {
		return complainPhone;
	}
	public void setComplainPhone(String complainPhone) {
		this.complainPhone = complainPhone;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public int getId() {
		return id;
	}
}

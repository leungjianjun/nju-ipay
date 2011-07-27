package com.ipay.client.model;

public class Product {
	private double price;
	private String description;
	private String barcode;
	private String name;//商品名称
	private String banner;//生产商
	
	
	public Product(){
		
	}
	
	public Product(double price, String description, String barcode, String name,
			String banner) {
		super();
		this.price = price;
		this.description = description;
		this.barcode = barcode;
		this.name = name;
		this.banner = banner;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	
	
	
}

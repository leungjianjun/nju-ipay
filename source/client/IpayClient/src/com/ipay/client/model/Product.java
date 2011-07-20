package com.ipay.client.model;

public class Product {
	private double price;
	private String description;
	private long barcode;
	public Product(long barcode){
		this.barcode = barcode;
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
	public long getBarcode() {
		return barcode;
	}
	
}

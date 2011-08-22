package com.ipay.client.model;

public class SpecialProduct extends Product {
	public static final String New_PRICE="newPrice";
	public static final String OLD_PRICE="oldPrice";
	public static final String AD_WORDS = "adWords";
	
	private double specialPrice;
	private String adWords;
	public double getSpecialPrice() {
		return specialPrice;
	}
	public void setSpecialPrice(double specialPrice) {
		this.specialPrice = specialPrice;
	}
	public String getAdWords() {
		return adWords;
	}
	public void setAdWords(String adWords) {
		this.adWords = adWords;
	}
}

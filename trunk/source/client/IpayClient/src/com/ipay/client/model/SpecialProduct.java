package com.ipay.client.model;


public class SpecialProduct extends Product {
	
	public static final String OLD_PRICE="oldPrice";
	public static final String AD_WORDS = "adWords";
	public static final String NEW_PRICE="newPrice";
	
	private double oldPrice;
	private String adWords;
	public double getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(double specialPrice) {
		this.oldPrice = specialPrice;
	}
	public String getAdWords() {
		return adWords;
	}
	public void setAdWords(String adWords) {
		this.adWords = adWords;
	}
}

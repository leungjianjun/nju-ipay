package com.ipay.client.model;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Product {
	public static final String ID="id";
	public static final String NAME="name";
	public static final String BANNER="banner";
	public static final String MIN_IMG_URL="minImgUrl";
	public static final String MID_IMG_URL="midImgUrl";
	public static final String PRICE="price";
	public static final String QUANTITY="quantity";
	public static final String ATTRIBUTES="attributes";
	public static final String BARCADE = "barcode";
	private double price;
	private String description;
	private String barcode;
	private String name;//商品名称
	private String banner;//生产商
	private int id;
	private int quantity;
	private String midImgUrl;
	private String minImgUrl;
	public String getMidImgUrl() {
		return midImgUrl;
	}

	public void setMidImgUrl(String midImgUrl) {
		this.midImgUrl = midImgUrl;
	}

	public String getMinImgUrl() {
		return minImgUrl;
	}

	public void setMinImgUrl(String minImgUrl) {
		this.minImgUrl = minImgUrl;
	}

	private HashMap<String, String> attributes = new HashMap<String, String>();
	
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
	public void putAttr(String key, String value){
		attributes.put(key, value);
	}
	public String getAttr(String key){
		return attributes.get(key);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public HashMap<String, String> getAttributes() {
		return attributes;
	}
	public static Product parseJSONObect(JSONObject object) throws JSONException{
		Product product = new Product();
		product.setId(object.getInt(ID));
		product.setName(object.getString(NAME));
		product.setBanner(object.getString(BANNER));
		product.setBarcode(object.getString(BARCADE));
		product.setMidImgUrl(object.getString(MID_IMG_URL));
		product.setMinImgUrl(object.getString(MIN_IMG_URL));
		product.setPrice(object.getDouble(PRICE));
		product.setQuantity(object.getInt(QUANTITY));
		JSONArray attrs = object.getJSONArray(ATTRIBUTES);
		for (int i = 0; i < attrs.length(); i++) {
			JSONObject attr = attrs.getJSONObject(i);
			product.putAttr(attr.getString("key"),
					attr.getString("value"));
		}
		return product;
	}
	
}

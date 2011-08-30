/**
 * 
 */
package com.ipay.client.model;

import java.util.ArrayList;
import java.util.Observable;


/**
 * 单键，维护一个存放商品对象的ArrayList
 * 被观察者
 * 
 * 
 * @author tangym
 * 
 */
public class ShoppingCart {
	private static ShoppingCart instance;
	private ArrayList<Product> list;

	public static ShoppingCart getInstance() {
		if (instance != null)
			return instance;
		else
			return new ShoppingCart();
	}

	private ShoppingCart() {
		list = new ArrayList<Product>();
	}

	public Product get(int index) {
		return list.get(index);
	}

	public void add(Product product) {
		list.add(product);
	}

	public void remove(int index) {
		list.remove(index);
	}

	public void remove(Product product) {
		int index = list.indexOf(product);
		list.remove(index);
	}

	public void clear() {
		list.clear();
	}

	/**
	 * @return int 总数量
	 */
	public int getQuantity() {
		int quantity = 0;
		for (int i = 0; i < list.size(); i++) {
			quantity += list.get(i).getQuantity();
		}
		return quantity;
	}

	/**
	 * @return double 总价格
	 */
	public double getTotalPrice() {
		double totalPrice = 0;
		for (int i = 0; i < list.size(); i++) {
			totalPrice += list.get(i).getQuantity() * list.get(i).getPrice();
		}
		return totalPrice;
	}
	
	public int getSize(){
		return list.size();
	}

}

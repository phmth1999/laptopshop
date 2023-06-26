package com.phmth.laptopshop.service;

import java.util.Collection;

import com.phmth.laptopshop.dto.CartItem;

public interface IShoppingCartService {

	int getTotalMoney();

	int getTotalQuantity();

	Collection<CartItem> getAllItems();

	void clear();

	boolean update(long productId, int numProduct);

	void remove(long id);

	void add(CartItem item);
	
	void readCookieCart(String txt);
	
	CartItem findCartItem(long id);

	boolean isCartEmpty();

}

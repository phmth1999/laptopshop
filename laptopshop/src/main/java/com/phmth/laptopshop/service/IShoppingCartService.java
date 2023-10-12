package com.phmth.laptopshop.service;

import java.util.Collection;

import com.phmth.laptopshop.dto.CartItemDto;

public interface IShoppingCartService {

	int getTotalMoney();

	int getTotalQuantity();

	Collection<CartItemDto> getAllItems();
	CartItemDto findCartItem(long id);
	
	void add (long userId, long productId, int numProduct);

	boolean update(long userId, long productId, int numProduct);

	void remove(long userId, long productId);
	void clear(long userId);

	void readCookieCart(String txt, long userID);
	void readDatabaseCart(long userId);
	
	boolean isCartEmpty();

}

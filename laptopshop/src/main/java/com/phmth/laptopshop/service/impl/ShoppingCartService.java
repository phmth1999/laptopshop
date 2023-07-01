package com.phmth.laptopshop.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phmth.laptopshop.dto.CartItem;
import com.phmth.laptopshop.entity.ProductEntity;
import com.phmth.laptopshop.repository.IProductRepository;
import com.phmth.laptopshop.service.IShoppingCartService;

import groovy.util.logging.Slf4j;

@Service
@Slf4j
public class ShoppingCartService implements IShoppingCartService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);

	private Map<Long, CartItem> carts = new HashMap<>();

	@Autowired
	private IProductRepository productRepository;

	@Override
	public boolean isCartEmpty() {
		if(this.carts.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public void add(CartItem item) {
		CartItem cartItem = carts.get(item.getProductId());
		if (cartItem == null) {
			carts.put(item.getProductId(), item);
		} else {
			cartItem.setNumProduct(cartItem.getNumProduct() + item.getNumProduct());
			cartItem.setTotalPrice(item.getTotalPrice() + cartItem.getTotalPrice());
		}
	}

	@Override
	public void remove(long id) {
		carts.remove(id);
	}

	@Override
	public CartItem findCartItem(long id) {
		return carts.get(id);
	}

	@Override
	public boolean update(long productId, int numProduct) {
		CartItem cartItem = carts.get(productId);
		if(cartItem == null || cartItem.isEmpty()) {
			return false;
		}
		cartItem.setNumProduct(numProduct);
		cartItem.setTotalPrice(numProduct*(cartItem.getPrice()-(cartItem.getPrice()*cartItem.getDiscount())));
		carts.put(productId, cartItem);
		return true;
	}

	@Override
	public void clear() {
		carts.clear();
	}

	@Override
	public Collection<CartItem> getAllItems() {
		return carts.values().isEmpty() ? null : carts.values();
	}

	@Override
	public int getTotalQuantity() {
		return carts.values().stream().mapToInt(item -> item.getNumProduct()).sum();
	}

	@Override
	public int getTotalMoney() {
		return carts.values().stream()
				.mapToInt(
						item -> item.getNumProduct() * (item.getPrice() - (item.getPrice() * item.getDiscount() / 100)))
				.sum();
	}

	@Override
	public void readCookieCart(String txt) {
		if (txt != null && !txt.isEmpty() && carts.isEmpty()) {
			String[] cartItems = txt.split("_");
			for (String cartItem : cartItems) {
				String[] items = cartItem.split(":");
				long id = (long) Integer.parseInt(items[0]);
				int num = Integer.parseInt(items[1]);
				ProductEntity productEntity = productRepository.findById(id).get();
				if (productEntity != null) {
					CartItem item = new CartItem();
					item.setProductId(id);
					item.setName(productEntity.getName());
					item.setPrice(productEntity.getPrice());
					item.setDiscount(productEntity.getDiscount());
					item.setNumProduct(num);
					item.setThumbnail(productEntity.getThumbnail());
					item.setTotalPrice(num * (productEntity.getPrice()
							- (productEntity.getPrice() * productEntity.getDiscount() / 100)));
					this.add(item);
				}
			}
		}
	}

}

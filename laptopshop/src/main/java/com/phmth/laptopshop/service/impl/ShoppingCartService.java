package com.phmth.laptopshop.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

import com.phmth.laptopshop.dto.CartItemDto;
import com.phmth.laptopshop.entity.CartItemEntity;
import com.phmth.laptopshop.entity.ProductEntity;
import com.phmth.laptopshop.entity.UserEntity;
import com.phmth.laptopshop.mapper.CartItemMapper;
import com.phmth.laptopshop.repository.ICartItemRepository;
import com.phmth.laptopshop.repository.IProductRepository;
import com.phmth.laptopshop.repository.IUserRepository;
import com.phmth.laptopshop.service.IShoppingCartService;

import lombok.extern.log4j.Log4j2;

@Service
@SessionScope
@Transactional
@Log4j2
public class ShoppingCartService implements IShoppingCartService {

	private Map<Long, CartItemDto> carts = new HashMap<>();

	@Autowired
	private IProductRepository productRepository;

	@Autowired
	private ICartItemRepository cartItemRepository;

	@Autowired
	private IUserRepository userRepository;

	private CartItemMapper cartItemMapper = new CartItemMapper();

	@Override
	public boolean isCartEmpty() {
		if (this.carts.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public void add(long userId, long productId, int numProduct) {
		ProductEntity productEntity = productRepository.findById(productId).get();
		int totalPrice = numProduct * (productEntity.getPrice() - (productEntity.getPrice() * productEntity.getDiscount() / 100));
		
		// case user do not login
		if (userId == 0) {
			CartItemDto cartItem = carts.get(productId);
			CartItemDto cartItemDto = new CartItemDto();
			cartItemDto.setId(null);
			cartItemDto.setUserId(null);
			cartItemDto.setProductId(productId);
			cartItemDto.setName(productEntity.getName());
			cartItemDto.setPrice(productEntity.getPrice());
			cartItemDto.setDiscount(productEntity.getDiscount());
			cartItemDto.setPrice(productEntity.getPrice());
			cartItemDto.setThumbnail(productEntity.getThumbnail());
			cartItemDto.setNumProduct(numProduct);
			cartItemDto.setQuantity_in_stock(productEntity.getQuantity_in_stock());
			cartItemDto.setTotalPrice(totalPrice);
			// case have not product in cart
			if (cartItem == null) {
				carts.put(productId, cartItemDto);
				log.error("cookie add user id: "+userId+"product id: "+productId);
			}
			else {
				cartItem.setNumProduct(cartItem.getNumProduct() + cartItemDto.getNumProduct());
				cartItem.setTotalPrice(cartItemDto.getTotalPrice() + cartItem.getTotalPrice());
				log.error("cookie update user id: "+userId+"product id: "+productId);
			}
		}
		
		// case user has logged
		else {
			UserEntity userlogin = userRepository.findById(userId).get();
			CartItemEntity cartItem = cartItemRepository.findByUserAndProduct(userlogin, productEntity);
			CartItemDto cartItemDto = new CartItemDto();
			cartItemDto.setId(userId);
			cartItemDto.setProductId(productId);
			cartItemDto.setName(productEntity.getName());
			cartItemDto.setPrice(productEntity.getPrice());
			cartItemDto.setDiscount(productEntity.getDiscount());
			cartItemDto.setPrice(productEntity.getPrice());
			cartItemDto.setThumbnail(productEntity.getThumbnail());
			cartItemDto.setNumProduct(numProduct);
			cartItemDto.setQuantity_in_stock(productEntity.getQuantity_in_stock());
			cartItemDto.setTotalPrice(totalPrice);
			if (cartItem == null) {
				CartItemEntity cartItemEntity = new CartItemEntity();

				cartItemEntity.setProduct(productEntity);
				cartItemEntity.setUser(userlogin);
				cartItemEntity.setName(cartItemDto.getName());
				cartItemEntity.setPrice(cartItemDto.getPrice());
				cartItemEntity.setDiscount(cartItemDto.getDiscount());
				cartItemEntity.setQuantity_in_stock(cartItemDto.getQuantity_in_stock());
				cartItemEntity.setThumbnail(cartItemDto.getThumbnail());
				cartItemEntity.setNumProduct(cartItemDto.getNumProduct());
				cartItemEntity.setTotalPrice(cartItemDto.getTotalPrice());
				cartItemRepository.save(cartItemEntity);
				carts.put(productId, cartItemMapper.entityToDto(cartItemEntity));
				log.error("database add user id: "+userId+"product id: "+productId);
			}
			else {
				cartItem.setNumProduct(cartItem.getNumProduct() + cartItemDto.getNumProduct());
				cartItem.setTotalPrice(cartItemDto.getTotalPrice() + cartItem.getTotalPrice());
				cartItemRepository.save(cartItem);
				carts.put(productId, cartItemMapper.entityToDto(cartItem));
				log.error("database update user id: "+userId+"product id: "+productId);
			}
		}	}

	@Override
	public void remove(long userId, long productId) {
		carts.remove(productId);
		if(userId > 0) {
			UserEntity userlogin = userRepository.findById(userId).get();
			ProductEntity productEntity = productRepository.findById(productId).get();
			CartItemEntity cartItemEntity = cartItemRepository.findByUserAndProduct(userlogin, productEntity);
			cartItemRepository.deleteById(cartItemEntity.getId());
		}
	}

	@Override
	public CartItemDto findCartItem(long id) {
		return carts.get(id);
	}

	@Override
	public boolean update(long userId, long productId, int numProduct) {
		CartItemDto cartItem = carts.get(productId);
		if (cartItem == null || cartItem.isEmpty()) {
			return false;
		}
		if(userId == 0) {
			cartItem.setNumProduct(numProduct + cartItem.getNumProduct());
			cartItem.setTotalPrice(cartItem.getNumProduct() * (cartItem.getPrice() - (cartItem.getPrice() * cartItem.getDiscount())));
			carts.put(productId, cartItem);
		}else if(userId > 0) {
			ProductEntity productEntity = productRepository.findById(productId).get();
			UserEntity userlogin = userRepository.findById(userId).get();
			CartItemEntity cartItem1 = cartItemRepository.findByUserAndProduct(userlogin, productEntity);
			cartItem1.setNumProduct(numProduct + cartItem1.getNumProduct());
			cartItem1.setTotalPrice(cartItem1.getNumProduct() * (cartItem.getPrice() - (cartItem.getPrice() * cartItem.getDiscount())));
			cartItemRepository.save(cartItem1);
			carts.put(productId, cartItemMapper.entityToDto(cartItem1));
		}
		
		return true;
	}

	@Override
	public void clear(long userId) {
		carts.clear();
		if(userId > 0) {
			UserEntity userlogin = userRepository.findById(userId).get();
			List<CartItemEntity> listCart = cartItemRepository.findByUser(userlogin);
			for (CartItemEntity cartItemEntity : listCart) {
				cartItemRepository.deleteById(cartItemEntity.getId());
			}
		}
	}

	@Override
	public Collection<CartItemDto> getAllItems() {
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
	public void readCookieCart(String txt, long userID) {
		
		if (txt != null && !txt.isEmpty()) {
			String[] cartItems = txt.split("_");
			for (String cartItem : cartItems) {
				String[] items = cartItem.split(":");
				long productId = (long) Integer.parseInt(items[0]);
				int numProduct = Integer.parseInt(items[1]);
				
				if(carts.isEmpty() && userID == 0) {
					log.error("read cookie user id = 0");
					this.add(0, productId, numProduct);
				}else if(userID > 0){
					log.error("read cookie user id: "+userID+"carts size:"+carts.size());
					this.add(userID, productId, numProduct);
					carts.clear();
				}
			}
		}
	}

	@Override
	public void readDatabaseCart(long userId) {
		if(carts.isEmpty()) {
			log.error("read database user id: "+userId);
			UserEntity userlogin = userRepository.findById(userId).get();
			List<CartItemEntity> listCart = cartItemRepository.findByUser(userlogin);
			for (CartItemEntity cartItemEntity : listCart) {
				//insert database data into map
				carts.put(cartItemEntity.getProduct().getId(), cartItemMapper.entityToDto(cartItemEntity));
			}
		}
	}

}

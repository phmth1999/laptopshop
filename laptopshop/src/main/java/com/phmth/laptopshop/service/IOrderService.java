package com.phmth.laptopshop.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phmth.laptopshop.dto.CartItem;
import com.phmth.laptopshop.dto.FormOrderInfo;
import com.phmth.laptopshop.dto.FormSearchOrder;
import com.phmth.laptopshop.entity.OrderEntity;
import com.phmth.laptopshop.enums.StateCheckout;
import com.phmth.laptopshop.enums.StateOrder;

public interface IOrderService{

	Page<OrderEntity> findAll(int page, int limit);
	Page<OrderEntity> findAll(int page, int limit, FormSearchOrder formSearchOrder);

	List<OrderEntity> findByUser(long id);
	
	Optional<OrderEntity> findOne(long id);
	
	OrderEntity Order(Collection<CartItem> carts, FormOrderInfo formOrderInfo);
	
	Optional<OrderEntity> findByCodeOrder(String codeOrder);
	
	boolean updateStateOrder(long id, StateOrder status);
	void updateStateCheckout(long id, StateCheckout paid);
	
	boolean cancelOrder(long id);
}

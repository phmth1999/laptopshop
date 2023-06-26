package com.phmth.laptopshop.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phmth.laptopshop.dto.CartItem;
import com.phmth.laptopshop.dto.OrderDto;
import com.phmth.laptopshop.dto.request.OrderInfoRequest;
import com.phmth.laptopshop.dto.request.SearchOrderRequest;
import com.phmth.laptopshop.enums.StateCheckout;
import com.phmth.laptopshop.enums.StateOrder;

public interface IOrderService{

	Page<OrderDto> findAll(int page, int limit);
	Page<OrderDto> findAll(int page, int limit, SearchOrderRequest formSearchOrder);

	List<OrderDto> findByUser(long id);
	
	Optional<OrderDto> findById(long id);
	
	OrderDto Order(Collection<CartItem> carts, OrderInfoRequest formOrderInfo);
	
	Optional<OrderDto> findByCodeOrder(String codeOrder);
	
	boolean updateStateOrder(long id, StateOrder status);
	void updateStateCheckout(long id, StateCheckout paid);
	
	boolean cancelOrder(long id);
}

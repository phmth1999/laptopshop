package com.phmth.laptopshop.mapper;

import com.phmth.laptopshop.dto.OrderDto;
import com.phmth.laptopshop.entity.OrderEntity;

public class OrderMapper {

	public OrderDto entityToDto(OrderEntity orderEntity) {
		OrderDto orderDto = new OrderDto();
		
		orderDto.setId(orderEntity.getId());
		orderDto.setUser(orderEntity.getUser().getId());
		orderDto.setCodeOrder(orderEntity.getCodeOrder());
		orderDto.setName(orderEntity.getName());
		orderDto.setEmail(orderEntity.getEmail());
		orderDto.setPhone(orderEntity.getPhone());
		orderDto.setAddress_delivery(orderEntity.getAddress_delivery());
		orderDto.setCreated_at(orderEntity.getCreated_at());
		orderDto.setNum(orderEntity.getNum());
		orderDto.setTotal_money(orderEntity.getTotal_money());
		orderDto.setPayment(orderEntity.getPayment());
		orderDto.setStateCheckout(orderEntity.getStateCheckout());
		orderDto.setStateOrder(orderEntity.getStateOrder());
		
		return orderDto;
	}
}

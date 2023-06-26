package com.phmth.laptopshop.mapper;

import com.phmth.laptopshop.dto.OrderDetailDto;
import com.phmth.laptopshop.entity.OrderDetailEntity;

public class OrderDetailMapper {

	public OrderDetailDto entityToDto(OrderDetailEntity orderDetailEntity) {
		OrderDetailDto orderDetailDto = new OrderDetailDto();
		
		orderDetailDto.setId(orderDetailEntity.getId());
		orderDetailDto.setOrder(orderDetailEntity.getOrder().getId());
		orderDetailDto.setNum(orderDetailEntity.getNum());
		orderDetailDto.setPrice(orderDetailEntity.getPrice());
		orderDetailDto.setDiscount(orderDetailEntity.getDiscount());
		orderDetailDto.setTotalPrice(orderDetailEntity.getTotalPrice());
		
		return orderDetailDto;
	}
}

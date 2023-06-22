package com.phmth.laptopshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phmth.laptopshop.entity.OrderDetailEntity;
import com.phmth.laptopshop.entity.OrderEntity;
import com.phmth.laptopshop.repository.IOrderDetailRepository;
import com.phmth.laptopshop.service.IOrderDetailService;

@Service
@Transactional
public class OrderDetailService implements IOrderDetailService {

	@Autowired
	private IOrderDetailRepository orderDetailRepository;

	@Override
	public List<OrderDetailEntity> findByOrder(long id) {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setId(id);
		
		return orderDetailRepository.findByOrder(orderEntity);
	}

}

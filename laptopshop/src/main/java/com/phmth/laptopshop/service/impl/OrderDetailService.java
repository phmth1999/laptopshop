package com.phmth.laptopshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phmth.laptopshop.dto.OrderDetailDto;
import com.phmth.laptopshop.entity.OrderDetailEntity;
import com.phmth.laptopshop.entity.OrderEntity;
import com.phmth.laptopshop.mapper.OrderDetailMapper;
import com.phmth.laptopshop.mapper.ProductMapper;
import com.phmth.laptopshop.repository.IOrderDetailRepository;
import com.phmth.laptopshop.service.IOrderDetailService;

@Service
@Transactional
public class OrderDetailService implements IOrderDetailService {

	@Autowired
	private IOrderDetailRepository orderDetailRepository;
	
	private OrderDetailMapper orderDetailMapper = new OrderDetailMapper();
	
	private ProductMapper productMapper = new ProductMapper();

	public List<OrderDetailDto> findByOrder(long id) {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setId(id);
		
		List<OrderDetailEntity> listOrderDetailEntity = orderDetailRepository.findByOrder(orderEntity);
		
		List<OrderDetailDto> listOrderDetailDto = new ArrayList<>();
		for (OrderDetailEntity orderDetailEntity : listOrderDetailEntity) {
			OrderDetailDto orderDetailDto = orderDetailMapper.entityToDto(orderDetailEntity);
			orderDetailDto.setProduct(productMapper.entityToDto(orderDetailEntity.getProduct()));
			listOrderDetailDto.add(orderDetailDto);
		}
		
		return listOrderDetailDto;
	}

}

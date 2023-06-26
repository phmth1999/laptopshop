package com.phmth.laptopshop.service;

import java.util.List;

import com.phmth.laptopshop.dto.OrderDetailDto;

public interface IOrderDetailService{

	List<OrderDetailDto> findByOrder(long id);

}

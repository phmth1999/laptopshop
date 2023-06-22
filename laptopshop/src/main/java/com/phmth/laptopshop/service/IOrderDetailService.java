package com.phmth.laptopshop.service;

import java.util.List;

import com.phmth.laptopshop.entity.OrderDetailEntity;

public interface IOrderDetailService{

	List<OrderDetailEntity> findByOrder(long id);

}

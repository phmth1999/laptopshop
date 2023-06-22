package com.phmth.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phmth.laptopshop.entity.OrderEntity;
import com.phmth.laptopshop.entity.OrderDetailEntity;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetailEntity, Long>{

	List<OrderDetailEntity> findByOrder(OrderEntity orderEntity);

}

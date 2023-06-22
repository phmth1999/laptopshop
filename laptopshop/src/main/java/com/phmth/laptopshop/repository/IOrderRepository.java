package com.phmth.laptopshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phmth.laptopshop.entity.OrderEntity;
import com.phmth.laptopshop.entity.UserEntity;

@Repository
public interface IOrderRepository extends JpaRepository<OrderEntity, Long>{

	List<OrderEntity> findByUser(UserEntity userEntity);

	Page<OrderEntity> findAll(Specification<OrderEntity> specification, Pageable pageable);
	
	Optional<OrderEntity> findByCodeOrder(String codeOrder);
}

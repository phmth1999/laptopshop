package com.phmth.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phmth.laptopshop.entity.CartItemEntity;
import com.phmth.laptopshop.entity.ProductEntity;
import com.phmth.laptopshop.entity.UserEntity;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItemEntity, Long>{

	CartItemEntity findByUserAndProduct(UserEntity userLogin, ProductEntity productCart);

	List<CartItemEntity> findByUser(UserEntity userLogin);

}

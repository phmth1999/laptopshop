package com.phmth.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phmth.laptopshop.entity.CheckoutEntity;

@Repository
public interface ICheckoutRepository extends JpaRepository<CheckoutEntity, Long>{

}

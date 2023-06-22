package com.phmth.laptopshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phmth.laptopshop.entity.CheckoutEntity;
import com.phmth.laptopshop.repository.ICheckoutRepository;
import com.phmth.laptopshop.service.ICheckoutService;

@Service
public class CheckoutService implements ICheckoutService{

	@Autowired
	private ICheckoutRepository checkoutRepository;
	
	@Override
	public CheckoutEntity save(CheckoutEntity checkoutEntity) {
		return checkoutRepository.save(checkoutEntity);
	}

}

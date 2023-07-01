package com.phmth.laptopshop.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phmth.laptopshop.dto.CheckoutDto;
import com.phmth.laptopshop.entity.CheckoutEntity;
import com.phmth.laptopshop.entity.OrderEntity;
import com.phmth.laptopshop.entity.UserEntity;
import com.phmth.laptopshop.mapper.CheckoutMapper;
import com.phmth.laptopshop.repository.ICheckoutRepository;
import com.phmth.laptopshop.repository.IOrderRepository;
import com.phmth.laptopshop.repository.IUserRepository;
import com.phmth.laptopshop.service.ICheckoutService;

import groovy.util.logging.Slf4j;

@Service
@Slf4j
public class CheckoutService implements ICheckoutService{

	@Autowired
	private ICheckoutRepository checkoutRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IOrderRepository orderRepository;
	
	private CheckoutMapper checkoutMapper = new CheckoutMapper();
	
	@Override
	public CheckoutDto insert(CheckoutDto checkoutDto) {
		if(checkoutDto == null) {
			return null;
		}
		
		if(checkoutDto.isEmpty()) {
			return null;
		}
		
		CheckoutEntity checkoutEntity = new CheckoutEntity();
		
		Optional<OrderEntity> orderEntity = orderRepository.findById(checkoutDto.getOrder());
		if(orderEntity.isEmpty()) {
			return null;
		}
		checkoutEntity.setOrder(orderEntity.get());
		Optional<UserEntity> userEntity = userRepository.findById(checkoutDto.getUser());
		if(userEntity.isEmpty()) {
			return null;
		}
		checkoutEntity.setUser(userEntity.get());
		checkoutEntity.setAmount(checkoutDto.getAmount());
		checkoutEntity.setBankCode(checkoutDto.getBankCode());
		checkoutEntity.setBankTranNo(checkoutDto.getBankTranNo());
		checkoutEntity.setCardType(checkoutDto.getCardType());
		checkoutEntity.setOrderInfo(checkoutDto.getOrderInfo());
		checkoutEntity.setPayDate(checkoutDto.getPayDate());
		checkoutEntity.setResponseCode(checkoutDto.getResponseCode());
		checkoutEntity.setTmnCode(checkoutDto.getTmnCode());
		checkoutEntity.setTransactionNo(checkoutDto.getTransactionNo());
		checkoutEntity.setTransactionStatus(checkoutDto.getTransactionStatus());
		checkoutEntity.setTxnRef(checkoutDto.getTxnRef());
		checkoutEntity.setSecureHash(checkoutDto.getSecureHash());
		
		CheckoutEntity checkoutSave = checkoutRepository.save(checkoutEntity);
		if(!checkoutRepository.existsById(checkoutSave.getId())) {
			return null;
		}
		
		return checkoutMapper.entityToDto(checkoutSave);
	}

}

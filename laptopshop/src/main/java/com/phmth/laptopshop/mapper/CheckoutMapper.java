package com.phmth.laptopshop.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.phmth.laptopshop.dto.CheckoutDto;
import com.phmth.laptopshop.entity.CheckoutEntity;
import com.phmth.laptopshop.entity.OrderEntity;
import com.phmth.laptopshop.entity.UserEntity;
import com.phmth.laptopshop.repository.IOrderRepository;
import com.phmth.laptopshop.repository.IUserRepository;

public class CheckoutMapper {
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IOrderRepository orderRepository;
	

	public CheckoutDto entityToDto(CheckoutEntity checkoutEntity) {
		CheckoutDto checkoutDto = new CheckoutDto();
		checkoutDto.setOrder(checkoutEntity.getOrder().getId());
		checkoutDto.setUser(checkoutEntity.getUser().getId());
		checkoutDto.setAmount(checkoutEntity.getAmount());
		checkoutDto.setBankCode(checkoutEntity.getBankCode());
		checkoutDto.setBankTranNo(checkoutEntity.getBankTranNo());
		checkoutDto.setCardType(checkoutEntity.getCardType());
		checkoutDto.setOrderInfo(checkoutEntity.getOrderInfo());
		checkoutDto.setPayDate(checkoutEntity.getPayDate());
		checkoutDto.setResponseCode(checkoutEntity.getResponseCode());
		checkoutDto.setTmnCode(checkoutEntity.getTmnCode());
		checkoutDto.setTransactionNo(checkoutEntity.getTransactionNo());
		checkoutDto.setTransactionStatus(checkoutEntity.getTransactionStatus());
		checkoutDto.setTxnRef(checkoutEntity.getTxnRef());
		checkoutDto.setSecureHash(checkoutEntity.getSecureHash());
		
		return checkoutDto;
	}
	
	public CheckoutEntity dtoToEntity(CheckoutDto checkoutDto) {
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
		
		return checkoutEntity;
	}
}

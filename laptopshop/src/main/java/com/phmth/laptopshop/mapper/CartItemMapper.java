package com.phmth.laptopshop.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.phmth.laptopshop.dto.CartItemDto;
import com.phmth.laptopshop.entity.CartItemEntity;
import com.phmth.laptopshop.entity.ProductEntity;
import com.phmth.laptopshop.entity.UserEntity;
import com.phmth.laptopshop.repository.IProductRepository;
import com.phmth.laptopshop.repository.IUserRepository;

public class CartItemMapper {
	
	@Autowired
	private IProductRepository productRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	public CartItemDto entityToDto(CartItemEntity cartItemEntity) {
		CartItemDto cartItemDto = new CartItemDto();

		cartItemDto.setId(cartItemEntity.getId());
		cartItemDto.setProductId(cartItemEntity.getProduct().getId());
		cartItemDto.setUserId(cartItemEntity.getUser().getId());
		cartItemDto.setName(cartItemEntity.getName());
		cartItemDto.setPrice(cartItemEntity.getPrice());
		cartItemDto.setDiscount(cartItemEntity.getDiscount());
		cartItemDto.setQuantity_in_stock(cartItemDto.getQuantity_in_stock());
		cartItemDto.setThumbnail(cartItemEntity.getThumbnail());
		cartItemDto.setNumProduct(cartItemEntity.getNumProduct());
		cartItemDto.setTotalPrice(cartItemEntity.getTotalPrice());

		return cartItemDto;
	}

	public CartItemEntity dtoToEntity(CartItemDto cartItemDto) {
		CartItemEntity cartItemEntity = new CartItemEntity();

		cartItemEntity.setId(cartItemDto.getId());
		Optional<ProductEntity> productEntity = productRepository.findById(cartItemDto.getProductId());
		cartItemEntity.setProduct(productEntity.get());
		Optional<UserEntity> userEntity = userRepository.findById(cartItemDto.getUserId());
		cartItemEntity.setUser(userEntity.get());
		cartItemEntity.setName(cartItemDto.getName());
		cartItemEntity.setPrice(cartItemDto.getPrice());
		cartItemEntity.setDiscount(cartItemDto.getDiscount());
		cartItemEntity.setQuantity_in_stock(cartItemDto.getQuantity_in_stock());
		cartItemEntity.setThumbnail(cartItemDto.getThumbnail());
		cartItemEntity.setNumProduct(cartItemDto.getNumProduct());
		cartItemEntity.setTotalPrice(cartItemDto.getTotalPrice());

		return cartItemEntity;
	}
}

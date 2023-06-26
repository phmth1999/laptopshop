package com.phmth.laptopshop.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.phmth.laptopshop.dto.ProductDto;
import com.phmth.laptopshop.entity.BrandEntity;
import com.phmth.laptopshop.entity.CategoryEntity;
import com.phmth.laptopshop.entity.ProductEntity;
import com.phmth.laptopshop.repository.IBrandRepository;
import com.phmth.laptopshop.repository.ICategoryRepository;

@Component
public class ProductMapper {
	
	@Autowired
	private ICategoryRepository categoryRepository;

	@Autowired
	private IBrandRepository brandRepository;

	public ProductDto entityToDto(ProductEntity productEntity) {
		ProductDto productDto = new ProductDto();
		
		productDto.setId(productEntity.getId());
		productDto.setCategoryName(productEntity.getCategory().getName());
		productDto.setBrandName(productEntity.getBrand().getName());
		productDto.setName(productEntity.getName());
		productDto.setPrice(productEntity.getPrice());
		productDto.setDiscount(productEntity.getDiscount());
		productDto.setQuantity_in_stock(productEntity.getQuantity_in_stock());
		productDto.setThumbnail(productEntity.getThumbnail());
		productDto.setDescription(productEntity.getDescription());
		
		return productDto;
	}
	public ProductEntity dtoToEntity(ProductDto productDto) {
		ProductEntity productEntity = new ProductEntity();
		
		productEntity.setId(productDto.getId());
		Optional<CategoryEntity> categoryEntity = categoryRepository.findByName(productDto.getCategoryName());
		productEntity.setCategory(categoryEntity.get());
		Optional<BrandEntity> brandEntity = brandRepository.findByName(productDto.getBrandName());
		productEntity.setBrand(brandEntity.get());
		productEntity.setName(productDto.getName());
		productEntity.setPrice(productDto.getPrice());
		productEntity.setDiscount(productDto.getDiscount());
		productEntity.setQuantity_in_stock(productDto.getQuantity_in_stock());
		productEntity.setThumbnail(productDto.getThumbnail());
		productEntity.setDescription(productDto.getDescription());
		
		return productEntity;
	}
	
}

package com.phmth.laptopshop.mapper;

import com.phmth.laptopshop.dto.CategoryDto;
import com.phmth.laptopshop.entity.CategoryEntity;

public class CategoryMapper {

	public CategoryDto entityToDto(CategoryEntity categoryEntity) {
		CategoryDto categoryDto = new CategoryDto();

		categoryDto.setId(categoryEntity.getId());
		categoryDto.setName(categoryEntity.getName());

		return categoryDto;
	}

	public CategoryEntity dtoToEntity(CategoryDto categoryDto) {
		CategoryEntity categoryEntity = new CategoryEntity();

		categoryEntity.setId(categoryDto.getId());
		categoryEntity.setName(categoryDto.getName());

		return categoryEntity;
	}
}
